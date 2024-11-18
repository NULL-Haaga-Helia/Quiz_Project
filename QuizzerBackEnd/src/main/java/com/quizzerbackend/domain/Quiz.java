package com.quizzerbackend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class Quiz {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Quiz name cannot be empty")
    private String name;

    @NotBlank(message = "Quiz description cannot be empty")
    private String description;

    private String addedOn;
    private boolean isPublished;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quiz")
    private List<Question> questions;
    

    public Quiz() {
	}
  
   
    public Quiz(String name, String description, String addedOn, boolean isPublished) {
		super();
		this.name = name;
		this.description = description;
		this.addedOn = addedOn;
        this.isPublished = isPublished;
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

    public boolean getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(boolean isPublished) {
        this.isPublished = isPublished;
    }



    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    

    @Override
    public String toString() {
        return "Quiz [id=" + id + ", name=" + name + ", description=" + description + ", addedOn=" + addedOn
                + ", isPublished=" + isPublished + "]";
    }

  
  
   
    

}
