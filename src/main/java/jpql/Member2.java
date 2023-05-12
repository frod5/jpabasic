package jpql;

import javax.persistence.*;

@Entity
//Named 쿼리
//미리 정의해서 이름을 부여해두고 사용하는 JPQL
//• 정적 쿼리
//• 어노테이션, XML에 정의
//• 애플리케이션 로딩 시점에 초기화 후 재사용
//• 애플리케이션 로딩 시점에 쿼리를 검증
// XML이 항상 우선권을 가진다.
//• 애플리케이션 운영 환경에 따라 다른 XML을 배포할 수 있다.
// spring data jpa에서 @Query("select ~~ ") 이것이 바로 Named 쿼리이다.
@NamedQuery(name = "Member.findByUsername",
            query = "select m from Member2 m where m.username = :username"
)
public class Member2 {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team2 team;

    private int age;

    public void changeTeam(Team2 team) {
        this.team = team;
        team.getMembers().add(this);
    }

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Team2 getTeam() {
        return team;
    }

    public void setTeam(Team2 team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Member2{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
