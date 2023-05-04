package hellojpa;

import javax.persistence.Entity;
import javax.persistence.Id;

//JPA Entity는 Java reflection을 사용해서 기본생성자를 넣어주어야한다.
@Entity
public class Member {

    @Id
    private Long id;
    private String name;

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
