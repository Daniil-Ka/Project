package org.basicprogramming.db.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Lesson implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column()
    private String lessonName;

    public Lesson() { }

    public Lesson(String lessonName) {
        this.lessonName = lessonName;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "lessonName='" + lessonName + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }
}
