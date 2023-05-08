package hellojpa;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team")   // mappedBy = "team" 매핑되어있는 객체(Member1)의 변수명.
    private List<Member1> members = new ArrayList<>();

    public List<Member1> getMembers() {
        return members;
    }

    public void setMembers(List<Member1> members) {
        this.members = members;
    }

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
}
