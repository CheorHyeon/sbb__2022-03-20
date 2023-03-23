package com.mysite.sbb.Question;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Optional<Question> findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);
    List<Question> findBySubjectLike(String subject);
    @Transactional // Modifying 있으면 트랜잭셔널 붙여야 함
    @Modifying // 만약 아래 쿼리가 SELECT가 아니 라면 이걸 붙여야 한다.
    // nativeQuery = true 여야 MySQL 쿼리문법 사용 가능
    @Query(value = "ALTER TABLE question AUTO_INCREMENT = 1", nativeQuery = true)
    void clearAutoIncrement();
}
