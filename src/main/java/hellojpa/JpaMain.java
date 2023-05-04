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
            Member findMember = em.find(Member.class, 3L);
            findMember.setName("update3L");

            //em.persist(findMember)를 해야되지 않을까??? 보통 생각하기론..
            //하지만 jpa는 find를 한 후 영속성컨텍스트에 저장된 후 .set으로 데이터를 변경한 후 commit을 하면 변경된 것을 감지하여 update쿼리를 날린다.
            //변경감지(dirty checking)

            //commit을하면 flush가 호출되고
            //스냅샷은 영속성 컨텍스트에 첨에 들어간 시점의 엔티티이다.
            //변경한 엔티티와 스냅샷을 비교한다.
            //update쿼리를 쓰기지연 sql저장소에 둔다.
            //commit할때 모아둔 쿼리가 커밋된다.


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
