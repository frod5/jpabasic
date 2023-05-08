package hellojpa;

import javax.persistence.*;
import java.util.Date;

//JPA Entity는 Java reflection을 사용해서 기본생성자를 넣어주어야한다.
@Entity
public class Member1 {

    @Id //기본키 직접 할당할떄는 @Id만 사용.
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    @Column(name = "USERNAME")
    private String username;
    @Column(name = "TEAM_ID")
    private Long teamId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }
}
