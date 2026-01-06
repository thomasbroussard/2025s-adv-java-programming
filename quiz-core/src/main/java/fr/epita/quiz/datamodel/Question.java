package fr.epita.quiz.datamodel;

import jakarta.persistence.*;

@Entity
@Table(name="QUESTION")
public class Question {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id")
    Integer id;


    @Column(name="title")
    String title;


    public Question() {}

    public Question(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
