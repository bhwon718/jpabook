package jpabook.model;

import jpabook.model.entity.Member;
import jpabook.model.entity.Team;

import javax.persistence.*;

/**
 * Created by 1001218 on 15. 4. 5..
 */
public class Main {

    public static void main(String[] args) {

        //엔티티 매니저 팩토리 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성

        EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

        try {
            tx.begin(); //트랜잭션 시작

//            Member member = em.find(Member.class,  "member1");
            Member member = em.getReference(Member.class,  "member1");

            System.out.println(em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(member));
            System.out.println("member.toString() = " + member.toString());

            System.out.println(em.getEntityManagerFactory().getPersistenceUnitUtil().isLoaded(member));
          /*  PersistenceUnitUtil

            System.out.println("사용전");

            System.out.println("회원이름 : "+member.getUsername());
            Team team = member.getTeam();
            System.out.println("소속팀 : "+team.getName());*/

            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

}
