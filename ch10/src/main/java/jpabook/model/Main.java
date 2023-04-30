package jpabook.model;

import jpabook.model.entity.Member;
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

            Main.예제10_2(em);

            Main.예제10_8(em);

            Main.예제10_10(em);

            Main.예제10_11(em);

            Main.예제10_15(em);
            Main.예제10_17(em);
            Main.예제10_18(em);

            Main.집합함수(em);

            Main.예제10_24(em);


            Main.예제10_28(em);
            em.flush();
            Main.예제10_28_2(em);

            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }


    public static void 예제10_2(EntityManager em) {
        System.out.println("예제10_2 시작");
        String jpql = "select m from Member as m where m.username = '멤버1'";
        List<Member> resultList = em.createQuery(jpql).getResultList();
        System.out.println(resultList);
        System.out.println("예제10_2 끝");
    }

    //Type Query 사용
    public static void 예제10_8(EntityManager em) {
        System.out.println("예제10_8 시작");

        TypedQuery<Member> query = em.createQuery("select m from Member as m", Member.class);

        List<Member> resultList = query.getResultList();
        System.out.println(resultList);
        System.out.println("예제10_8 끝");
    }


    //이름 기준 파라미터
    public static void 예제10_10(EntityManager em) {
        System.out.println("예제10_10 시작");

        TypedQuery<Member> query = em.createQuery("select m from Member as m where m.username=:username", Member.class);
        query.setParameter("username", "멤버1");
        List<Member> resultList = query.getResultList();
        System.out.println(resultList);
        System.out.println("예제10_10 끝");
    }

    //이름 기준 파라미터
    public static void 예제10_11(EntityManager em) {
        System.out.println("예제10_11 시작");

        TypedQuery<Member> query = em.createQuery("select m from Member as m where m.username=?1", Member.class);
        query.setParameter(1, "멤버1");
        List<Member> resultList = query.getResultList();
        System.out.println(resultList);
        System.out.println("예제10_11 끝");
    }


    //new 명령어 사용전
    public static void 예제10_15(EntityManager em) {
        System.out.println("예제10_15 시작");

        List<Object[]> resultList = em.createQuery("select m.username, m.age from Member as m").getResultList();

        //객체 변환작업
        List<UserDTO> userDTOS = new ArrayList<UserDTO>();
        for (Object[] row : resultList) {
            userDTOS.add(new UserDTO((String) row[0], (Integer) row[1]));
        }
        System.out.println(userDTOS);
        System.out.println("예제10_15 끝");
    }

    //new 명령어 사용
    public static void 예제10_17(EntityManager em) {
        System.out.println("예제10_17 시작");
        TypedQuery<UserDTO> query = em.createQuery("select new  jpabook.model.UserDTO(m.username, m.age) from Member as m", UserDTO.class);
        List<UserDTO> userDTOS = query.getResultList();
        System.out.println(userDTOS);
        System.out.println("예제10_17 끝");
    }

    //페이징 사용
    public static void 예제10_18(EntityManager em) {
        System.out.println("예제10_18 시작");
        TypedQuery<Member> query = em.createQuery("select m from Member as m ORDER BY m.username DESC", Member.class);

        query.setFirstResult(0);
        query.setMaxResults(2);

        List<Member> memberList = query.getResultList();
        System.out.println(memberList);
        System.out.println("예제10_18 끝");
    }

    //집합함수
    public static void 집합함수(EntityManager em) {
        System.out.println("집합함수 시작");

        String qlString = """
                   select COUNT(m),
                       SUM(m.age),
                       AVG(m.age),
                       MAX(m.age),
                       MIN(m.age)
                   from Member m
                """;

        Query query = em.createQuery(qlString);


        List<Object[]> resultList = query.getResultList();
        ;

        for (Object[] obj : resultList) {

            for (Object o : obj) {
                System.out.print(o.toString() + " ,");
            }
            System.out.println();
        }
        System.out.println("집합함수 끝");
    }


    //Inner Join
    public static void 예제10_24(EntityManager em) {

        System.out.println("예제10_24 시작");


        String qlString = """
                   SELECT m
                   from Member m
                   join m.team t
                   where t.name = :teamName
                """;

        List<Member> members = em.createQuery(qlString, Member.class)
                .setParameter("teamName", "팀1")
                .getResultList();
        System.out.println(members);
        System.out.println("예제10_24 끝");
    }



    //페치 조인
    public static void 예제10_28(EntityManager em) {

        System.out.println("예제10_28 시작");


        String qlString = """
                   SELECT m
                   from Member m
                   join fetch m.team t
                """;

        List<Member> members = em.createQuery(qlString, Member.class)
                .getResultList();


        for (Member member : members) {
            //페치 조인으로 회원과 팀을 한번에 조회하기 떄문에 지연 로딩 발생 안함
            System.out.printf("userName = %s , teamName= %s %n",member.getUsername(), member.getTeam().getName());

        }


        System.out.println("예제10_28 끝");
    }


    //페치 조인
    public static void 예제10_28_2(EntityManager em) {

        System.out.println("예제10_28_2 시작");


        String qlString = """
                   SELECT m
                   from Member m
                """;

        List<Member> members = em.createQuery(qlString, Member.class)
                .getResultList();


        for (Member member : members) {
            //페치 조인을 안할경우 지연로딩 발생
            System.out.printf("userName = %s , teamName= %s %n",member.getUsername(), member.getTeam().getName());

        }


        System.out.println("예제10_28_2 끝");
    }



}
