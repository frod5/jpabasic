package hellojpa;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //default는 단일테이블 전략
@DiscriminatorColumn  //ITEM1 테이블에 DTYPE이라는 컬럼생성을 해주고 자식클래스의 이름을 넣어준다. 단일테이블 전략에서는 생략해도 DTYPE이 생긴다.
public class Item1 {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int price;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
