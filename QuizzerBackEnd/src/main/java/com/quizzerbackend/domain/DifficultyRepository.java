package com.quizzerbackend.domain;
import java.util.List;
import org.springframework.data.repository.CrudRepository;


public interface DifficultyRepository extends CrudRepository<Difficulty, Long>{


    List<Difficulty> findByLevel (String level);
}

