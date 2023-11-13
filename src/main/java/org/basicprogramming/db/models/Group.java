package org.basicprogramming.db.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "LearningGroup")
public class Group implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column()
    private String groupName;

    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "group",
            cascade = CascadeType.ALL)
    private List<Student> students;

    public Integer getId() {
        return id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
