package com.quizzerbackend.domain;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="DIFFICULTY")
public class Difficulty {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)

    private Long difficultyId;
    private String level;
    private String description;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "difficulty")
    private List<Quiz> quizs;

    public Difficulty() {
    }

    public Difficulty(String level, String description) {
        super();
        this.level = level;
        this.description = description;
    }


    public Long getDifficultyId() {
        return difficultyId;
    }

    public void setDifficultyId(Long difficultyId) {
        this.difficultyId = difficultyId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

