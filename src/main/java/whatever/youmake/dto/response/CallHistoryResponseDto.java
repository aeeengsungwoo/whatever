package whatever.youmake.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@RequiredArgsConstructor
public class CallHistoryResponseDto {

    private Long callId;
    private LocalDateTime createAt;
    private List<HistoryListDto> callHistoryList;

    public CallHistoryResponseDto(Long callId, LocalDateTime createAt, List<HistoryListDto> callHistoryList) {
        this.callId = callId;
        this.createAt = createAt;
        this.callHistoryList = callHistoryList;
    }
}
