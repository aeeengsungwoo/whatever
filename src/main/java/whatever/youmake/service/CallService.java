package whatever.youmake.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import whatever.youmake.domain.Call;
import whatever.youmake.domain.CallHistory;
import whatever.youmake.domain.User;
import whatever.youmake.dto.request.CallRequestDto;
import whatever.youmake.dto.response.CallResponseDto;
import whatever.youmake.dto.response.CallStartResponseDto;
import whatever.youmake.exeption.ApiException;
import whatever.youmake.exeption.ErrorDefine;
import whatever.youmake.repository.CallHistoryRepository;
import whatever.youmake.repository.CallRepository;
import whatever.youmake.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@Service
@Transactional
@RequiredArgsConstructor
public class CallService {

    private final UserRepository userRepository;
    private final CallRepository callRepository;
    private final CallHistoryRepository callHistoryRepository;
    public CallStartResponseDto startCall(Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        Call call = Call.builder()
                .createAt(LocalDateTime.now())
                .user(user)
                .build();

        callRepository.save(call);

        return CallStartResponseDto.of(call);
    }

    public CallResponseDto callAsync(CallRequestDto callRequestDto) {
        User user = userRepository.findById(callRequestDto.getUserId())
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        Call call = callRepository.findById(callRequestDto.getCallId())
                .orElseThrow(() -> new ApiException(ErrorDefine.CALL_NOT_FOUND));

        // 비동기
        CompletableFuture<Map<String, Object>> futureResponse = CompletableFuture.supplyAsync(() -> {
            RestClient restClient = RestClient.builder()
                    .baseUrl("https://proma-ai.store")
                    .build();

            try {
                return restClient.post()
                        .uri("/api/one-question")
                        .body(buildRequestBody(user, callRequestDto))
                        .retrieve()
                        .body(new ParameterizedTypeReference<Map<String, Object>>() {});
            } catch (RestClientException e) {
                throw new ApiException(ErrorDefine.AI_SERVER_REQUEST_ERROR);
            }
        });

        try {

            Map<String, Object> responseBody = futureResponse.get();  // get()으로 결과를 기다림

            if (responseBody != null && (Boolean) responseBody.get("success")) {
                Map<String, Object> responseDto = (Map<String, Object>) responseBody.get("responseDto");
                if (responseDto != null) {
                    String messageAnswer = (String) responseDto.get("messageAnswer");

                    CallHistory callHistory = CallHistory.builder()
                            .call(call)
                            .messageAnswer(messageAnswer)
                            .messageQuestion(callRequestDto.getMessageQuestion())
                            .createAt(LocalDateTime.now())
                            .build();

                    callHistoryRepository.save(callHistory);
                    return CallResponseDto.of(callHistory);

                } else {
                    throw new ApiException(ErrorDefine.AI_SERVER_ERROR_RESPONSE_DTO_ERROR);
                }
            } else {
                throw new ApiException(ErrorDefine.AI_SERVER_ERROR_RESPONSE_BODY_ERROR);
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new ApiException(ErrorDefine.AI_SERVER_REQUEST_ERROR);
        }
    }

    private Map<String, String> buildRequestBody(User user, CallRequestDto callRequestDto) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("userLoginId", user.getUserLoginId());
        requestBody.put("apiToken", user.getApiToken());
        requestBody.put("secretKey", user.getSecretKey());
        requestBody.put("messageQuestion", callRequestDto.getMessageQuestion());
        return requestBody;
    }


}
