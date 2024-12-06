package whatever.youmake.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import whatever.youmake.domain.Call;

import java.util.List;

@Repository
public interface CallRepository extends JpaRepository<Call, Long> {
    @Query("SELECT c FROM Call c WHERE c.user.id = :userId ORDER BY c.createAt desc ")
    List<Call> findByUserId(Long userId);

}
