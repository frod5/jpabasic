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

//            Member member = new Member();
//            member.setId(3L);
//            member.setName("helloABC");
//
//            em.persist(member);


            //id가 3인 객체를 생성 후 조회 시, 영속성에서 조회가 되서 select쿼리가 나가지않는다.
            //저장코드를 주석한 후 find하면 첫번째는 영속성 컨텍스트에 없어서 실제 쿼리를 날려 db조회를 한다.
            Member findMember1 = em.find(Member.class, 3L);
            System.out.println("findMember1.id = " + findMember1.getId());

            //같은 키값으로 조회 시, 영속성 컨텍스트에 1차캐싱되어 실제 쿼리를 날리지 않는다.
            Member findMember2 = em.find(Member.class, 3L);
            System.out.println("findMember2.id = " + findMember2.getId());

            //1차 캐시로 반복 가능한 읽기(REPEATABLE READ) 등급의 트랜잭션 격리 수준을 데이터베이스가 아닌 애플리케이션 차원에서 제공
            System.out.println("영속성 동일보장 = " + (findMember1==findMember2)); //true

            //트랜잭션 커밋, 커밋을 해야 쿼리가 날라간다.
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
    }
}
