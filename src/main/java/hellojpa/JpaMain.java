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
            Member member = new Member();
            member.setId(10L);
            member.setName("hello10L");

            em.persist(member);

            //영속성 컨텍스트의 변경내용을 데이터베이스에 반영
            // em.flush()가 호출되면,
            // 변경 감지, 수정된 엔티티 쓰기 지연 SQL 저장소에 등록, 쓰기 지연 SQL 저장소의 쿼리를 데이터베이스에 전송(등록, 수정, 삭제 쿼리)을 하게된다.

            //flush를 하는 방법
            //• em.flush() - 직접 호출
            //• 트랜잭션 커밋(tx.commit()) - 플러시 자동 호출
            //• JPQL 쿼리 실행 - 플러시 자동 호출

            //flush를 하더라도 1차캐시는 유지된다.
//            em.flush();

            //em.setFlushMode(FlushModeType.COMMIT)
            //• FlushModeType.AUTO 커밋이나 쿼리를 실행할 때 플러시 (기본값)
            //• FlushModeType.COMMIT 커밋할 때만 플러시

            //JPQL 쿼리 실행 - 플러시 자동 호출에 대한 설명
            //em.persist(memberA)
            //em.persist(memberB)
            //em.persist(memberC)

            //JPQL 쿼리 호출
            //em.createQuery("select m from Member m", Member.class);
            //List<Member> members= query.getResultList();

            //이렇게하면 memberA,memberB,memberC는 기본적으로 flush()를 호출하지 않았지만,
            //JPQL이 flush()를 호출하여, memberA,memberB,memberC에 대한 insert쿼리들이 날라간다.
            //물론 커밋전에는 데이터베이스에 반영이 되지않는다. 커밋이 된다면 db반영.


            //주의!
            //• 영속성 컨텍스트를 비우지 않음
            //• 영속성 컨텍스트의 변경내용을 데이터베이스에 동기화
            //• 트랜잭션이라는 작업 단위가 중요 -> 커밋 직전에만 동기화 하면 됨

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
