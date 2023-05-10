package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;

//JPA Entity는 Java reflection을 사용해서 기본생성자를 넣어주어야한다.
@Entity
public class Member1 extends BaseEntity1 {

    @Id //기본키 직접 할당할떄는 @Id만 사용.
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    @Column(name = "USERNAME")
    private String username;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    //Period 임베디드 타입 사용, @Embedded - 값 타입을 사용하는 곳
    @Embedded
    private Period workPeriod;

    //Address
    @Embedded
    private Address homeAddress;

    //Address 같은 Address 사용하고 싶은경우 기존 컬럼명과 다른컬렴명 Override하여 사용.
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column =@Column(name = "WORK_CITY")),
            @AttributeOverride(name = "street", column =@Column(name = "WORK_STREET")),
            @AttributeOverride(name = "zipcode", column =@Column(name = "WORK_ZIPCODE"))
    })
    private Address workAddress;

    @ManyToOne(fetch = FetchType.LAZY)  //연관관계 매핑 , ManyToOne의 default fetch타입은 EAGER, 외래키가 있는곳을 연관관계의 주인으로 설정하는 것이 좋다.
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void changeTeam(Team team) {
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

    public Period getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Period workPeriod) {
        this.workPeriod = workPeriod;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }
}
