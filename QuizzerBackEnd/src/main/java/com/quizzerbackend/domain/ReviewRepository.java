package com.quizzerbackend.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ReviewRepository extends JpaRepository<Reviews, Long>{

List<Reviews> findByNickname(String nickname);


}