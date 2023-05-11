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

            for (int i=0; i<100; i++) {
                Member2 member = new Member2();
                member.setUsername("member"+i);
                member.setAge(i);
                em.persist(member);
            }

            List<Member2> result = em.createQuery("select m from Member2 m order by m.age desc", Member2.class)
                    .setFirstResult(0)  //조회 시작 위치(0부터 시작)
                    .setMaxResults(10) //조회할 데이터 수
                    .getResultList();

            System.out.println("result.size = " + result.size());

            for (Member2 member2 : result) {
                System.out.println("member2 = " + member2);
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
