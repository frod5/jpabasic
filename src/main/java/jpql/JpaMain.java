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

            em.flush();
            em.clear();

//            String query = "select m from Member2 m";  // N+1 문제 발생

            //fetch join 사용
//            String query = "select m from Member2 m join fetch m.team";


//            String query = "select distinct t from Team2 t join fetch t.members";
            //distinct를 사용하면 SQL에서도 distinct가 실행되지만 DB에서는 distinct를 한다고해서 데이터가 줄지 않는다.
            //애플리케이션에서 같은 식별자를 가진 엔티티를 제거해준다.


            //일반 조인과 fetch join의 차이
            //일반 조인 실행시 연관된 엔티티를 함께 조회하지 않음
            //실제로 조인은 하지만 select절에서 team의 값만 가져와서 해당 값을 사용(getTeam().getName()) 할때 Member를 또 조회해온다.
            //fetch 조인을 사용할 때만 연관된 엔티티도 함께 조회(즉시로딩)
            //fetch 조인은 객체 그래프를 SQL 한번에 조회하는 개념.

            //fetch 조인의 특징과 한계
            //원칙적으론 join t.members m 별칭 사용을 못하지만 hibernate는 가능. 가급적 사용x
            //둘 이상의 컬렉션은 fetch join 할 수 없다.
            //컬렉션을 fetch join하면 페이징 API를 사용할 수 없다.
            // -> 일대일, 다대다 같은 단일 연관 필드들은 fetch join 해도 페이징 가능
            // -> 하이버네이트는 경고 로그를 남기고 메모리에서 페이징 (매우 위험)

            //연관된 엔티티들을 SQL 한번으로 조회. -> 성능 최적화
            //엔티티에 직접 적용하는 글로벌 로딩 전략보다 우선함. -> @OneToMany(fetch = FetchType.LAZY) // 글로벌 로딩 전략
            //실무에서는 글로벌 로딩 모두 지연 로딩
            //최적화가 필요한 곳은 fetch join 적용.
            String query = "select t from Team2 t join t.members m";


            List<Team2> result = em.createQuery(query, Team2.class).getResultList();
            for (Team2 team : result) {
                System.out.println("team = " + team.getName() + "|" + team.getMembers().size());
            }

            //fetch 조인 정리
            //모든 것을 페치조인으로 해결할 수는 없다.
            //페치 조인은 객체 그래프를 유지할 때 사용하면 효과적
            //여러 테이블을 조인해서 엔티티가 가진 모양이 아닌 전혀 다른
            //결과를 내야 하면, 페치 조인 보다는 일반 조인을 사용하고 필요
            //한 데이터들만 조회해서 DTO로 반환하는 것이 효과적

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
