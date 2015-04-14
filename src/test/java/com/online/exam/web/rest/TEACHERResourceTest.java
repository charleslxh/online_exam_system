package com.online.exam.web.rest;

import com.online.exam.Application;
import com.online.exam.domain.TEACHER;
import com.online.exam.repository.TEACHERRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TEACHERResource REST controller.
 *
 * @see TEACHERResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TEACHERResourceTest {

    private static final String DEFAULT_TEACHER_NO = "SAMPLE_TEXT";
    private static final String UPDATED_TEACHER_NO = "UPDATED_TEXT";

    private static final Integer DEFAULT_GENDER = 0;
    private static final Integer UPDATED_GENDER = 1;

    private static final Integer DEFAULT_AGE = 0;
    private static final Integer UPDATED_AGE = 1;
    private static final String DEFAULT_AVATAR_URL = "SAMPLE_TEXT";
    private static final String UPDATED_AVATAR_URL = "UPDATED_TEXT";
    private static final String DEFAULT_SCHOOL = "SAMPLE_TEXT";
    private static final String UPDATED_SCHOOL = "UPDATED_TEXT";
    private static final String DEFAULT_CLASSES = "SAMPLE_TEXT";
    private static final String UPDATED_CLASSES = "UPDATED_TEXT";
    private static final String DEFAULT_ADDRESS = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS = "UPDATED_TEXT";
    private static final String DEFAULT_PHONE = "SAMPLE_TEXT";
    private static final String UPDATED_PHONE = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    @Inject
    private TEACHERRepository tEACHERRepository;

    private MockMvc restTEACHERMockMvc;

    private TEACHER tEACHER;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TEACHERResource tEACHERResource = new TEACHERResource();
        ReflectionTestUtils.setField(tEACHERResource, "tEACHERRepository", tEACHERRepository);
        this.restTEACHERMockMvc = MockMvcBuilders.standaloneSetup(tEACHERResource).build();
    }

    @Before
    public void initTest() {
        tEACHER = new TEACHER();
        tEACHER.setTeacher_no(DEFAULT_TEACHER_NO);
        tEACHER.setGender(DEFAULT_GENDER);
        tEACHER.setAge(DEFAULT_AGE);
        tEACHER.setAvatar_url(DEFAULT_AVATAR_URL);
        tEACHER.setSchool(DEFAULT_SCHOOL);
        tEACHER.setClasses(DEFAULT_CLASSES);
        tEACHER.setAddress(DEFAULT_ADDRESS);
        tEACHER.setPhone(DEFAULT_PHONE);
        tEACHER.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTEACHER() throws Exception {
        int databaseSizeBeforeCreate = tEACHERRepository.findAll().size();

        // Create the TEACHER
        restTEACHERMockMvc.perform(post("/api/tEACHERs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tEACHER)))
                .andExpect(status().isCreated());

        // Validate the TEACHER in the database
        List<TEACHER> tEACHERs = tEACHERRepository.findAll();
        assertThat(tEACHERs).hasSize(databaseSizeBeforeCreate + 1);
        TEACHER testTEACHER = tEACHERs.get(tEACHERs.size() - 1);
        assertThat(testTEACHER.getTeacher_no()).isEqualTo(DEFAULT_TEACHER_NO);
        assertThat(testTEACHER.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testTEACHER.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testTEACHER.getAvatar_url()).isEqualTo(DEFAULT_AVATAR_URL);
        assertThat(testTEACHER.getSchool()).isEqualTo(DEFAULT_SCHOOL);
        assertThat(testTEACHER.getClasses()).isEqualTo(DEFAULT_CLASSES);
        assertThat(testTEACHER.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testTEACHER.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testTEACHER.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTEACHERs() throws Exception {
        // Initialize the database
        tEACHERRepository.saveAndFlush(tEACHER);

        // Get all the tEACHERs
        restTEACHERMockMvc.perform(get("/api/tEACHERs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tEACHER.getId().intValue())))
                .andExpect(jsonPath("$.[*].teacher_no").value(hasItem(DEFAULT_TEACHER_NO.toString())))
                .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
                .andExpect(jsonPath("$.[*].avatar_url").value(hasItem(DEFAULT_AVATAR_URL.toString())))
                .andExpect(jsonPath("$.[*].school").value(hasItem(DEFAULT_SCHOOL.toString())))
                .andExpect(jsonPath("$.[*].classes").value(hasItem(DEFAULT_CLASSES.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getTEACHER() throws Exception {
        // Initialize the database
        tEACHERRepository.saveAndFlush(tEACHER);

        // Get the tEACHER
        restTEACHERMockMvc.perform(get("/api/tEACHERs/{id}", tEACHER.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(tEACHER.getId().intValue()))
            .andExpect(jsonPath("$.teacher_no").value(DEFAULT_TEACHER_NO.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.avatar_url").value(DEFAULT_AVATAR_URL.toString()))
            .andExpect(jsonPath("$.school").value(DEFAULT_SCHOOL.toString()))
            .andExpect(jsonPath("$.classes").value(DEFAULT_CLASSES.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTEACHER() throws Exception {
        // Get the tEACHER
        restTEACHERMockMvc.perform(get("/api/tEACHERs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTEACHER() throws Exception {
        // Initialize the database
        tEACHERRepository.saveAndFlush(tEACHER);
		
		int databaseSizeBeforeUpdate = tEACHERRepository.findAll().size();

        // Update the tEACHER
        tEACHER.setTeacher_no(UPDATED_TEACHER_NO);
        tEACHER.setGender(UPDATED_GENDER);
        tEACHER.setAge(UPDATED_AGE);
        tEACHER.setAvatar_url(UPDATED_AVATAR_URL);
        tEACHER.setSchool(UPDATED_SCHOOL);
        tEACHER.setClasses(UPDATED_CLASSES);
        tEACHER.setAddress(UPDATED_ADDRESS);
        tEACHER.setPhone(UPDATED_PHONE);
        tEACHER.setDescription(UPDATED_DESCRIPTION);
        restTEACHERMockMvc.perform(put("/api/tEACHERs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tEACHER)))
                .andExpect(status().isOk());

        // Validate the TEACHER in the database
        List<TEACHER> tEACHERs = tEACHERRepository.findAll();
        assertThat(tEACHERs).hasSize(databaseSizeBeforeUpdate);
        TEACHER testTEACHER = tEACHERs.get(tEACHERs.size() - 1);
        assertThat(testTEACHER.getTeacher_no()).isEqualTo(UPDATED_TEACHER_NO);
        assertThat(testTEACHER.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testTEACHER.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testTEACHER.getAvatar_url()).isEqualTo(UPDATED_AVATAR_URL);
        assertThat(testTEACHER.getSchool()).isEqualTo(UPDATED_SCHOOL);
        assertThat(testTEACHER.getClasses()).isEqualTo(UPDATED_CLASSES);
        assertThat(testTEACHER.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testTEACHER.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testTEACHER.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteTEACHER() throws Exception {
        // Initialize the database
        tEACHERRepository.saveAndFlush(tEACHER);
		
		int databaseSizeBeforeDelete = tEACHERRepository.findAll().size();

        // Get the tEACHER
        restTEACHERMockMvc.perform(delete("/api/tEACHERs/{id}", tEACHER.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TEACHER> tEACHERs = tEACHERRepository.findAll();
        assertThat(tEACHERs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
