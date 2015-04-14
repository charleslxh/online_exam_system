package com.online.exam.web.rest;

import com.online.exam.Application;
import com.online.exam.domain.STUDENT;
import com.online.exam.repository.STUDENTRepository;

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
 * Test class for the STUDENTResource REST controller.
 *
 * @see STUDENTResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class STUDENTResourceTest {

    private static final String DEFAULT_USER_NO = "SAMPLE_TEXT";
    private static final String UPDATED_USER_NO = "UPDATED_TEXT";

    private static final Integer DEFAULT_GENDER = 0;
    private static final Integer UPDATED_GENDER = 1;

    private static final Integer DEFAULT_AGE = 0;
    private static final Integer UPDATED_AGE = 1;
    private static final String DEFAULT_AVATAR_URL = "SAMPLE_TEXT";
    private static final String UPDATED_AVATAR_URL = "UPDATED_TEXT";
    private static final String DEFAULT_SCHOOL = "SAMPLE_TEXT";
    private static final String UPDATED_SCHOOL = "UPDATED_TEXT";
    private static final String DEFAULT_CLASSESS = "SAMPLE_TEXT";
    private static final String UPDATED_CLASSESS = "UPDATED_TEXT";
    private static final String DEFAULT_ADDRESS = "SAMPLE_TEXT";
    private static final String UPDATED_ADDRESS = "UPDATED_TEXT";
    private static final String DEFAULT_PHONE = "SAMPLE_TEXT";
    private static final String UPDATED_PHONE = "UPDATED_TEXT";
    private static final String DEFAULT_DESCRIPTION = "SAMPLE_TEXT";
    private static final String UPDATED_DESCRIPTION = "UPDATED_TEXT";

    @Inject
    private STUDENTRepository sTUDENTRepository;

    private MockMvc restSTUDENTMockMvc;

    private STUDENT sTUDENT;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        STUDENTResource sTUDENTResource = new STUDENTResource();
        ReflectionTestUtils.setField(sTUDENTResource, "sTUDENTRepository", sTUDENTRepository);
        this.restSTUDENTMockMvc = MockMvcBuilders.standaloneSetup(sTUDENTResource).build();
    }

    @Before
    public void initTest() {
        sTUDENT = new STUDENT();
        sTUDENT.setUser_no(DEFAULT_USER_NO);
        sTUDENT.setGender(DEFAULT_GENDER);
        sTUDENT.setAge(DEFAULT_AGE);
        sTUDENT.setAvatar_url(DEFAULT_AVATAR_URL);
        sTUDENT.setSchool(DEFAULT_SCHOOL);
        sTUDENT.setClassess(DEFAULT_CLASSESS);
        sTUDENT.setAddress(DEFAULT_ADDRESS);
        sTUDENT.setPhone(DEFAULT_PHONE);
        sTUDENT.setDescription(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createSTUDENT() throws Exception {
        int databaseSizeBeforeCreate = sTUDENTRepository.findAll().size();

        // Create the STUDENT
        restSTUDENTMockMvc.perform(post("/api/sTUDENTs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sTUDENT)))
                .andExpect(status().isCreated());

        // Validate the STUDENT in the database
        List<STUDENT> sTUDENTs = sTUDENTRepository.findAll();
        assertThat(sTUDENTs).hasSize(databaseSizeBeforeCreate + 1);
        STUDENT testSTUDENT = sTUDENTs.get(sTUDENTs.size() - 1);
        assertThat(testSTUDENT.getUser_no()).isEqualTo(DEFAULT_USER_NO);
        assertThat(testSTUDENT.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testSTUDENT.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testSTUDENT.getAvatar_url()).isEqualTo(DEFAULT_AVATAR_URL);
        assertThat(testSTUDENT.getSchool()).isEqualTo(DEFAULT_SCHOOL);
        assertThat(testSTUDENT.getClassess()).isEqualTo(DEFAULT_CLASSESS);
        assertThat(testSTUDENT.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testSTUDENT.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testSTUDENT.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllSTUDENTs() throws Exception {
        // Initialize the database
        sTUDENTRepository.saveAndFlush(sTUDENT);

        // Get all the sTUDENTs
        restSTUDENTMockMvc.perform(get("/api/sTUDENTs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(sTUDENT.getId().intValue())))
                .andExpect(jsonPath("$.[*].user_no").value(hasItem(DEFAULT_USER_NO.toString())))
                .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
                .andExpect(jsonPath("$.[*].avatar_url").value(hasItem(DEFAULT_AVATAR_URL.toString())))
                .andExpect(jsonPath("$.[*].school").value(hasItem(DEFAULT_SCHOOL.toString())))
                .andExpect(jsonPath("$.[*].classess").value(hasItem(DEFAULT_CLASSESS.toString())))
                .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
                .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
                .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getSTUDENT() throws Exception {
        // Initialize the database
        sTUDENTRepository.saveAndFlush(sTUDENT);

        // Get the sTUDENT
        restSTUDENTMockMvc.perform(get("/api/sTUDENTs/{id}", sTUDENT.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(sTUDENT.getId().intValue()))
            .andExpect(jsonPath("$.user_no").value(DEFAULT_USER_NO.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.avatar_url").value(DEFAULT_AVATAR_URL.toString()))
            .andExpect(jsonPath("$.school").value(DEFAULT_SCHOOL.toString()))
            .andExpect(jsonPath("$.classess").value(DEFAULT_CLASSESS.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSTUDENT() throws Exception {
        // Get the sTUDENT
        restSTUDENTMockMvc.perform(get("/api/sTUDENTs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSTUDENT() throws Exception {
        // Initialize the database
        sTUDENTRepository.saveAndFlush(sTUDENT);
		
		int databaseSizeBeforeUpdate = sTUDENTRepository.findAll().size();

        // Update the sTUDENT
        sTUDENT.setUser_no(UPDATED_USER_NO);
        sTUDENT.setGender(UPDATED_GENDER);
        sTUDENT.setAge(UPDATED_AGE);
        sTUDENT.setAvatar_url(UPDATED_AVATAR_URL);
        sTUDENT.setSchool(UPDATED_SCHOOL);
        sTUDENT.setClassess(UPDATED_CLASSESS);
        sTUDENT.setAddress(UPDATED_ADDRESS);
        sTUDENT.setPhone(UPDATED_PHONE);
        sTUDENT.setDescription(UPDATED_DESCRIPTION);
        restSTUDENTMockMvc.perform(put("/api/sTUDENTs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(sTUDENT)))
                .andExpect(status().isOk());

        // Validate the STUDENT in the database
        List<STUDENT> sTUDENTs = sTUDENTRepository.findAll();
        assertThat(sTUDENTs).hasSize(databaseSizeBeforeUpdate);
        STUDENT testSTUDENT = sTUDENTs.get(sTUDENTs.size() - 1);
        assertThat(testSTUDENT.getUser_no()).isEqualTo(UPDATED_USER_NO);
        assertThat(testSTUDENT.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testSTUDENT.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testSTUDENT.getAvatar_url()).isEqualTo(UPDATED_AVATAR_URL);
        assertThat(testSTUDENT.getSchool()).isEqualTo(UPDATED_SCHOOL);
        assertThat(testSTUDENT.getClassess()).isEqualTo(UPDATED_CLASSESS);
        assertThat(testSTUDENT.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testSTUDENT.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testSTUDENT.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void deleteSTUDENT() throws Exception {
        // Initialize the database
        sTUDENTRepository.saveAndFlush(sTUDENT);
		
		int databaseSizeBeforeDelete = sTUDENTRepository.findAll().size();

        // Get the sTUDENT
        restSTUDENTMockMvc.perform(delete("/api/sTUDENTs/{id}", sTUDENT.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<STUDENT> sTUDENTs = sTUDENTRepository.findAll();
        assertThat(sTUDENTs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
