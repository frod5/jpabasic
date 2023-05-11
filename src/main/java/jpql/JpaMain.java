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
            Team2 team = new Team2();
            team.setName("teamA");
            em.persist(team);

            Member2 member = new Member2();
//            member.setUsername("member1");
//            member.setUsername(null);
            member.setUsername("관리자");
            member.setAge(10);
            member.changeTeam(team);
            em.persist(member);

            //CASE 실습
            /*String query = "select " +
                                "case when m.age <= 10 then '학생요금'" +
                                "     when m.age >= 60 then '경로요금'" +
                                "     else '일반요금'" +
                    "END " +
                    "from Member2 m";*/

            //coalesce 실습
//            String query = "select coalesce(m.username,'이름 없는 회원') from Member2 m";

            //nullif 실습 - 두 값이 같으면 null 반환, 다르면 첫번째 값 반환
            String query = "select nullif(m.username,'관리자') from Member2 m";

            List<String> resultList = em.createQuery(query, String.class).getResultList();
            for (String s : resultList) {
                System.out.println("s = " + s);
            }

//            List<Member2> result = em.createQuery(query, Member2.class)
//                    .getResultList();

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
