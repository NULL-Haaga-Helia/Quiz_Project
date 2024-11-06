package com.quizzerbackend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity
public class Quiz {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private String name;
    private String description;
    private String addedOn;
    private boolean isPublished;

    @ManyToOne
    @JoinColumn(name= "difficultyId")
    private Difficulty difficulty;


    public Quiz() {
	}

    public Quiz(String name, String description, String addedOn, boolean isPublished, Difficulty difficulty) {
		super();
		this.name = name;
		this.description = description;
		this.addedOn = addedOn;
        this.isPublished = isPublished;
        this.difficulty = difficulty;
	}


	public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddedOn() {
        return addedOn;
    }
    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }


    public boolean isPublished() {
        return isPublished;
    }
    public void setPublished(boolean isPublished) {
        this.isPublished = isPublished;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

}
