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
           member.setUsername("A");
           member.setRoleType(RoleType.USER);

           System.out.println("===============1");
           em.persist(member);
           System.out.println("===============2");

           //기본키 전략 - IDENTITY의 특징.
           //• JPA는 보통 트랜잭션 커밋 시점에 INSERT SQL 실행
           //• AUTO_ INCREMENT는 데이터베이스에 INSERT SQL을 실행한 이후에 ID 값을 알 수 있음
           //• IDENTITY 전략은 em.persist() 시점에 즉시 INSERT SQL 실행하고 DB에서 식별자를 조회

            //트랜잭션 커밋, 커밋을 해야 쿼리가 날라간다. 예외적으로 기본키생성전략이 IDENTITY일떄는 em.persist()시 쿼리가 날라간다.
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
