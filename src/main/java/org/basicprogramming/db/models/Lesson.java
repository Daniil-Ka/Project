package org.basicprogramming.db.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Lesson implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column()
    private String groupName;

    public Lesson() { }

    public Lesson(String groupName) {
        this.groupName = groupName;
    }

    public Integer getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
