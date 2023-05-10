package hellojpa;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        //EntityManagerFactory를 META-INF/persisten.xml에서 설정정보를 가져와 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        //EntityManagerFactory를 사용해 EntityManager생성.
        EntityManager em = emf.createEntityManager();

        //모든 JPA는 트랜잭션 안에서 동작해야한다.
        //EntityManager에 있는 트랜잭션을 받아온다.
        EntityTransaction tx = em.getTransaction();

        //트랜잭션 시작
        tx.begin();

        try {

            Address address = new Address("city", "street", "zipcode");

            Member1 member1 = new Member1();
            member1.setUsername("member1");
            member1.setHomeAddress(address);  //임베디드 타입 사용
            member1.setWorkPeriod(new Period());  //임베디드 타입 사용
            em.persist(member1);

            Address address1 = new Address(address.getCity(),address.getStreet(),address.getZipcode());

            Member1 member2 = new Member1();
            member2.setUsername("member2");
            member2.setHomeAddress(address1);  //값을 복제하여 사용하여야 member1만 변경시 member1만 변경된다.
            member2.setWorkPeriod(new Period());  //임베디드 타입 사용
            em.persist(member2);

            //첫번째 멤버의 주소의 도시를 변경
            member1.getHomeAddress().setCity("newCity");

            //member1과 member2가 같은 참조값을 바라보기 때문에 의도치 않게 member2의 도시 값도 newCity로 변경된다.
            //값을 복사해서 사용해야한다. Address address1 = new Address(address.getCity(),address.getStreet(),address.getZipcode());

            //객체 타입의 한계
            //• 항상 값을 복사해서 사용하면 공유 참조로 인해 발생하는 부작용을 피할 수 있다.
            //• 문제는 임베디드 타입처럼 직접 정의한 값 타입은 자바의 기본타입이 아니라 객체 타입이다.
            //• 자바 기본 타입에 값을 대입하면 값을 복사한다.
            //• 객체 타입은 참조 값을 직접 대입하는 것을 막을 방법이 없다.
            //• 객체의 공유 참조는 피할 수 없다.

            //불변객체로 만들어주어야한다. 생성자로만 값을 설정하고 수정자(Setter)를 만들지 않으면 됨. 대표적인 불변객체 ex) 자바 Integer, String
            //setter가 없으면 변경자체를 못한다 -> 해결방법 새 객체를 만들어서 새 겍체로 변경해주어야 한다.

            //임베디드 타입과 테이블 매핑
            //• 임베디드 타입은 엔티티의 값일 뿐이다.
            //• 임베디드 타입을 사용하기 전과 후에 매핑하는 테이블은 같다.
            //• 객체와 테이블을 아주 세밀하게(find-grained) 매핑하는 것이 가능
            //• 잘 설계한 ORM 애플리케이션은 매핑한 테이블의 수보다 클래스의 수가 더 많음


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            //EntityManager 종료
            em.close();
        }
        //EntityManagerFactory 종료
        emf.close();

        //주의
        //• 엔티티 매니저 팩토리는 하나만 생성해서 애플리케이션 전체에서 공유
        //• 엔티티 매니저는 쓰레드간에 공유X (사용하고 버려야 한다).
        //• JPA의 모든 데이터 변경은 트랜잭션 안에서 실행

        //영속성 컨텍스트의 이점
        //• 1차 캐시
        //• 동일성(identity) 보장
        //• 트랜잭션을 지원하는 쓰기 지연(transactional write-behind)
        //• 변경 감지(Dirty Checking)
        //• 지연 로딩(Lazy Loading)
    }

}
