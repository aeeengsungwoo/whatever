package whatever.youmake.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import whatever.youmake.domain.Call;

@Getter
@Builder
@RequiredArgsConstructor
public class CallStartResponseDto {

    private final Long callId;

    public static CallStartResponseDto of(Call call){
        return CallStartResponseDto.builder()
                .callId(call.getId())
                .build();
    }

}
