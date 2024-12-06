package whatever.youmake.dto.request;

import lombok.Getter;
import whatever.youmake.domain.User;

import java.util.List;

@Getter
public class SignUpRequestDto {

    private String userLoginId;
    private String userPassword;

    public User toEntity(SignUpRequestDto signUpRequestDto) {
        return User.builder()
                .userLoginId(signUpRequestDto.getUserLoginId())
                .userPassword(signUpRequestDto.getUserPassword())
                .build();
    }



}
