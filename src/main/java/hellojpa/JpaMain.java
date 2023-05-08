package hellojpa;

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
            //연관 관계 없을 떄

            //Team 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);


            //Member 저장
            Member1 member1 = new Member1();
            member1.setUsername("Member1");
            member1.setTeam(team);
//            member1.setTeamId(team.getId());
            em.persist(member1);


            //select 쿼리를 보기위해 영속성 컨텍스트 초기화.
            em.flush();
            em.clear();

            //member 조회
            Member1 findMember = em.find(Member1.class, member1.getId());

            //member가 소속된 team 조회
            Team findTeam = findMember.getTeam();
            System.out.println("findTeam = " + findTeam.getName());

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
