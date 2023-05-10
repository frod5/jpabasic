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
            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);  // parent만 persist해도 child까지 persist된다. cascade = CascadeType.ALL

            //영속성 전이: CASCADE - 주의!
            //• 영속성 전이는 연관관계를 매핑하는 것과 아무 관련이 없음
            //• 엔티티를 영속화할 때 연관된 엔티티도 함께 영속화하는 편리함을 제공할 뿐

            em.flush();
            em.clear();

            //고아객체 - 참조가 제거된 엔티티는 다른 곳에서 참조하지 않는 고아객체로 보고 삭제하는 기능.
            //참조하는곳이 하나일때 (Child가 Parent외에 다른곳에 쓰이면 안됨)
            //특정 엔티티가 개인 소유할때 사용
            //@OneToOne, @OneToMany만 사용 가능.
            Parent findParent = em.find(Parent.class, parent.getId());
            findParent.getChildList().remove(0);  // orphanRemoval = true설정시, delete쿼리가 나간다.

            //영속성 전이 + 고아 객체, 생명주기
            //• CascadeType.ALL + orphanRemoval=true
            //• 스스로 생명주기를 관리하는 엔티티는 em.persist()로 영속화, em.remove()로 제거
            //• 두 옵션을 모두 활성화 하면 부모 엔티티를 통해서 자식의 생명주기를 관리할 수 있음
            //• 도메인 주도 설계(DDD)의 Aggregate Root개념을 구현할 때 유용

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
