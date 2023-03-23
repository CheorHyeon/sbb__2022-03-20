package com.mysite.sbb;

import com.mysite.sbb.Question.Question;
import com.mysite.sbb.Question.QuestionRepository;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;

	@BeforeEach
	// 아래 메소드는 각 테스트케이스 실행 전에 실행 된다.
	void beforeEach() {
		answerRepository.deleteAll();
		//흔적삭제(다음번 INSERT때 id가 1번으로 설정되도록)
		answerRepository.clearAutoIncrement();

		questionRepository.deleteAll();
		//흔적삭제(다음번 INSERT때 id가 1번으로 설정되도록)
		questionRepository.clearAutoIncrement();

		Question q1 = new Question();
		q1.setSubject("sbb가 무엇인가요?");
		q1.setContent("sbb에 대해서 알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);  // 첫번째 질문 저장

		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문입니다.");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreateDate(LocalDateTime.now());
		questionRepository.save(q2);  // 두번째 질문 저장

		Answer a1 = new Answer();
		a1.setContent("네 자동으로 생성됩니다.");
		a1.setCreateDate(LocalDateTime.now());
		q2.addAnswer(a1);
		answerRepository.save(a1);
	}

	@Test
	@DisplayName("데이터 저장")
	void testJpa() {
		Question q1 = new Question();
		q1.setSubject("세계에서 가장 부유한 국가가 어디인가요?");
		q1.setContent("알고 싶습니다.");
		q1.setCreateDate(LocalDateTime.now());
		questionRepository.save(q1);  // 첫번째 질문 저장
		assertEquals("세계에서 가장 부유한 국가가 어디인가요?", questionRepository.findById(3).get().getSubject());
	}

	@Test
	@DisplayName("findAll")
	void testJpa2() {
		List<Question> all = this.questionRepository.findAll();
		assertEquals(2, all.size());

		Question q = all.get(0);
		assertEquals("sbb가 무엇인가요?", q.getSubject());
	}

	@Test
	@DisplayName("findById")
	void testJpa3() {
		Optional<Question> oq = this.questionRepository.findById(1);
		if(oq.isPresent()){
			Question q = oq.get();
			assertEquals("sbb가 무엇인가요?", q.getSubject());
		}
	}
	@Test
	@DisplayName("findBySubject")
	void testJpa4() {
		Optional<Question> q = this.questionRepository.findBySubject("sbb가 무엇인가요?");
		if(q.isPresent()){
			assertEquals(1, q.get().getId());
		}
	}

	@Test
	@DisplayName("findBySubjectAndContent")
	void testJpa5() {
		Question q = this.questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
			assertEquals(1, q.getId());
	}

	@Test
	@DisplayName("findBySubjectLike")
	void testJpa6() {
		List<Question> q = this.questionRepository.findBySubjectLike("sbb%");
		Question q2 = q.get(0);
		assertEquals("sbb가 무엇인가요?", q2.getSubject());
	}

	@Test
	@DisplayName("setSubject로 수정")
	void testJpa7() {
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		q.setSubject("수정된 제목");
		this.questionRepository.save(q);
	}

	@Test
	@DisplayName("삭제하기")
	void testJpa8() {
		assertEquals(2, this.questionRepository.count());
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		this.questionRepository.delete(q);
		assertEquals(1, this.questionRepository.count());
	}

	@Test
	@DisplayName("답변 생성 후 저장하기")
	void testJpa9() {
		Question q = questionRepository.findById(2).orElse(null);
		Answer a = new Answer();
		a.setContent("네 자동으로 생성됩니다.");
		a.setQuestion(q); // 어떤 질문의 답변인지 알기 위해 필요
		a.setCreateDate(LocalDateTime.now());
		answerRepository.save(a);
	}

	@Test
	@DisplayName("답변 조회하기")
	void testJpa10() {
		Optional<Answer> oa = answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer a = oa.get();
		assertEquals(2, a.getQuestion().getId());
	}
	@Transactional
	@Rollback(false)
	@Test
	@DisplayName("질문에 달린 답변 찾기")
	void t011() {
		Optional<Question> oq = questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		List<Answer> answerList = q.getAnswerList();

		assertEquals(1, answerList.size());
		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
	}



}
