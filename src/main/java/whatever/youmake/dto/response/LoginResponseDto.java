package whatever.youmake.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import whatever.youmake.domain.User;

@Getter
@Builder
@RequiredArgsConstructor
public class LoginResponseDto {
    private final Long userId;

    public static LoginResponseDto of(User user){
        return LoginResponseDto.builder()
                .userId(user.getId())
                .build();
    }
}
