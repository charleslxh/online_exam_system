package com.online.exam.repository;

import com.online.exam.domain.Question;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Question entity.
 */
public interface QuestionRepository extends JpaRepository<Question,Long> {

}
