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
            findMember.setName("updateName");

            //준영속
            //em.detach()를 하지않으면 영속성 컨텍스트에서 스냅샷과 비교해 달라져 변경감지 하여 update쿼리를 날릴것이다.
            //하지만 영속성 컨텍스트에서 분리하여 update쿼리가 날라가지 않는다. (select 쿼리만 날라감.)

            //준영속 상태로 만드는 방법
            // • em.detach(entity) 특정 엔티티만 준영속 상태로 전환
            //• em.clear() 영속성 컨텍스트를 완전히 초기화
            //• em.close() 영속성 컨텍스트를 종료
//            em.detach(findMember);
            em.clear();

            //clear이후 다시 조회하더라도 같은 키값의 영속성컨텍스트가 없어서 다시 select 쿼리가 날라간다.
            Member findMember2 = em.find(Member.class, 3L);

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

        //영속성 컨텍스트의 이점
        //• 1차 캐시
        //• 동일성(identity) 보장
        //• 트랜잭션을 지원하는 쓰기 지연(transactional write-behind)
        //• 변경 감지(Dirty Checking)
        //• 지연 로딩(Lazy Loading)
    }
}
