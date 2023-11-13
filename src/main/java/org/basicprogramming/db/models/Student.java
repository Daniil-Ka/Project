package org.basicprogramming.db.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column()
    private String firstName;

    @Column()
    private String lastName;

    @Column()
    private String mail;

    @Column()
    private UUID ulearnId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="group_id")
    private Group group;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "student",
            cascade = CascadeType.ALL)
    private List<Mark> marks;

    public Group getGroup() {
        return group;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UUID getUlearnId() {
        return ulearnId;
    }

    public Group getStandard() {
        return group;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUlearnId(UUID ulearnId) {
        this.ulearnId = ulearnId;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}