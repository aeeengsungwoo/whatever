package whatever.youmake.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import whatever.youmake.domain.CallHistory;

@Getter
@ToString
@Builder
@RequiredArgsConstructor
public class CallResponseDto {

    private final String messageAnswer;

    public static CallResponseDto of(CallHistory callHistory){
        return CallResponseDto.builder()
                .messageAnswer(callHistory.getMessageAnswer())
                .build();
    }

}
