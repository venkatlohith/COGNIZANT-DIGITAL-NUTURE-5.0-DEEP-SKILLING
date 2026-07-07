package com.cognizant.ormlearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cognizant.ormlearn.model.Attempt;

@Repository
public interface AttemptRepository extends JpaRepository<Attempt, Integer> {

    // HQL joining all tables in order:
    // user -> attempt -> attempt_question -> question -> attempt_option -> options
    // 'fetch' used for OneToMany relationships to populate the beans in a single query
    @Query(value = "SELECT a FROM Attempt a "
                 + "join fetch a.user u "
                 + "join fetch a.attemptQuestionList aq "
                 + "join fetch aq.question q "
                 + "join fetch aq.attemptOptionList ao "
                 + "join fetch ao.option op "
                 + "WHERE u.id = :userId AND a.id = :attemptId")
    Attempt getAttempt(@Param("userId") int userId, @Param("attemptId") int attemptId);
}
