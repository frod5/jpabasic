package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ElementCollection // 값타입을 하나이상 저장할때 사용. 값타입 컬렉션,  값 타입 컬렉션은 default 지연로딩
    @CollectionTable(name = "FAVORITE_FOOD", joinColumns = @JoinColumn(name = "MEMBER_ID"))
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods = new HashSet<>();

//    @ElementCollection
//    @CollectionTable(name = "ADDRESS", joinColumns = @JoinColumn(name = "MEMBER_ID"))
//    private List<Address> addressHistory = new ArrayList<>();


    //값타입 컬렉션 대안 -> 일대다 Entity로 만들고 Entity 안에서 값 타입 사용.
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "MEMBER_ID")
    private List<AddressEntity> addressHistory = new ArrayList<>();

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

    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

//    public List<Address> getAddressHistory() {
//        return addressHistory;
//    }
//
//    public void setAddressHistory(List<Address> addressHistory) {
//        this.addressHistory = addressHistory;
//    }


    public List<AddressEntity> getAddressHistory() {
        return addressHistory;
    }

    public void setAddressHistory(List<AddressEntity> addressHistory) {
        this.addressHistory = addressHistory;
    }

    public Address getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(Address workAddress) {
        this.workAddress = workAddress;
    }
}
