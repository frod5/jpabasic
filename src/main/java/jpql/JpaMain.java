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
            Team2 team = new Team2();
            team.setName("teamA");
            em.persist(team);

            Member2 member = new Member2();
            member.setUsername("관리자");
            member.setAge(10);
            member.changeTeam(team);
            em.persist(member);

            Member2 member1 = new Member2();
            member1.setUsername("관리자2");
            member1.setAge(10);
            member1.changeTeam(team);
            em.persist(member1);

            //경로 표현식
//            String query = "select m.username from Member2 m"; //상태 필드 - 경로 탐색의 끝, 탐색X
//            String query = "select m.team from Member2 m"; //단일값 연관 경로 - 묵시적 내부조인(inner) 발생, 탐색O. select m.team.name 이런식으로
            String query = "select m.username from Team2 t join t.members m"; //컬렉션 연관 경로 - 묵시적 내부조인(inner) 발생, 탐색X. from절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색가능

            //묵시적 조인은 사용하지 말자. 묵시적 조인은 항상 inner join
            //명시적 조인 사용 - 명시적 조인은 직접 join문 작성.

            Collection resultList = em.createQuery(query, Collection.class).getResultList();
            for (Object o : resultList) {
                System.out.println("o = " + o);
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
