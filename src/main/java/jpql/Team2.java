package jpql;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team2 {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

//    @BatchSize(size = 100) //N+1 해결을 위해 사용. 사용하면 1번 select 실행 시 사이즈 크기로 IN () 절에 담아 조회. ex) N이 200일때, 100개씩 IN절에 담아 조회해서 select 쿼리가 2번만 나간다.
    @OneToMany(mappedBy = "team")
    private List<Member2> members = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member2> getMembers() {
        return members;
    }

    public void setMembers(List<Member2> members) {
        this.members = members;
    }
}
