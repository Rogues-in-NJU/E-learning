package edu.nju.service.repo;

import edu.nju.service.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface QuestionRepository extends JpaRepository<Question, Integer>, JpaSpecificationExecutor<Question> {
}
