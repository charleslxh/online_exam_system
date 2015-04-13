package com.justin.exam.repository;

import com.justin.exam.domain.Test1;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Test1 entity.
 */
public interface Test1Repository extends JpaRepository<Test1,Long> {

}
