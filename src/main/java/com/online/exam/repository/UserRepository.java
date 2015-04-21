package com.online.exam.repository;

import com.online.exam.domain.User;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findOneByActivationKey(String activationKey);

    List<User> findAllByActivatedIsFalseAndCreatedDateBefore(DateTime dateTime);

    Optional<User> findOneByEmail(String email);

    Optional<User> findOneByLogin(String login);

    Optional<User> findOneByUserNo(String userNo);

    void delete(User t);

    @Modifying
	@Query("update User u set u.password = ?1 where u.id = ?2")
    int upadtePasswordById(String password, Long id);

    @Query("select u from User u where u.deleted = 0")
	List<User> findAllByDeleted();

    @Modifying
	@Query("update User u set u.deleted = 1 where u.id = ?1")
    int deleteUserLogic(Long id);

}
