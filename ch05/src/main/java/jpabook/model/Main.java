package jpabook.model;

import jpabook.model.entity.Member;
import jpabook.model.entity.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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
       /*
            Team team = em.find(Team.class, "team1");
            em.remove(team);

            Member member = em.find(Member.class, "member1");
            em.remove(member);

*/

//            Team team1 = new Team("team1","팀1");
//            em.persist(team1);
//
//            Member member1 = new Member("member1","멤버1");
//            member1.setTeam(team1);
//            em.persist(member1);
//
//            Member member2 = new Member("member2","멤버2");
//            member2.setTeam(team1);
//            em.persist(member2);


//
/*
            Member member = em.find(Member.class, "member1");
            System.out.println(member.toString());

            System.out.println(member.getTeam().toString());
*/

            Team team = em.find(Team.class,"team1");
            System.out.println(team);

            System.out.println(team.getMemberList());

            tx.commit();//트랜잭션 커밋

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

}
