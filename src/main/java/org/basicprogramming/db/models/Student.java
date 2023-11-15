package org.basicprogramming.db.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
public class Student implements Serializable {
    @Id
    private UUID ulearnId;

    @Column()
    private String firstName;

    @Column()
    private String lastName;

    @Column()
    private String mail;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name="group_id")
    private Group group;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "student",
            cascade = CascadeType.MERGE)
    private List<Mark> marks;

    @Override
    public String toString() {
        return "Student{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public Group getGroup() {
        return group;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public UUID getId() {
        return ulearnId;
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