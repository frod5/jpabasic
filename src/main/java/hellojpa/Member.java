package hellojpa;

import javax.persistence.*;
import java.util.Date;

//JPA Entity는 Java reflection을 사용해서 기본생성자를 넣어주어야한다.
//@SequenceGenerator(name = "MEMBER_SEQ_GENERATOR", sequenceName = "MEMBER_SEQ")
//@SequenceGenerator(  시퀀스 전략 사용시. 하지않으면 hibernate_sequence이란 이름으로 생성됌.
// name = “MEMBER_SEQ_GENERATOR",
// sequenceName = “MEMBER_SEQ", //매핑할 데이터베이스 시퀀스 이름
// initialValue = 1, allocationSize = 1)
//@TableGenerator(
//        name = "MEMBER_SEQ_GENERATOR",
//        table = "MY_SEQUENCES",
//        pkColumnValue = "MEMBER_SEQ", allocationSize = 1)
@Entity
public class Member {

    //자동 생성(@GeneratedValue)
    //• IDENTITY: 데이터베이스에 위임, MYSQL
    //• SEQUENCE: 데이터베이스 시퀀스 오브젝트 사용, ORACLE
    //• @SequenceGenerator 필요
    //• TABLE: 키 생성용 테이블 사용, 모든 DB에서 사용
    //• @TableGenerator 필요
    //• AUTO: 방언에 따라 자동 지정, 기본값


//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR") //기본키 시퀀스 전략 사용
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MEMBER_SEQ_GENERATOR") //기본키 테이블 전략 사용 , 모든데이터 베이스에서 사용가능하지만, 성능이슈
    @Id //기본키 직접 할당할떄는 @Id만 사용.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 자동생성 (기본키 생성을 데이터베이스에 위임)
    private Long id;
    @Column(name = "name")
    private String username;
    private Integer age;
    @Enumerated(EnumType.STRING)
    private RoleType roleType;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;
    @Lob
    private String description;
    @Transient  // DB컬럼으로 사용안함.
    private int test;

    public Member() {
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }
}
