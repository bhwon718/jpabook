package jpabook.model.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Parent2 {
    @EmbeddedId
    private ParentId2 id;
    private String name;

    public Parent2() {
    }

    public Parent2(ParentId2 id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
