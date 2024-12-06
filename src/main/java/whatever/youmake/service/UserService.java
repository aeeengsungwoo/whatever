package whatever.youmake.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import whatever.youmake.domain.User;
import whatever.youmake.dto.request.LoginRequestDto;
import whatever.youmake.dto.request.SignUpRequestDto;
import whatever.youmake.dto.response.LoginResponseDto;
import whatever.youmake.exeption.ApiException;
import whatever.youmake.exeption.ErrorDefine;
import whatever.youmake.repository.UserRepository;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;


    public LoginResponseDto login(LoginRequestDto loginRequestDto){

        User user = userRepository.findByUserLoginId(loginRequestDto.getLoginId())
                .orElseThrow(() -> new ApiException(ErrorDefine.USER_NOT_FOUND));

        if(user.getUserPassword().equals(loginRequestDto.getUserPassword())){

            return LoginResponseDto.of(user);

        } else throw new ApiException(ErrorDefine.USER_NOT_FOUND);

    }
    public Boolean signup(SignUpRequestDto signUpRequestDto){
        User user = userRepository.save(signUpRequestDto.toEntity(signUpRequestDto));
        return true;
    }


}
