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
            //멤버 객체 생성
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("helloB");

            //멤버 저장
//            em.persist(member);

            //멤버 목록 조회 (JPQL)
            //JPQL
            //• JPA를 사용하면 엔티티 객체를 중심으로 개발
            //• 문제는 검색 쿼리
            //• 검색을 할 때도 테이블이 아닌 엔티티 객체를 대상으로 검색
            //• 모든 DB 데이터를 객체로 변환해서 검색하는 것은 불가능
            //• 애플리케이션이 필요한 데이터만 DB에서 불러오려면 결국 검색 조건이 포함된 SQL이 필요

            // JPA는 SQL을 추상화한 JPQL이라는 객체 지향 쿼리 언어 제공
            //• SQL과 문법 유사, SELECT, FROM, WHERE, GROUP BY, HAVING, JOIN 지원
            //• JPQL은 엔티티 객체를 대상으로 쿼리
            //• SQL은 데이터베이스 테이블을 대상으로 쿼리
            List<Member> findMembers = em.createQuery("select m from Member m", Member.class)
                    .getResultList();

            for (Member findMember : findMembers) {
                System.out.println("findMember.name = " + findMember.getName());
            }

            //멤버 조회(단건)
//            Member findMember = em.find(Member.class, 1L);

            //멤버 수정
//            findMember.setName("helloC");

            //트랜잭션 커밋
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
    }
}
