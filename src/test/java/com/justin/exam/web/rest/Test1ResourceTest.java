package com.justin.exam.web.rest;

import com.justin.exam.Application;
import com.justin.exam.domain.Test1;
import com.justin.exam.repository.Test1Repository;

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
 * Test class for the Test1Resource REST controller.
 *
 * @see Test1Resource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class Test1ResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_TESTNAME = "SAMPLE_TEXT";
    private static final String UPDATED_TESTNAME = "UPDATED_TEXT";
    private static final String DEFAULT_PASSWORD = "SAMPLE_TEXT";
    private static final String UPDATED_PASSWORD = "UPDATED_TEXT";
    private static final String DEFAULT_FULL_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_FULL_NAME = "UPDATED_TEXT";

    private static final DateTime DEFAULT_CREATE_TIME = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_CREATE_TIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_CREATE_TIME_STR = dateTimeFormatter.print(DEFAULT_CREATE_TIME);

    private static final DateTime DEFAULT_LASE_UPDATE_TIME = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_LASE_UPDATE_TIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_LASE_UPDATE_TIME_STR = dateTimeFormatter.print(DEFAULT_LASE_UPDATE_TIME);

    @Inject
    private Test1Repository test1Repository;

    private MockMvc restTest1MockMvc;

    private Test1 test1;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        Test1Resource test1Resource = new Test1Resource();
        ReflectionTestUtils.setField(test1Resource, "test1Repository", test1Repository);
        this.restTest1MockMvc = MockMvcBuilders.standaloneSetup(test1Resource).build();
    }

    @Before
    public void initTest() {
        test1 = new Test1();
        test1.setTestname(DEFAULT_TESTNAME);
        test1.setPassword(DEFAULT_PASSWORD);
        test1.setFullName(DEFAULT_FULL_NAME);
        test1.setCreateTime(DEFAULT_CREATE_TIME);
        test1.setLaseUpdateTime(DEFAULT_LASE_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void createTest1() throws Exception {
        int databaseSizeBeforeCreate = test1Repository.findAll().size();

        // Create the Test1
        restTest1MockMvc.perform(post("/api/test1s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test1)))
                .andExpect(status().isCreated());

        // Validate the Test1 in the database
        List<Test1> test1s = test1Repository.findAll();
        assertThat(test1s).hasSize(databaseSizeBeforeCreate + 1);
        Test1 testTest1 = test1s.get(test1s.size() - 1);
        assertThat(testTest1.getTestname()).isEqualTo(DEFAULT_TESTNAME);
        assertThat(testTest1.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testTest1.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testTest1.getCreateTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testTest1.getLaseUpdateTime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_LASE_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void getAllTest1s() throws Exception {
        // Initialize the database
        test1Repository.saveAndFlush(test1);

        // Get all the test1s
        restTest1MockMvc.perform(get("/api/test1s"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(test1.getId().intValue())))
                .andExpect(jsonPath("$.[*].testname").value(hasItem(DEFAULT_TESTNAME.toString())))
                .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
                .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())))
                .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME_STR)))
                .andExpect(jsonPath("$.[*].laseUpdateTime").value(hasItem(DEFAULT_LASE_UPDATE_TIME_STR)));
    }

    @Test
    @Transactional
    public void getTest1() throws Exception {
        // Initialize the database
        test1Repository.saveAndFlush(test1);

        // Get the test1
        restTest1MockMvc.perform(get("/api/test1s/{id}", test1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(test1.getId().intValue()))
            .andExpect(jsonPath("$.testname").value(DEFAULT_TESTNAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME_STR))
            .andExpect(jsonPath("$.laseUpdateTime").value(DEFAULT_LASE_UPDATE_TIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingTest1() throws Exception {
        // Get the test1
        restTest1MockMvc.perform(get("/api/test1s/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTest1() throws Exception {
        // Initialize the database
        test1Repository.saveAndFlush(test1);
		
		int databaseSizeBeforeUpdate = test1Repository.findAll().size();

        // Update the test1
        test1.setTestname(UPDATED_TESTNAME);
        test1.setPassword(UPDATED_PASSWORD);
        test1.setFullName(UPDATED_FULL_NAME);
        test1.setCreateTime(UPDATED_CREATE_TIME);
        test1.setLaseUpdateTime(UPDATED_LASE_UPDATE_TIME);
        restTest1MockMvc.perform(put("/api/test1s")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test1)))
                .andExpect(status().isOk());

        // Validate the Test1 in the database
        List<Test1> test1s = test1Repository.findAll();
        assertThat(test1s).hasSize(databaseSizeBeforeUpdate);
        Test1 testTest1 = test1s.get(test1s.size() - 1);
        assertThat(testTest1.getTestname()).isEqualTo(UPDATED_TESTNAME);
        assertThat(testTest1.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testTest1.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testTest1.getCreateTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testTest1.getLaseUpdateTime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_LASE_UPDATE_TIME);
    }

    @Test
    @Transactional
    public void deleteTest1() throws Exception {
        // Initialize the database
        test1Repository.saveAndFlush(test1);
		
		int databaseSizeBeforeDelete = test1Repository.findAll().size();

        // Get the test1
        restTest1MockMvc.perform(delete("/api/test1s/{id}", test1.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Test1> test1s = test1Repository.findAll();
        assertThat(test1s).hasSize(databaseSizeBeforeDelete - 1);
    }
}
