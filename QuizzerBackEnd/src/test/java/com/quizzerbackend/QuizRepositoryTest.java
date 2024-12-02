package com.quizzerbackend;


// import static org.assertj.core.api.Assertions.assertThat;

// import java.util.List;


// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
// import org.junit.jupiter.api.Test;

// import com.quizzerbackend.domain.Answer;
// import com.quizzerbackend.domain.AnswerRepository;
// import com.quizzerbackend.domain.Question;
// import com.quizzerbackend.domain.QuestionRepository;
// import com.quizzerbackend.domain.Quiz;
// import com.quizzerbackend.domain.QuizRepository;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) 
@SpringBootTest(classes = QuizzerbackendApplication.class)

public class QuizRepositoryTest {

    // @Autowired
    // private QuizRepository quizRepository;

    // @Autowired
    // private QuestionRepository questionRepository;

    // @Autowired
    // private AnswerRepository answerRepository;

    // @Test
    // public void findByNameShouldReturnQuizBiology() {
    //     List<Quiz> quizzes = quizRepository.findByName("Biology");
        
    //     assertThat(quizzes).hasSize(1);
    //     assertThat(quizzes.get(0).getDescription()).isEqualTo("Learn fundamental concepts in biology");
    // }

    // @Test
    // public void createNewQuizGeography() {
    // 	Quiz quiz = new Quiz("Geography", "Test your geography knowledge", "12.11.2024", false);
    // 	quizRepository.save(quiz);
    // 	Question question = new Question("What is the capital of Finland", "easy", quiz);
    // 	questionRepository.save(question);
    //     Answer answer = new Answer(true, "Helsinki", question);
    // 	answerRepository.save(answer);
    // 	assertThat(question.getQuestionId()).isNotNull();
    // }  

    //  @Test
    //  public void deleteBiologyQuiz() {
	//  	List<Quiz> quizzes = quizRepository.findByName("Biology");
	//  	Quiz quiz = quizzes.get(0);
	//  	quizRepository.delete(quiz);
	// 	List<Quiz> newQuizzes = quizRepository.findByName("Biology");
	//  	assertThat(newQuizzes).hasSize(0);
    //  } 

    //  @Test
    //  public void findByNameShouldReturnPublishedQuiz() {
    //      List<Quiz> quizzes = quizRepository.findByIsPublished(true);
    //      assertThat(quizzes).hasSize(1);
    //      assertThat(quizzes.get(0).getName()).isEqualTo("Biology");
    //  }
}
