package jpabook.model.entity;

import javax.persistence.*;

/**
 * Created by holyeye on 2014. 3. 11..
 */
@Entity
public class Member {

    public Member() {
    }

    public Member(String id, String username, int age) {
        this.id = id;
        this.username = username;
        this.age = age;

    }


    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    private String username;

    private int age;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        //기존 팀과 관계를 제거
        if (this.team != null) {
            this.team.getMemberList().remove(this);
        }

        this.team = team;
        //무한 루프에 빠지지 않도록 체크
        if (!team.getMemberList().contains(this)) {
            team.getMemberList().add(this);
        }
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
