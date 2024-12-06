package whatever.youmake.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import whatever.youmake.dto.request.CallRequestDto;
import whatever.youmake.dto.response.CallResponseDto;
import whatever.youmake.dto.response.CallStartResponseDto;
import whatever.youmake.dto.response.ResponseDto;
import whatever.youmake.service.CallService;

@RestController
@RequiredArgsConstructor
public class CallController {

    private final CallService callService;

    @PostMapping("/call/start/{userId}")
    public ResponseDto<CallStartResponseDto> callStart(@PathVariable Long userId){
        return new ResponseDto<>(callService.startCall(userId));
    }


    @PostMapping("/call")
    public ResponseDto<CallResponseDto> callAsync(@RequestBody CallRequestDto callRequestDto){
        return new ResponseDto<>(callService.callAsync(callRequestDto));
    }
}
