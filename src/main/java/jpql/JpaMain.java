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


            //벌크 연산
            //• 재고가 10개 미만인 모든 상품의 가격을 10% 상승하려면?
            //• JPA 변경 감지 기능으로 실행하려면 너무 많은 SQL 실행
            //• 1. 재고가 10개 미만인 상품을 리스트로 조회한다.
            //• 2. 상품 엔티티의 가격을 10% 증가한다.
            //• 3. 트랜잭션 커밋 시점에 변경감지가 동작한다.
            //• 변경된 데이터가 100건이라면 100번의 UPDATE SQL 실행

            // 쿼리 한 번으로 여러 테이블 로우 변경(엔티티)
            //• executeUpdate()의 결과는 영향받은 엔티티 수 반환
            //• UPDATE, DELETE 지원
            // INSERT(insert into .. select, 하이버네이트 지원. jpa 표준 아님)

            //FLUSH 자동 호출
            int resultCount = em.createQuery("update Member2 m set m.age = 20")
                    .executeUpdate();
            System.out.println("resultCount = " + resultCount);

            int age1 = member1.getAge();
            System.out.println("age = " + age1); //0으로 나온다. 20으로 벌크연산을 했지만, 영속성 컨텍스트엔 반영 X

            em.clear(); //영속성 초기화
            Member2 findMember = em.find(Member2.class, member1.getId()); //영속성 초기화

            int age2 = findMember.getAge();
            System.out.println("age2 = " + age2);

            //벌크 연산 주의
            //• 벌크 연산은 영속성 컨텍스트를 무시하고 데이터베이스에 직접쿼리
            //• 벌크 연산을 먼저 실행
            //• 벌크 연산 수행 후 영속성 컨텍스트 초기화
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
