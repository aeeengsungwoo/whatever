package whatever.youmake.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import whatever.youmake.domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CallHistoryRepository extends JpaRepository<CallHistory,Long> {
    List<CallHistory> findByCallId(Long callId);

}
