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

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Team team1 = new Team();
            team1.setName("teamB");
            em.persist(team1);

            Member1 member1 = new Member1();
            member1.setUsername("hello");
            member1.setTeam(team);
            em.persist(member1);

            Member1 member2 = new Member1();
            member2.setUsername("hello2");
            member2.setTeam(team1);
            em.persist(member2);

            em.flush();
            em.clear();

            //em.find
//            Member1 findMember = em.find(Member1.class, member1.getId());

//            System.out.println("findMember.team class = " + findMember.getTeam().getClass());   // 지연로딩인 경우에는 프록시 사용.

//            System.out.println("=================");
//            System.out.println("findMember.team.name = " + findMember.getTeam().getName());  //지연로딩인 경우 team.getTeam.getName 순간에 쿼리가 날라간다.
//            System.out.println("=================");

            //JPQL에서 즉시로딩시 N+1 문제 실습
            //N+1은 1이 하나의 쿼리로 N개의 쿼리가 나간다는것.
            List<Member1> result = em.createQuery("select m from Member1 m join fetch m.team", Member1.class)
                    .getResultList();
            // 즉시로딩인데도 불구하고, 조인을 하지않고 member,team 테이블 쿼리가 2개가 날라간다.
            // em.find처럼 키를 가지고 하는것이 아니기 때문에, member의 갯수대로 team을 select하는 쿼리가 나간다.

            //N+1 해결방법 3가지
            //일단 지연로딩으로 설정.
            //1. JPQL- fetchJoin ex) select m from Member1 m join fetch m.team. 대부분 이것으로 해결
            //2. entity 그래프 어노테이션
            //3. 배치사이즈 N+1 -> 1+1로 해결

            //@ManyToOne, @OneToOne은 기본 패치타입이 EAGER여서 LAZY로 변경해 주어야한다.
            //@OneToMany, @ManyToMany는 기본 패치타입이 LAZY


            //em.getReference()  select 쿼리조회 X sout으로 찍기 전까지는
//            Member1 findMember = em.getReference(Member1.class, member1.getId());
//            System.out.println("findMember.getClass = " + findMember.getClass());
//            System.out.println("findMember.id = " + findMember.getId());
//            System.out.println("findMember.username = " + findMember.getUsername());

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
