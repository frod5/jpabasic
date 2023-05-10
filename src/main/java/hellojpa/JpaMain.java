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
            Member1 member = new Member1();
            member.setUsername("hello");
            member.setHomeAddress(new Address("homeCity","street","10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("피자");
            member.getFavoriteFoods().add("족발");

            member.getAddressHistory().add(new AddressEntity("old1","street","10000"));
            member.getAddressHistory().add(new AddressEntity("old2","street","10000"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("=============");
            Member1 findMember = em.find(Member1.class, member.getId());  //값 타입 컬렉션 -> default 지연로딩

            //치킨 -> 한식
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            //주소히스토리 변경
            findMember.getAddressHistory().remove(new AddressEntity("old1","street","10000")); //컬렉션은 같은객체를 찾는것을 .equals로 찾기 떄문에 equals를 오버라이딩을 잘해주어야한다.
            findMember.getAddressHistory().add(new AddressEntity("newCity1","street","10000"));

            System.out.println("=============");

            //값 타입 컬렉션은 영속성 전에(Cascade) + 고아 객체 제거 기능을 필수로 가진다고 볼 수 있다.
            //값 타입 컬렉션의 제약사항
            //• 값 타입은 엔티티와 다르게 식별자 개념이 없다.
            //• 값은 변경하면 추적이 어렵다.
            //• 값 타입 컬렉션에 변경 사항이 발생하면, 주인 엔티티와 연관된 모든 데이터를 삭제하고, 값 타입 컬렉션에 있는 현재 값을 모두 다시 저장한다.
            //• 값 타입 컬렉션을 매핑하는 테이블은 모든 컬럼을 묶어서 기본키를 구성해야 함: null 입력X, 중복 저장X

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
