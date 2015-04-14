package com.online.exam.repository;

import com.online.exam.domain.STUDENT;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the STUDENT entity.
 */
public interface STUDENTRepository extends JpaRepository<STUDENT,Long> {

}
