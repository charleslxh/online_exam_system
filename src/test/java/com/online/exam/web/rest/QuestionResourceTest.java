package com.online.exam.web.rest;

import com.online.exam.Application;
import com.online.exam.domain.Question;
import com.online.exam.repository.QuestionRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the QuestionResource REST controller.
 *
 * @see QuestionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class QuestionResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_QUESTION_NO = "SAMPLE_TEXT";
    private static final String UPDATED_QUESTION_NO = "UPDATED_TEXT";
    private static final String DEFAULT_QUESTION_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_QUESTION_NAME = "UPDATED_TEXT";
    private static final String DEFAULT_QUESTION_TYPE = "SAMPLE_TEXT";
    private static final String UPDATED_QUESTION_TYPE = "UPDATED_TEXT";
    private static final String DEFAULT_SUBJECT = "SAMPLE_TEXT";
    private static final String UPDATED_SUBJECT = "UPDATED_TEXT";

    private static final DateTime DEFAULT_CREATE_TIME = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_CREATE_TIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_CREATE_TIME_STR = dateTimeFormatter.print(DEFAULT_CREATE_TIME);

    private static final DateTime DEFAULT_LAST_UPDATE_TIME = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_LAST_UPDATE_TIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_LAST_UPDATE_TIME_STR = dateTimeFormatter.print(DEFAULT_LAST_UPDATE_TIME);

    private static final Integer DEFAULT_DELETED = 0;
    private static final Integer UPDATED_DELETED = 1;
    private static final String DEFAULT_CODE = "SAMPLE_TEXT";
    private static final String UPDATED_CODE = "UPDATED_TEXT";
    private static final String DEFAULT_AUTHOR = "SAMPLE_TEXT";
    private static final String UPDATED_AUTHOR = "UPDATED_TEXT";
    private static final String DEFAULT_OPTION_A = "SAMPLE_TEXT";
    private static final String UPDATED_OPTION_A = "UPDATED_TEXT";
    private static final String DEFAULT_OPTION_B = "SAMPLE_TEXT";
    private static final String UPDATED_OPTION_B = "UPDATED_TEXT";
    private static final String DEFAULT_OPTION_C = "SAMPLE_TEXT";
    private static final String UPDATED_OPTION_C = "UPDATED_TEXT";
    private static final String DEFAULT_OPTION_D = "SAMPLE_TEXT";
    private static final String UPDATED_OPTION_D = "UPDATED_TEXT";
    private static final String DEFAULT_ANSWER = "SAMPLE_TEXT";
    private static final String UPDATED_ANSWER = "UPDATED_TEXT";

    @Inject
    private QuestionRepository questionRepository;

    private MockMvc restQuestionMockMvc;

    private Question question;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        QuestionResource questionResource = new QuestionResource();
        ReflectionTestUtils.setField(questionResource, "questionRepository", questionRepository);
        this.restQuestionMockMvc = MockMvcBuilders.standaloneSetup(questionResource).build();
    }

    @Before
    public void initTest() {
        question = new Question();
        question.setQuestionNo(DEFAULT_QUESTION_NO);
        question.setQuestionName(DEFAULT_QUESTION_NAME);
        question.setQuestionType(DEFAULT_QUESTION_TYPE);
        question.setSubject(DEFAULT_SUBJECT);
        question.setCreateTime(DEFAULT_CREATE_TIME);
        question.setLastUpdateTime(DEFAULT_LAST_UPDATE_TIME);
        question.setDeleted(DEFAULT_DELETED);
        question.setCode(DEFAULT_CODE);
        question.setAuthor(DEFAULT_AUTHOR);
        question.setOptionA(DEFAULT_OPTION_A);
        question.setOptionB(DEFAULT_OPTION_B);
        question.setOptionC(DEFAULT_OPTION_C);
        question.setOptionD(DEFAULT_OPTION_D);
        question.setAnswer(DEFAULT_ANSWER);
    }

    @Test
    @Transactional
    public void createQuestion() throws Exception {
        int databaseSizeBeforeCreate = questionRepository.findAll().size();

        // Create the Question
        restQuestionMockMvc.perform(post("/api/questions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(question)))
                .andExpect(status().isCreated());

        // Validate the Question in the database
        List<Question> questions = questionRepository.findAll();
        assertThat(questions).hasSize(databaseSizeBeforeCreate + 1);
        Question testQuestion = questions.get(questions.size() - 1);
        assertThat(testQuestion.getQuestionNo()).isEqualTo(DEFAULT_QUESTION_NO);
        assertThat(testQuestion.getQuestionName()).isEqualTo(DEFAULT_QUESTION_NAME);
        assertThat(testQuestion.getQuestionType()).isEqualTo(DEFAULT_QUESTION_TYPE);
        assertThat(testQuestion.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testQuestion.getCreateTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testQuestion.getLastUpdateTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_LAST_UPDATE_TIME);
        assertThat(testQuestion.getDeleted()).isEqualTo(DEFAULT_DELETED);
        assertThat(testQuestion.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testQuestion.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testQuestion.getOptionA()).isEqualTo(DEFAULT_OPTION_A);
        assertThat(testQuestion.getOptionB()).isEqualTo(DEFAULT_OPTION_B);
        assertThat(testQuestion.getOptionC()).isEqualTo(DEFAULT_OPTION_C);
        assertThat(testQuestion.getOptionD()).isEqualTo(DEFAULT_OPTION_D);
        assertThat(testQuestion.getAnswer()).isEqualTo(DEFAULT_ANSWER);
    }

    @Test
    @Transactional
    public void checkQuestionNoIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(questionRepository.findAll()).hasSize(0);
        // set the field null
        question.setQuestionNo(null);

        // Create the Question, which fails.
        restQuestionMockMvc.perform(post("/api/questions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(question)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Question> questions = questionRepository.findAll();
        assertThat(questions).hasSize(0);
    }

    @Test
    @Transactional
    public void checkQuestionNameIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(questionRepository.findAll()).hasSize(0);
        // set the field null
        question.setQuestionName(null);

        // Create the Question, which fails.
        restQuestionMockMvc.perform(post("/api/questions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(question)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Question> questions = questionRepository.findAll();
        assertThat(questions).hasSize(0);
    }

    @Test
    @Transactional
    public void checkQuestionTypeIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(questionRepository.findAll()).hasSize(0);
        // set the field null
        question.setQuestionType(null);

        // Create the Question, which fails.
        restQuestionMockMvc.perform(post("/api/questions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(question)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Question> questions = questionRepository.findAll();
        assertThat(questions).hasSize(0);
    }

    @Test
    @Transactional
    public void checkAnswerIsRequired() throws Exception {
        // Validate the database is empty
        assertThat(questionRepository.findAll()).hasSize(0);
        // set the field null
        question.setAnswer(null);

        // Create the Question, which fails.
        restQuestionMockMvc.perform(post("/api/questions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(question)))
                .andExpect(status().isBadRequest());

        // Validate the database is still empty
        List<Question> questions = questionRepository.findAll();
        assertThat(questions).hasSize(0);
    }

    @Test
    @Transactional
    public void getAllQuestions() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get all the questions
        restQuestionMockMvc.perform(get("/api/questions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(question.getId().intValue())))
                .andExpect(jsonPath("$.[*].questionNo").value(hasItem(DEFAULT_QUESTION_NO.toString())))
                .andExpect(jsonPath("$.[*].questionName").value(hasItem(DEFAULT_QUESTION_NAME.toString())))
                .andExpect(jsonPath("$.[*].questionType").value(hasItem(DEFAULT_QUESTION_TYPE.toString())))
                .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
                .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME_STR)))
                .andExpect(jsonPath("$.[*].lastUpdateTime").value(hasItem(DEFAULT_LAST_UPDATE_TIME_STR)))
                .andExpect(jsonPath("$.[*].deleted").value(hasItem(DEFAULT_DELETED)))
                .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
                .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())))
                .andExpect(jsonPath("$.[*].optionA").value(hasItem(DEFAULT_OPTION_A.toString())))
                .andExpect(jsonPath("$.[*].optionB").value(hasItem(DEFAULT_OPTION_B.toString())))
                .andExpect(jsonPath("$.[*].optionC").value(hasItem(DEFAULT_OPTION_C.toString())))
                .andExpect(jsonPath("$.[*].optionD").value(hasItem(DEFAULT_OPTION_D.toString())))
                .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER.toString())));
    }

    @Test
    @Transactional
    public void getQuestion() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);

        // Get the question
        restQuestionMockMvc.perform(get("/api/questions/{id}", question.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(question.getId().intValue()))
            .andExpect(jsonPath("$.questionNo").value(DEFAULT_QUESTION_NO.toString()))
            .andExpect(jsonPath("$.questionName").value(DEFAULT_QUESTION_NAME.toString()))
            .andExpect(jsonPath("$.questionType").value(DEFAULT_QUESTION_TYPE.toString()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME_STR))
            .andExpect(jsonPath("$.lastUpdateTime").value(DEFAULT_LAST_UPDATE_TIME_STR))
            .andExpect(jsonPath("$.deleted").value(DEFAULT_DELETED))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()))
            .andExpect(jsonPath("$.optionA").value(DEFAULT_OPTION_A.toString()))
            .andExpect(jsonPath("$.optionB").value(DEFAULT_OPTION_B.toString()))
            .andExpect(jsonPath("$.optionC").value(DEFAULT_OPTION_C.toString()))
            .andExpect(jsonPath("$.optionD").value(DEFAULT_OPTION_D.toString()))
            .andExpect(jsonPath("$.answer").value(DEFAULT_ANSWER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingQuestion() throws Exception {
        // Get the question
        restQuestionMockMvc.perform(get("/api/questions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestion() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);
		
		int databaseSizeBeforeUpdate = questionRepository.findAll().size();

        // Update the question
        question.setQuestionNo(UPDATED_QUESTION_NO);
        question.setQuestionName(UPDATED_QUESTION_NAME);
        question.setQuestionType(UPDATED_QUESTION_TYPE);
        question.setSubject(UPDATED_SUBJECT);
        question.setCreateTime(UPDATED_CREATE_TIME);
        question.setLastUpdateTime(UPDATED_LAST_UPDATE_TIME);
        question.setDeleted(UPDATED_DELETED);
        question.setCode(UPDATED_CODE);
        question.setAuthor(UPDATED_AUTHOR);
        question.setOptionA(UPDATED_OPTION_A);
        question.setOptionB(UPDATED_OPTION_B);
        question.setOptionC(UPDATED_OPTION_C);
        question.setOptionD(UPDATED_OPTION_D);
        question.setAnswer(UPDATED_ANSWER);
        restQuestionMockMvc.perform(put("/api/questions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(question)))
                .andExpect(status().isOk());

        // Validate the Question in the database
        List<Question> questions = questionRepository.findAll();
        assertThat(questions).hasSize(databaseSizeBeforeUpdate);
        Question testQuestion = questions.get(questions.size() - 1);
        assertThat(testQuestion.getQuestionNo()).isEqualTo(UPDATED_QUESTION_NO);
        assertThat(testQuestion.getQuestionName()).isEqualTo(UPDATED_QUESTION_NAME);
        assertThat(testQuestion.getQuestionType()).isEqualTo(UPDATED_QUESTION_TYPE);
        assertThat(testQuestion.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testQuestion.getCreateTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testQuestion.getLastUpdateTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_LAST_UPDATE_TIME);
        assertThat(testQuestion.getDeleted()).isEqualTo(UPDATED_DELETED);
        assertThat(testQuestion.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testQuestion.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testQuestion.getOptionA()).isEqualTo(UPDATED_OPTION_A);
        assertThat(testQuestion.getOptionB()).isEqualTo(UPDATED_OPTION_B);
        assertThat(testQuestion.getOptionC()).isEqualTo(UPDATED_OPTION_C);
        assertThat(testQuestion.getOptionD()).isEqualTo(UPDATED_OPTION_D);
        assertThat(testQuestion.getAnswer()).isEqualTo(UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void deleteQuestion() throws Exception {
        // Initialize the database
        questionRepository.saveAndFlush(question);
		
		int databaseSizeBeforeDelete = questionRepository.findAll().size();

        // Get the question
        restQuestionMockMvc.perform(delete("/api/questions/{id}", question.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Question> questions = questionRepository.findAll();
        assertThat(questions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
