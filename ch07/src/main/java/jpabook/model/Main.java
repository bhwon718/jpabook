package jpabook.model;

import jpabook.model.entity.Parent;
import jpabook.model.entity.Parent2;
import jpabook.model.entity.ParentId;
import jpabook.model.entity.ParentId2;

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
            Parent parent = new Parent();
            parent.setId1("myId1");
            parent.setId2("myId2");
            parent.setName("Name");
            em.persist(parent);*/


            /*ParentId parentId = new ParentId("myId1","myId2");
            Parent parent = em.find(Parent.class,parentId);
            System.out.println(parent.toString());*/

            ParentId2 parentId2 = new ParentId2("myId1","myId2");
            Parent2 parent2 = new Parent2(parentId2, "parent2");

            em.persist(parent2);





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
