package com.kdatalab.bridge.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kdatalab.bridge.IntegrationTest;
import com.kdatalab.bridge.domain.TbAttachment;
import com.kdatalab.bridge.repository.TbAttachmentRepository;
import com.kdatalab.bridge.service.dto.TbAttachmentDTO;
import com.kdatalab.bridge.service.mapper.TbAttachmentMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TbAttachmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TbAttachmentResourceIT {

    private static final String DEFAULT_ATT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ATT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final Long DEFAULT_FILE_SIZE = 1L;
    private static final Long UPDATED_FILE_SIZE = 2L;

    private static final String DEFAULT_EXT = "AAAAAAAAAA";
    private static final String UPDATED_EXT = "BBBBBBBBBB";

    private static final String DEFAULT_REG_USER = "AAAAAAAAAA";
    private static final String UPDATED_REG_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_REG_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REG_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MOD_USER = "AAAAAAAAAA";
    private static final String UPDATED_MOD_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_MOD_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MOD_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/tb-attachments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TbAttachmentRepository tbAttachmentRepository;

    @Autowired
    private TbAttachmentMapper tbAttachmentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTbAttachmentMockMvc;

    private TbAttachment tbAttachment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TbAttachment createEntity(EntityManager em) {
        TbAttachment tbAttachment = new TbAttachment()
            .attType(DEFAULT_ATT_TYPE)
            .name(DEFAULT_NAME)
            .path(DEFAULT_PATH)
            .fileSize(DEFAULT_FILE_SIZE)
            .ext(DEFAULT_EXT)
            .regUser(DEFAULT_REG_USER)
            .regDate(DEFAULT_REG_DATE)
            .modUser(DEFAULT_MOD_USER)
            .modDate(DEFAULT_MOD_DATE);
        return tbAttachment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TbAttachment createUpdatedEntity(EntityManager em) {
        TbAttachment tbAttachment = new TbAttachment()
            .attType(UPDATED_ATT_TYPE)
            .name(UPDATED_NAME)
            .path(UPDATED_PATH)
            .fileSize(UPDATED_FILE_SIZE)
            .ext(UPDATED_EXT)
            .regUser(UPDATED_REG_USER)
            .regDate(UPDATED_REG_DATE)
            .modUser(UPDATED_MOD_USER)
            .modDate(UPDATED_MOD_DATE);
        return tbAttachment;
    }

    @BeforeEach
    public void initTest() {
        tbAttachment = createEntity(em);
    }

    @Test
    @Transactional
    void createTbAttachment() throws Exception {
        int databaseSizeBeforeCreate = tbAttachmentRepository.findAll().size();
        // Create the TbAttachment
        TbAttachmentDTO tbAttachmentDTO = tbAttachmentMapper.toDto(tbAttachment);
        restTbAttachmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tbAttachmentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TbAttachment in the database
        List<TbAttachment> tbAttachmentList = tbAttachmentRepository.findAll();
        assertThat(tbAttachmentList).hasSize(databaseSizeBeforeCreate + 1);
        TbAttachment testTbAttachment = tbAttachmentList.get(tbAttachmentList.size() - 1);
        assertThat(testTbAttachment.getAttType()).isEqualTo(DEFAULT_ATT_TYPE);
        assertThat(testTbAttachment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTbAttachment.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testTbAttachment.getFileSize()).isEqualTo(DEFAULT_FILE_SIZE);
        assertThat(testTbAttachment.getExt()).isEqualTo(DEFAULT_EXT);
        assertThat(testTbAttachment.getRegUser()).isEqualTo(DEFAULT_REG_USER);
        assertThat(testTbAttachment.getRegDate()).isEqualTo(DEFAULT_REG_DATE);
        assertThat(testTbAttachment.getModUser()).isEqualTo(DEFAULT_MOD_USER);
        assertThat(testTbAttachment.getModDate()).isEqualTo(DEFAULT_MOD_DATE);
    }

    @Test
    @Transactional
    void createTbAttachmentWithExistingId() throws Exception {
        // Create the TbAttachment with an existing ID
        tbAttachment.setId(1L);
        TbAttachmentDTO tbAttachmentDTO = tbAttachmentMapper.toDto(tbAttachment);

        int databaseSizeBeforeCreate = tbAttachmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbAttachmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tbAttachmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbAttachment in the database
        List<TbAttachment> tbAttachmentList = tbAttachmentRepository.findAll();
        assertThat(tbAttachmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTbAttachments() throws Exception {
        // Initialize the database
        tbAttachmentRepository.saveAndFlush(tbAttachment);

        // Get all the tbAttachmentList
        restTbAttachmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbAttachment.getId().intValue())))
            .andExpect(jsonPath("$.[*].attType").value(hasItem(DEFAULT_ATT_TYPE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)))
            .andExpect(jsonPath("$.[*].fileSize").value(hasItem(DEFAULT_FILE_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].ext").value(hasItem(DEFAULT_EXT)))
            .andExpect(jsonPath("$.[*].regUser").value(hasItem(DEFAULT_REG_USER)))
            .andExpect(jsonPath("$.[*].regDate").value(hasItem(DEFAULT_REG_DATE.toString())))
            .andExpect(jsonPath("$.[*].modUser").value(hasItem(DEFAULT_MOD_USER)))
            .andExpect(jsonPath("$.[*].modDate").value(hasItem(DEFAULT_MOD_DATE.toString())));
    }

    @Test
    @Transactional
    void getTbAttachment() throws Exception {
        // Initialize the database
        tbAttachmentRepository.saveAndFlush(tbAttachment);

        // Get the tbAttachment
        restTbAttachmentMockMvc
            .perform(get(ENTITY_API_URL_ID, tbAttachment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tbAttachment.getId().intValue()))
            .andExpect(jsonPath("$.attType").value(DEFAULT_ATT_TYPE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH))
            .andExpect(jsonPath("$.fileSize").value(DEFAULT_FILE_SIZE.intValue()))
            .andExpect(jsonPath("$.ext").value(DEFAULT_EXT))
            .andExpect(jsonPath("$.regUser").value(DEFAULT_REG_USER))
            .andExpect(jsonPath("$.regDate").value(DEFAULT_REG_DATE.toString()))
            .andExpect(jsonPath("$.modUser").value(DEFAULT_MOD_USER))
            .andExpect(jsonPath("$.modDate").value(DEFAULT_MOD_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTbAttachment() throws Exception {
        // Get the tbAttachment
        restTbAttachmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTbAttachment() throws Exception {
        // Initialize the database
        tbAttachmentRepository.saveAndFlush(tbAttachment);

        int databaseSizeBeforeUpdate = tbAttachmentRepository.findAll().size();

        // Update the tbAttachment
        TbAttachment updatedTbAttachment = tbAttachmentRepository.findById(tbAttachment.getId()).get();
        // Disconnect from session so that the updates on updatedTbAttachment are not directly saved in db
        em.detach(updatedTbAttachment);
        updatedTbAttachment
            .attType(UPDATED_ATT_TYPE)
            .name(UPDATED_NAME)
            .path(UPDATED_PATH)
            .fileSize(UPDATED_FILE_SIZE)
            .ext(UPDATED_EXT)
            .regUser(UPDATED_REG_USER)
            .regDate(UPDATED_REG_DATE)
            .modUser(UPDATED_MOD_USER)
            .modDate(UPDATED_MOD_DATE);
        TbAttachmentDTO tbAttachmentDTO = tbAttachmentMapper.toDto(updatedTbAttachment);

        restTbAttachmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tbAttachmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tbAttachmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the TbAttachment in the database
        List<TbAttachment> tbAttachmentList = tbAttachmentRepository.findAll();
        assertThat(tbAttachmentList).hasSize(databaseSizeBeforeUpdate);
        TbAttachment testTbAttachment = tbAttachmentList.get(tbAttachmentList.size() - 1);
        assertThat(testTbAttachment.getAttType()).isEqualTo(UPDATED_ATT_TYPE);
        assertThat(testTbAttachment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTbAttachment.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testTbAttachment.getFileSize()).isEqualTo(UPDATED_FILE_SIZE);
        assertThat(testTbAttachment.getExt()).isEqualTo(UPDATED_EXT);
        assertThat(testTbAttachment.getRegUser()).isEqualTo(UPDATED_REG_USER);
        assertThat(testTbAttachment.getRegDate()).isEqualTo(UPDATED_REG_DATE);
        assertThat(testTbAttachment.getModUser()).isEqualTo(UPDATED_MOD_USER);
        assertThat(testTbAttachment.getModDate()).isEqualTo(UPDATED_MOD_DATE);
    }

    @Test
    @Transactional
    void putNonExistingTbAttachment() throws Exception {
        int databaseSizeBeforeUpdate = tbAttachmentRepository.findAll().size();
        tbAttachment.setId(count.incrementAndGet());

        // Create the TbAttachment
        TbAttachmentDTO tbAttachmentDTO = tbAttachmentMapper.toDto(tbAttachment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTbAttachmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tbAttachmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tbAttachmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbAttachment in the database
        List<TbAttachment> tbAttachmentList = tbAttachmentRepository.findAll();
        assertThat(tbAttachmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTbAttachment() throws Exception {
        int databaseSizeBeforeUpdate = tbAttachmentRepository.findAll().size();
        tbAttachment.setId(count.incrementAndGet());

        // Create the TbAttachment
        TbAttachmentDTO tbAttachmentDTO = tbAttachmentMapper.toDto(tbAttachment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbAttachmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tbAttachmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbAttachment in the database
        List<TbAttachment> tbAttachmentList = tbAttachmentRepository.findAll();
        assertThat(tbAttachmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTbAttachment() throws Exception {
        int databaseSizeBeforeUpdate = tbAttachmentRepository.findAll().size();
        tbAttachment.setId(count.incrementAndGet());

        // Create the TbAttachment
        TbAttachmentDTO tbAttachmentDTO = tbAttachmentMapper.toDto(tbAttachment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbAttachmentMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tbAttachmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TbAttachment in the database
        List<TbAttachment> tbAttachmentList = tbAttachmentRepository.findAll();
        assertThat(tbAttachmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTbAttachmentWithPatch() throws Exception {
        // Initialize the database
        tbAttachmentRepository.saveAndFlush(tbAttachment);

        int databaseSizeBeforeUpdate = tbAttachmentRepository.findAll().size();

        // Update the tbAttachment using partial update
        TbAttachment partialUpdatedTbAttachment = new TbAttachment();
        partialUpdatedTbAttachment.setId(tbAttachment.getId());

        partialUpdatedTbAttachment.name(UPDATED_NAME).fileSize(UPDATED_FILE_SIZE).regDate(UPDATED_REG_DATE).modUser(UPDATED_MOD_USER);

        restTbAttachmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTbAttachment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTbAttachment))
            )
            .andExpect(status().isOk());

        // Validate the TbAttachment in the database
        List<TbAttachment> tbAttachmentList = tbAttachmentRepository.findAll();
        assertThat(tbAttachmentList).hasSize(databaseSizeBeforeUpdate);
        TbAttachment testTbAttachment = tbAttachmentList.get(tbAttachmentList.size() - 1);
        assertThat(testTbAttachment.getAttType()).isEqualTo(DEFAULT_ATT_TYPE);
        assertThat(testTbAttachment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTbAttachment.getPath()).isEqualTo(DEFAULT_PATH);
        assertThat(testTbAttachment.getFileSize()).isEqualTo(UPDATED_FILE_SIZE);
        assertThat(testTbAttachment.getExt()).isEqualTo(DEFAULT_EXT);
        assertThat(testTbAttachment.getRegUser()).isEqualTo(DEFAULT_REG_USER);
        assertThat(testTbAttachment.getRegDate()).isEqualTo(UPDATED_REG_DATE);
        assertThat(testTbAttachment.getModUser()).isEqualTo(UPDATED_MOD_USER);
        assertThat(testTbAttachment.getModDate()).isEqualTo(DEFAULT_MOD_DATE);
    }

    @Test
    @Transactional
    void fullUpdateTbAttachmentWithPatch() throws Exception {
        // Initialize the database
        tbAttachmentRepository.saveAndFlush(tbAttachment);

        int databaseSizeBeforeUpdate = tbAttachmentRepository.findAll().size();

        // Update the tbAttachment using partial update
        TbAttachment partialUpdatedTbAttachment = new TbAttachment();
        partialUpdatedTbAttachment.setId(tbAttachment.getId());

        partialUpdatedTbAttachment
            .attType(UPDATED_ATT_TYPE)
            .name(UPDATED_NAME)
            .path(UPDATED_PATH)
            .fileSize(UPDATED_FILE_SIZE)
            .ext(UPDATED_EXT)
            .regUser(UPDATED_REG_USER)
            .regDate(UPDATED_REG_DATE)
            .modUser(UPDATED_MOD_USER)
            .modDate(UPDATED_MOD_DATE);

        restTbAttachmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTbAttachment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTbAttachment))
            )
            .andExpect(status().isOk());

        // Validate the TbAttachment in the database
        List<TbAttachment> tbAttachmentList = tbAttachmentRepository.findAll();
        assertThat(tbAttachmentList).hasSize(databaseSizeBeforeUpdate);
        TbAttachment testTbAttachment = tbAttachmentList.get(tbAttachmentList.size() - 1);
        assertThat(testTbAttachment.getAttType()).isEqualTo(UPDATED_ATT_TYPE);
        assertThat(testTbAttachment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTbAttachment.getPath()).isEqualTo(UPDATED_PATH);
        assertThat(testTbAttachment.getFileSize()).isEqualTo(UPDATED_FILE_SIZE);
        assertThat(testTbAttachment.getExt()).isEqualTo(UPDATED_EXT);
        assertThat(testTbAttachment.getRegUser()).isEqualTo(UPDATED_REG_USER);
        assertThat(testTbAttachment.getRegDate()).isEqualTo(UPDATED_REG_DATE);
        assertThat(testTbAttachment.getModUser()).isEqualTo(UPDATED_MOD_USER);
        assertThat(testTbAttachment.getModDate()).isEqualTo(UPDATED_MOD_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingTbAttachment() throws Exception {
        int databaseSizeBeforeUpdate = tbAttachmentRepository.findAll().size();
        tbAttachment.setId(count.incrementAndGet());

        // Create the TbAttachment
        TbAttachmentDTO tbAttachmentDTO = tbAttachmentMapper.toDto(tbAttachment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTbAttachmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tbAttachmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tbAttachmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbAttachment in the database
        List<TbAttachment> tbAttachmentList = tbAttachmentRepository.findAll();
        assertThat(tbAttachmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTbAttachment() throws Exception {
        int databaseSizeBeforeUpdate = tbAttachmentRepository.findAll().size();
        tbAttachment.setId(count.incrementAndGet());

        // Create the TbAttachment
        TbAttachmentDTO tbAttachmentDTO = tbAttachmentMapper.toDto(tbAttachment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbAttachmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tbAttachmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbAttachment in the database
        List<TbAttachment> tbAttachmentList = tbAttachmentRepository.findAll();
        assertThat(tbAttachmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTbAttachment() throws Exception {
        int databaseSizeBeforeUpdate = tbAttachmentRepository.findAll().size();
        tbAttachment.setId(count.incrementAndGet());

        // Create the TbAttachment
        TbAttachmentDTO tbAttachmentDTO = tbAttachmentMapper.toDto(tbAttachment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbAttachmentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tbAttachmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TbAttachment in the database
        List<TbAttachment> tbAttachmentList = tbAttachmentRepository.findAll();
        assertThat(tbAttachmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTbAttachment() throws Exception {
        // Initialize the database
        tbAttachmentRepository.saveAndFlush(tbAttachment);

        int databaseSizeBeforeDelete = tbAttachmentRepository.findAll().size();

        // Delete the tbAttachment
        restTbAttachmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, tbAttachment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TbAttachment> tbAttachmentList = tbAttachmentRepository.findAll();
        assertThat(tbAttachmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
