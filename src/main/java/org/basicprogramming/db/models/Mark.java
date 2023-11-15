package org.basicprogramming.db.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class Mark implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column()
    private Integer score;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name="exercise_id")
    private Exercise exercise;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name="student_id")
    private Student student;

    public Mark() { }

    public Mark(Student student, Exercise exercise, int score) {
        this.student = student;
        this.exercise = exercise;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Mark{" +
                "score=" + score +
                ", student=" + student +
                '}';
    }
}