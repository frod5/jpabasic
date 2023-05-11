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

            Member2 member1 = new Member2();
//            member.setUsername("member1");
//            member.setUsername(null);
            member1.setUsername("관리자2");
            member1.setAge(10);
            member1.changeTeam(team);
            em.persist(member1);


            //JPQL 함수 실습
//            String query = "select 'a' || 'b' from Member2 m";  //hibernate 구현체로 하는 방법
//            String query = "select concat('a', 'b') from Member2 m";  // concat()
//            String query = "select substring(m.username,2,3) from Member2 m";  // substring()
            //trim(), lower(), upper(), length() 등등..

//            String query = "select locate('de','abcdefg') from Member2 m";  // locate() de가 들어있는 위치 반환 (Integer)
//            String query = "select size(t.members) from Team2 t";  // size()
            String query = "select function('group_concat', m.username) from Member2 m";  // 사용자 정의 함수 사용 MyH2Dialect 만들고, persistence.xml 수정 후. select group_concat(m.username) from Member2 m 도 hibernate구현체에서는 가능.

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
