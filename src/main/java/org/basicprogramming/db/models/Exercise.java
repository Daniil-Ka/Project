package org.basicprogramming.db.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Exercise implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name="lesson_id")
    private Lesson lesson;

    public Exercise() { }

    public Exercise(String name, Lesson lesson) {
        this.name = name;
        this.lesson = lesson;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "name='" + name + '\'' +
                ", lesson=" + lesson +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public String getName() {
        return name;
    }
}
