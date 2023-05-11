package jpql;

import javax.persistence.*;

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

            TypedQuery<Member2> query1 = em.createQuery("select m from Member2 m where m.username = :username", Member2.class); //return 타입 명시에는 TypedQuery
            query1.setParameter("username",member.getUsername()); //파라미터 바인딩


//            Query query2 = em.createQuery("select m from Member2 m"); //명시 되지 않으면 Query

//            query1.getResultList();  //결과 리스트
//            query1.getSingleResult(); // 결과가 무조건 하나 일때만 사용해한다. 결과가 없거나 1개 이상 나오면 Exception발생.



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
