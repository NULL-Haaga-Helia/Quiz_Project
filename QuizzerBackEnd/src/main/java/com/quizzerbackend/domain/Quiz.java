package com.quizzerbackend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Quiz {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private String name;
    private String description;
    private String addedOn;
    private boolean isPublished;


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


    public boolean isPublished() {
        return isPublished;
    }
    public void setPublished(boolean isPublished) {
        this.isPublished = isPublished;
    }

}
