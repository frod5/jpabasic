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

//            String query = "select m from Member2 m";  // N+1 문제 발생

            //fetch join 사용
//            String query = "select m from Member2 m join fetch m.team";


//            String query = "select distinct t from Team2 t join fetch t.members";
            //distinct를 사용하면 SQL에서도 distinct가 실행되지만 DB에서는 distinct를 한다고해서 데이터가 줄지 않는다.
            //애플리케이션에서 같은 식별자를 가진 엔티티를 제거해준다.


            //일반 조인과 fetch join의 차이
            //일반 조인 실행시 연관된 엔티티를 함께 조회하지 않음
            //실제로 조인은 하지만 select절에서 team의 값만 가져와서 해당 값을 사용(getTeam().getName()) 할때 Member를 또 조회해온다.
            //fetch 조인을 사용할 때만 연관된 엔티티도 함께 조회(즉시로딩)
            //fetch 조인은 객체 그래프를 SQL 한번에 조회하는 개념.
            String query = "select t from Team2 t join t.members m";



            List<Team2> result = em.createQuery(query, Team2.class).getResultList();
            for (Team2 team : result) {
                System.out.println("team = " + team.getName() + "|" + team.getMembers().size());
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
