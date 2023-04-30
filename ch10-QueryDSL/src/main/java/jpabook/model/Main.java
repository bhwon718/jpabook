package jpabook.model;

import com.mysema.query.SearchResults;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Projections;
import jpabook.model.entity.Member;
import jpabook.model.entity.QMember;
import jpabook.model.entity.Team;
import org.h2.engine.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

            Team team = new Team("team1", "팀1");
            em.persist(team);

            Team team2 = new Team("team2", "팀2");
            em.persist(team2);


            Member member = new Member("member1", "멤버1", 19);
            member.setTeam(team);
            em.persist(member);

            Member member2 = new Member("member2", "멤버2", 21);
            member2.setTeam(team);
            em.persist(member2);

            Member member3 = new Member("member3", "멤버3", 36);
            member3.setTeam(team);
            em.persist(member3);

            Member member4 = new Member("member4", "멤버4", 50);
            member4.setTeam(team2);
            em.persist(member4);

            Member member5 = new Member("member5", "멤버5", 45);
            member5.setTeam(team2);
            em.persist(member5);

            em.flush();

            Main.예제10_79(em);

            Main.예제10_85(em);

            Main.예제10_87(em);
            Main.예제10_97(em);

            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }


    //JPA 시작
    public static void 예제10_79(EntityManager em) {
        System.out.println("예제10_79 시작");

        JPAQuery query = new JPAQuery(em);
        QMember qMember = new QMember("m");     //직접지정
//        QMember qMember = QMember.member;        //기본 인스턴스 사용
        List<Member> members = query.from(qMember)
//                .where(qMember.username.eq("멤버2"))
//                .where(qMember.age.gt(30))
                .where(qMember.username.endsWith("버4").and(qMember.age.gt(30)))
                .orderBy(qMember.username.desc())
                .list(qMember);

        System.out.println(members);
        System.out.println("예제10_79 끝");
    }

    public static void 예제10_85(EntityManager em) {
        System.out.println("예제10_85 시작");

        JPAQuery query = new JPAQuery(em);
        QMember qMember = new QMember("m");     //직접지정
        //        QMember qMember = QMember.member;        //기본 인스턴스 사용
        List<Member> members = query.from(qMember)
                //                .where(qMember.username.eq("멤버2"))
                //                .where(qMember.age.gt(30))
                .orderBy(qMember.username.desc())
                .offset(1).limit(5)
                .list(qMember);

        System.out.println(members);
        System.out.println("예제10_85 끝");
    }


    public static void 예제10_87(EntityManager em) {
        System.out.println("예제10_87 시작");

        JPAQuery query = new JPAQuery(em);
        QMember qMember = new QMember("m");     //직접지정
        //        QMember qMember = QMember.member;        //기본 인스턴스 사용
        SearchResults<Member> result = query.from(qMember)
                //                .where(qMember.username.eq("멤버2"))
                //                .where(qMember.age.gt(30))
                .orderBy(qMember.username.desc())
                .offset(1).limit(4)
                .listResults(qMember);

        long total = result.getTotal();
        long limit = result.getLimit();
        long offset = result.getOffset();

        System.out.printf("total :%d, limit: %d, offset : %d %n", total, limit, offset);
        System.out.println(result.getResults());
        System.out.println("예제10_87 끝");
    }



    //DTO 결과반환
    public static void 예제10_97(EntityManager em) {
        System.out.println("예제10_97 시작");

        JPAQuery query = new JPAQuery(em);
        QMember qMember = new QMember("m");     //직접지정

        List<UserDTO> result = query.from(qMember).list(

                //프로퍼티 접근(Setter)
                //Projections.bean(UserDTO.class, qMember.username, qMember.age )

                //필드 직접접근
//                Projections.fields(UserDTO.class, qMember.username, qMember.age )

                //생성자 사용
                Projections.constructor(UserDTO.class, qMember.username, qMember.age )

        );

        System.out.println(result);
        System.out.println("예제10_97 끝");
    }


}
