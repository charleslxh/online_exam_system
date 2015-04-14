package com.online.exam.repository;

import com.online.exam.domain.TEACHER;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TEACHER entity.
 */
public interface TEACHERRepository extends JpaRepository<TEACHER,Long> {

}
