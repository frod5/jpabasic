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
            //영속성 컨텍스트 학습
            //영속성 컨텍스트란?
            //• JPA를 이해하는데 가장 중요한 용어
            //• “엔티티를 영구 저장하는 환경”이라는 뜻
            //• EntityManager.persist(entity);
            //• 영속성 컨텍스트는 논리적인 개념
            //• 눈에 보이지 않는다.
            //• 엔티티 매니저를 통해서 영속성 컨텍스트에 접근

            //EntityManager에

            //비영속 start - 단순히 객체 생성한 상태 (영속성 컨텍스트와 전혀 관계가 없는 새로운 상태)
            Member member = new Member();
            member.setId(3L);
            member.setName("helloABC");
            //비영속 end

            //영속 (DB에 저장하는것이 아닌 영속성 컨텍스트에 저장하는 것.), 영속성 컨텍스트에 관리되는 상태
            em.persist(member);

            //준영속 (영속성 컨텍스트에 저장되었다가 분리된 상태), 영속성 컨텍스트에서 지운다, 회원 엔티티를 영속성 컨텍스트에서 분리
//            em.detach(member);

            //객체를 삭제한 상태(삭제)
//            em.remove(member);

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
