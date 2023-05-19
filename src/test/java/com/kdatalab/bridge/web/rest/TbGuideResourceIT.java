package com.kdatalab.bridge.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kdatalab.bridge.IntegrationTest;
import com.kdatalab.bridge.domain.TbGuide;
import com.kdatalab.bridge.repository.TbGuideRepository;
import com.kdatalab.bridge.service.dto.TbGuideDTO;
import com.kdatalab.bridge.service.mapper.TbGuideMapper;
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
 * Integration tests for the {@link TbGuideResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TbGuideResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_LINK = "AAAAAAAAAA";
    private static final String UPDATED_LINK = "BBBBBBBBBB";

    private static final String DEFAULT_REG_USER = "AAAAAAAAAA";
    private static final String UPDATED_REG_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_REG_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REG_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MOD_USER = "AAAAAAAAAA";
    private static final String UPDATED_MOD_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_MOD_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MOD_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/tb-guides";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TbGuideRepository tbGuideRepository;

    @Autowired
    private TbGuideMapper tbGuideMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTbGuideMockMvc;

    private TbGuide tbGuide;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TbGuide createEntity(EntityManager em) {
        TbGuide tbGuide = new TbGuide()
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .status(DEFAULT_STATUS)
            .link(DEFAULT_LINK)
            .regUser(DEFAULT_REG_USER)
            .regDate(DEFAULT_REG_DATE)
            .modUser(DEFAULT_MOD_USER)
            .modDate(DEFAULT_MOD_DATE);
        return tbGuide;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TbGuide createUpdatedEntity(EntityManager em) {
        TbGuide tbGuide = new TbGuide()
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .status(UPDATED_STATUS)
            .link(UPDATED_LINK)
            .regUser(UPDATED_REG_USER)
            .regDate(UPDATED_REG_DATE)
            .modUser(UPDATED_MOD_USER)
            .modDate(UPDATED_MOD_DATE);
        return tbGuide;
    }

    @BeforeEach
    public void initTest() {
        tbGuide = createEntity(em);
    }

    @Test
    @Transactional
    void createTbGuide() throws Exception {
        int databaseSizeBeforeCreate = tbGuideRepository.findAll().size();
        // Create the TbGuide
        TbGuideDTO tbGuideDTO = tbGuideMapper.toDto(tbGuide);
        restTbGuideMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tbGuideDTO)))
            .andExpect(status().isCreated());

        // Validate the TbGuide in the database
        List<TbGuide> tbGuideList = tbGuideRepository.findAll();
        assertThat(tbGuideList).hasSize(databaseSizeBeforeCreate + 1);
        TbGuide testTbGuide = tbGuideList.get(tbGuideList.size() - 1);
        assertThat(testTbGuide.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTbGuide.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testTbGuide.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTbGuide.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testTbGuide.getRegUser()).isEqualTo(DEFAULT_REG_USER);
        assertThat(testTbGuide.getRegDate()).isEqualTo(DEFAULT_REG_DATE);
        assertThat(testTbGuide.getModUser()).isEqualTo(DEFAULT_MOD_USER);
        assertThat(testTbGuide.getModDate()).isEqualTo(DEFAULT_MOD_DATE);
    }

    @Test
    @Transactional
    void createTbGuideWithExistingId() throws Exception {
        // Create the TbGuide with an existing ID
        tbGuide.setId(1L);
        TbGuideDTO tbGuideDTO = tbGuideMapper.toDto(tbGuide);

        int databaseSizeBeforeCreate = tbGuideRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbGuideMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tbGuideDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TbGuide in the database
        List<TbGuide> tbGuideList = tbGuideRepository.findAll();
        assertThat(tbGuideList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTbGuides() throws Exception {
        // Initialize the database
        tbGuideRepository.saveAndFlush(tbGuide);

        // Get all the tbGuideList
        restTbGuideMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbGuide.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].link").value(hasItem(DEFAULT_LINK)))
            .andExpect(jsonPath("$.[*].regUser").value(hasItem(DEFAULT_REG_USER)))
            .andExpect(jsonPath("$.[*].regDate").value(hasItem(DEFAULT_REG_DATE.toString())))
            .andExpect(jsonPath("$.[*].modUser").value(hasItem(DEFAULT_MOD_USER)))
            .andExpect(jsonPath("$.[*].modDate").value(hasItem(DEFAULT_MOD_DATE.toString())));
    }

    @Test
    @Transactional
    void getTbGuide() throws Exception {
        // Initialize the database
        tbGuideRepository.saveAndFlush(tbGuide);

        // Get the tbGuide
        restTbGuideMockMvc
            .perform(get(ENTITY_API_URL_ID, tbGuide.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tbGuide.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.link").value(DEFAULT_LINK))
            .andExpect(jsonPath("$.regUser").value(DEFAULT_REG_USER))
            .andExpect(jsonPath("$.regDate").value(DEFAULT_REG_DATE.toString()))
            .andExpect(jsonPath("$.modUser").value(DEFAULT_MOD_USER))
            .andExpect(jsonPath("$.modDate").value(DEFAULT_MOD_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTbGuide() throws Exception {
        // Get the tbGuide
        restTbGuideMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTbGuide() throws Exception {
        // Initialize the database
        tbGuideRepository.saveAndFlush(tbGuide);

        int databaseSizeBeforeUpdate = tbGuideRepository.findAll().size();

        // Update the tbGuide
        TbGuide updatedTbGuide = tbGuideRepository.findById(tbGuide.getId()).get();
        // Disconnect from session so that the updates on updatedTbGuide are not directly saved in db
        em.detach(updatedTbGuide);
        updatedTbGuide
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .status(UPDATED_STATUS)
            .link(UPDATED_LINK)
            .regUser(UPDATED_REG_USER)
            .regDate(UPDATED_REG_DATE)
            .modUser(UPDATED_MOD_USER)
            .modDate(UPDATED_MOD_DATE);
        TbGuideDTO tbGuideDTO = tbGuideMapper.toDto(updatedTbGuide);

        restTbGuideMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tbGuideDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tbGuideDTO))
            )
            .andExpect(status().isOk());

        // Validate the TbGuide in the database
        List<TbGuide> tbGuideList = tbGuideRepository.findAll();
        assertThat(tbGuideList).hasSize(databaseSizeBeforeUpdate);
        TbGuide testTbGuide = tbGuideList.get(tbGuideList.size() - 1);
        assertThat(testTbGuide.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTbGuide.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testTbGuide.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTbGuide.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testTbGuide.getRegUser()).isEqualTo(UPDATED_REG_USER);
        assertThat(testTbGuide.getRegDate()).isEqualTo(UPDATED_REG_DATE);
        assertThat(testTbGuide.getModUser()).isEqualTo(UPDATED_MOD_USER);
        assertThat(testTbGuide.getModDate()).isEqualTo(UPDATED_MOD_DATE);
    }

    @Test
    @Transactional
    void putNonExistingTbGuide() throws Exception {
        int databaseSizeBeforeUpdate = tbGuideRepository.findAll().size();
        tbGuide.setId(count.incrementAndGet());

        // Create the TbGuide
        TbGuideDTO tbGuideDTO = tbGuideMapper.toDto(tbGuide);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTbGuideMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tbGuideDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tbGuideDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbGuide in the database
        List<TbGuide> tbGuideList = tbGuideRepository.findAll();
        assertThat(tbGuideList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTbGuide() throws Exception {
        int databaseSizeBeforeUpdate = tbGuideRepository.findAll().size();
        tbGuide.setId(count.incrementAndGet());

        // Create the TbGuide
        TbGuideDTO tbGuideDTO = tbGuideMapper.toDto(tbGuide);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbGuideMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tbGuideDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbGuide in the database
        List<TbGuide> tbGuideList = tbGuideRepository.findAll();
        assertThat(tbGuideList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTbGuide() throws Exception {
        int databaseSizeBeforeUpdate = tbGuideRepository.findAll().size();
        tbGuide.setId(count.incrementAndGet());

        // Create the TbGuide
        TbGuideDTO tbGuideDTO = tbGuideMapper.toDto(tbGuide);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbGuideMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tbGuideDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TbGuide in the database
        List<TbGuide> tbGuideList = tbGuideRepository.findAll();
        assertThat(tbGuideList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTbGuideWithPatch() throws Exception {
        // Initialize the database
        tbGuideRepository.saveAndFlush(tbGuide);

        int databaseSizeBeforeUpdate = tbGuideRepository.findAll().size();

        // Update the tbGuide using partial update
        TbGuide partialUpdatedTbGuide = new TbGuide();
        partialUpdatedTbGuide.setId(tbGuide.getId());

        partialUpdatedTbGuide
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .regUser(UPDATED_REG_USER)
            .regDate(UPDATED_REG_DATE)
            .modUser(UPDATED_MOD_USER)
            .modDate(UPDATED_MOD_DATE);

        restTbGuideMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTbGuide.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTbGuide))
            )
            .andExpect(status().isOk());

        // Validate the TbGuide in the database
        List<TbGuide> tbGuideList = tbGuideRepository.findAll();
        assertThat(tbGuideList).hasSize(databaseSizeBeforeUpdate);
        TbGuide testTbGuide = tbGuideList.get(tbGuideList.size() - 1);
        assertThat(testTbGuide.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTbGuide.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testTbGuide.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTbGuide.getLink()).isEqualTo(DEFAULT_LINK);
        assertThat(testTbGuide.getRegUser()).isEqualTo(UPDATED_REG_USER);
        assertThat(testTbGuide.getRegDate()).isEqualTo(UPDATED_REG_DATE);
        assertThat(testTbGuide.getModUser()).isEqualTo(UPDATED_MOD_USER);
        assertThat(testTbGuide.getModDate()).isEqualTo(UPDATED_MOD_DATE);
    }

    @Test
    @Transactional
    void fullUpdateTbGuideWithPatch() throws Exception {
        // Initialize the database
        tbGuideRepository.saveAndFlush(tbGuide);

        int databaseSizeBeforeUpdate = tbGuideRepository.findAll().size();

        // Update the tbGuide using partial update
        TbGuide partialUpdatedTbGuide = new TbGuide();
        partialUpdatedTbGuide.setId(tbGuide.getId());

        partialUpdatedTbGuide
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .status(UPDATED_STATUS)
            .link(UPDATED_LINK)
            .regUser(UPDATED_REG_USER)
            .regDate(UPDATED_REG_DATE)
            .modUser(UPDATED_MOD_USER)
            .modDate(UPDATED_MOD_DATE);

        restTbGuideMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTbGuide.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTbGuide))
            )
            .andExpect(status().isOk());

        // Validate the TbGuide in the database
        List<TbGuide> tbGuideList = tbGuideRepository.findAll();
        assertThat(tbGuideList).hasSize(databaseSizeBeforeUpdate);
        TbGuide testTbGuide = tbGuideList.get(tbGuideList.size() - 1);
        assertThat(testTbGuide.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTbGuide.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testTbGuide.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTbGuide.getLink()).isEqualTo(UPDATED_LINK);
        assertThat(testTbGuide.getRegUser()).isEqualTo(UPDATED_REG_USER);
        assertThat(testTbGuide.getRegDate()).isEqualTo(UPDATED_REG_DATE);
        assertThat(testTbGuide.getModUser()).isEqualTo(UPDATED_MOD_USER);
        assertThat(testTbGuide.getModDate()).isEqualTo(UPDATED_MOD_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingTbGuide() throws Exception {
        int databaseSizeBeforeUpdate = tbGuideRepository.findAll().size();
        tbGuide.setId(count.incrementAndGet());

        // Create the TbGuide
        TbGuideDTO tbGuideDTO = tbGuideMapper.toDto(tbGuide);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTbGuideMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tbGuideDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tbGuideDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbGuide in the database
        List<TbGuide> tbGuideList = tbGuideRepository.findAll();
        assertThat(tbGuideList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTbGuide() throws Exception {
        int databaseSizeBeforeUpdate = tbGuideRepository.findAll().size();
        tbGuide.setId(count.incrementAndGet());

        // Create the TbGuide
        TbGuideDTO tbGuideDTO = tbGuideMapper.toDto(tbGuide);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbGuideMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tbGuideDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbGuide in the database
        List<TbGuide> tbGuideList = tbGuideRepository.findAll();
        assertThat(tbGuideList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTbGuide() throws Exception {
        int databaseSizeBeforeUpdate = tbGuideRepository.findAll().size();
        tbGuide.setId(count.incrementAndGet());

        // Create the TbGuide
        TbGuideDTO tbGuideDTO = tbGuideMapper.toDto(tbGuide);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbGuideMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tbGuideDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TbGuide in the database
        List<TbGuide> tbGuideList = tbGuideRepository.findAll();
        assertThat(tbGuideList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTbGuide() throws Exception {
        // Initialize the database
        tbGuideRepository.saveAndFlush(tbGuide);

        int databaseSizeBeforeDelete = tbGuideRepository.findAll().size();

        // Delete the tbGuide
        restTbGuideMockMvc
            .perform(delete(ENTITY_API_URL_ID, tbGuide.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TbGuide> tbGuideList = tbGuideRepository.findAll();
        assertThat(tbGuideList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
