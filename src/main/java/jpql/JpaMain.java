package jpql;

import javax.persistence.*;
import java.util.Collection;
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
            Team2 team1 = new Team2();
            team1.setName("팀A");
            em.persist(team1);

            Team2 team2 = new Team2();
            team2.setName("팀B");
            em.persist(team2);

            Member2 member1 = new Member2();
            member1.setUsername("회원1");
            member1.setTeam(team1);
            em.persist(member1);

            Member2 member2 = new Member2();
            member2.setUsername("회원2");
            member2.setTeam(team1);
            em.persist(member2);

            Member2 member3 = new Member2();
            member3.setUsername("회원3");
            member3.setTeam(team2);
            em.persist(member3);

            em.flush();
            em.clear();

            List<Member2> result = em.createNamedQuery("Member.findByUsername", Member2.class)
                    .setParameter("username", member1.getUsername())
                    .getResultList();
            for (Member2 m : result) {
                System.out.println("m = " + m);
            }

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
