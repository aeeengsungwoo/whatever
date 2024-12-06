package whatever.youmake.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@RequiredArgsConstructor
@Getter
@DynamicUpdate
@Table(name = "USER_TB")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userLoginId;

    @Column(nullable = false)
    private String userPassword;

    @Column(columnDefinition = "CLOB")
    private String secretKey;

    @Column(columnDefinition = "CLOB")
    private String apiToken;

//=========================================================================//

//    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
//    private List<DisabledType> disabledTypes;

    @Builder
    public User(String userLoginId, String userPassword, String secretKey, String apiToken) {
        this.userLoginId = userLoginId;
        this.userPassword = userPassword;
        this.secretKey = secretKey;
        this.apiToken = apiToken;
    }
}
