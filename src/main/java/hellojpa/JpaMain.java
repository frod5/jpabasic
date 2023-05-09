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

            //Team 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            //Member 저장
            Member1 member1 = new Member1();
            member1.setUsername("Member1");

            //순수한 객체 관계를 고려하면 항상 양쪽다 값을 입력해야 한다.
            //team.getMembers().add(member1); // 역방향(주인이 아닌 방향)만 연관관계 설정. db에 insert되지않음 mapped by는 읽기전용 가짜매핑이기 떄문이다.
            member1.changeTeam(team); //양방향 매핑시 연관관계의 주인에 값을 입력해야 한다. 연관관계의 주인에 값 설정.

            em.persist(member1);

//            team.addMember(member1);

            //양방향 연관관계 주의 - 실습
            //• 순수 객체 상태를 고려해서 항상 양쪽에 값을 설정하자
            //• 연관관계 편의 메소드를 생성하자 ex) changeTeam() 또는 team.addMember() 둘중에 하나를 만들어서 양쪽다 값을 입력하도록.
            //• 양방향 매핑시에 무한 루프를 조심하자
            //• 예: toString(), lombok, JSON 생성 라이브러리

            //select 쿼리를 보기위해 영속성 컨텍스트 초기화.
//            em.flush();
//            em.clear();

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
