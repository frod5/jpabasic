package jpql;

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
            Member2 member = new Member2();
            member.setUsername("member1");
            member.setAge(10);

            em.persist(member);

            //프로젝션(SELECT)
            //select m from Member2 m -> 엔티티 프로젝션 (모두 영속성 컨텍스트로 관리된다. em.find와 같이 리스트의 갯수가 20개면 20개 모두 관리)
            //• SELECT m.team FROM Member m -> 엔티티 프로젝션
            //• SELECT m.address FROM Member m -> 임베디드 타입 프로젝션
            //• SELECT m.username, m.age FROM Member m -> 스칼라 타입 프로젝션
            List<Member2> result = em.createQuery("select m from Member2 m", Member2.class).getResultList();

            Member2 findMember = result.get(0);
            findMember.setAge(20);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            //EntityManager 종료
            em.close();
        }
        //EntityManagerFactory 종료
        emf.close();
    }
}
