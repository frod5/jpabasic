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
            Member memberA = new Member();
            memberA.setId(4L);
            memberA.setName("hello4");

            Member memberB = new Member();
            memberB.setId(5L);
            memberB.setName("hello5");

            em.persist(memberA);
            em.persist(memberB);
            System.out.println("==================");
            //여기까지 insert SQL을 날리지 않는다.
            //쓰기지연 SQL저장소에 쿼리를 모아둔다.

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
