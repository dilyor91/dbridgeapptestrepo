package com.kdatalab.bridge.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kdatalab.bridge.IntegrationTest;
import com.kdatalab.bridge.domain.TbBoardViewCnt;
import com.kdatalab.bridge.repository.TbBoardViewCntRepository;
import com.kdatalab.bridge.service.dto.TbBoardViewCntDTO;
import com.kdatalab.bridge.service.mapper.TbBoardViewCntMapper;
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
 * Integration tests for the {@link TbBoardViewCntResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TbBoardViewCntResourceIT {

    private static final Long DEFAULT_VIEW_CNT = 1L;
    private static final Long UPDATED_VIEW_CNT = 2L;

    private static final String ENTITY_API_URL = "/api/tb-board-view-cnts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TbBoardViewCntRepository tbBoardViewCntRepository;

    @Autowired
    private TbBoardViewCntMapper tbBoardViewCntMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTbBoardViewCntMockMvc;

    private TbBoardViewCnt tbBoardViewCnt;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TbBoardViewCnt createEntity(EntityManager em) {
        TbBoardViewCnt tbBoardViewCnt = new TbBoardViewCnt().viewCnt(DEFAULT_VIEW_CNT);
        return tbBoardViewCnt;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TbBoardViewCnt createUpdatedEntity(EntityManager em) {
        TbBoardViewCnt tbBoardViewCnt = new TbBoardViewCnt().viewCnt(UPDATED_VIEW_CNT);
        return tbBoardViewCnt;
    }

    @BeforeEach
    public void initTest() {
        tbBoardViewCnt = createEntity(em);
    }

    @Test
    @Transactional
    void createTbBoardViewCnt() throws Exception {
        int databaseSizeBeforeCreate = tbBoardViewCntRepository.findAll().size();
        // Create the TbBoardViewCnt
        TbBoardViewCntDTO tbBoardViewCntDTO = tbBoardViewCntMapper.toDto(tbBoardViewCnt);
        restTbBoardViewCntMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tbBoardViewCntDTO))
            )
            .andExpect(status().isCreated());

        // Validate the TbBoardViewCnt in the database
        List<TbBoardViewCnt> tbBoardViewCntList = tbBoardViewCntRepository.findAll();
        assertThat(tbBoardViewCntList).hasSize(databaseSizeBeforeCreate + 1);
        TbBoardViewCnt testTbBoardViewCnt = tbBoardViewCntList.get(tbBoardViewCntList.size() - 1);
        assertThat(testTbBoardViewCnt.getViewCnt()).isEqualTo(DEFAULT_VIEW_CNT);
    }

    @Test
    @Transactional
    void createTbBoardViewCntWithExistingId() throws Exception {
        // Create the TbBoardViewCnt with an existing ID
        tbBoardViewCnt.setId(1L);
        TbBoardViewCntDTO tbBoardViewCntDTO = tbBoardViewCntMapper.toDto(tbBoardViewCnt);

        int databaseSizeBeforeCreate = tbBoardViewCntRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbBoardViewCntMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tbBoardViewCntDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbBoardViewCnt in the database
        List<TbBoardViewCnt> tbBoardViewCntList = tbBoardViewCntRepository.findAll();
        assertThat(tbBoardViewCntList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTbBoardViewCnts() throws Exception {
        // Initialize the database
        tbBoardViewCntRepository.saveAndFlush(tbBoardViewCnt);

        // Get all the tbBoardViewCntList
        restTbBoardViewCntMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbBoardViewCnt.getId().intValue())))
            .andExpect(jsonPath("$.[*].viewCnt").value(hasItem(DEFAULT_VIEW_CNT.intValue())));
    }

    @Test
    @Transactional
    void getTbBoardViewCnt() throws Exception {
        // Initialize the database
        tbBoardViewCntRepository.saveAndFlush(tbBoardViewCnt);

        // Get the tbBoardViewCnt
        restTbBoardViewCntMockMvc
            .perform(get(ENTITY_API_URL_ID, tbBoardViewCnt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tbBoardViewCnt.getId().intValue()))
            .andExpect(jsonPath("$.viewCnt").value(DEFAULT_VIEW_CNT.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingTbBoardViewCnt() throws Exception {
        // Get the tbBoardViewCnt
        restTbBoardViewCntMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTbBoardViewCnt() throws Exception {
        // Initialize the database
        tbBoardViewCntRepository.saveAndFlush(tbBoardViewCnt);

        int databaseSizeBeforeUpdate = tbBoardViewCntRepository.findAll().size();

        // Update the tbBoardViewCnt
        TbBoardViewCnt updatedTbBoardViewCnt = tbBoardViewCntRepository.findById(tbBoardViewCnt.getId()).get();
        // Disconnect from session so that the updates on updatedTbBoardViewCnt are not directly saved in db
        em.detach(updatedTbBoardViewCnt);
        updatedTbBoardViewCnt.viewCnt(UPDATED_VIEW_CNT);
        TbBoardViewCntDTO tbBoardViewCntDTO = tbBoardViewCntMapper.toDto(updatedTbBoardViewCnt);

        restTbBoardViewCntMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tbBoardViewCntDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tbBoardViewCntDTO))
            )
            .andExpect(status().isOk());

        // Validate the TbBoardViewCnt in the database
        List<TbBoardViewCnt> tbBoardViewCntList = tbBoardViewCntRepository.findAll();
        assertThat(tbBoardViewCntList).hasSize(databaseSizeBeforeUpdate);
        TbBoardViewCnt testTbBoardViewCnt = tbBoardViewCntList.get(tbBoardViewCntList.size() - 1);
        assertThat(testTbBoardViewCnt.getViewCnt()).isEqualTo(UPDATED_VIEW_CNT);
    }

    @Test
    @Transactional
    void putNonExistingTbBoardViewCnt() throws Exception {
        int databaseSizeBeforeUpdate = tbBoardViewCntRepository.findAll().size();
        tbBoardViewCnt.setId(count.incrementAndGet());

        // Create the TbBoardViewCnt
        TbBoardViewCntDTO tbBoardViewCntDTO = tbBoardViewCntMapper.toDto(tbBoardViewCnt);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTbBoardViewCntMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tbBoardViewCntDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tbBoardViewCntDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbBoardViewCnt in the database
        List<TbBoardViewCnt> tbBoardViewCntList = tbBoardViewCntRepository.findAll();
        assertThat(tbBoardViewCntList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTbBoardViewCnt() throws Exception {
        int databaseSizeBeforeUpdate = tbBoardViewCntRepository.findAll().size();
        tbBoardViewCnt.setId(count.incrementAndGet());

        // Create the TbBoardViewCnt
        TbBoardViewCntDTO tbBoardViewCntDTO = tbBoardViewCntMapper.toDto(tbBoardViewCnt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbBoardViewCntMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tbBoardViewCntDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbBoardViewCnt in the database
        List<TbBoardViewCnt> tbBoardViewCntList = tbBoardViewCntRepository.findAll();
        assertThat(tbBoardViewCntList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTbBoardViewCnt() throws Exception {
        int databaseSizeBeforeUpdate = tbBoardViewCntRepository.findAll().size();
        tbBoardViewCnt.setId(count.incrementAndGet());

        // Create the TbBoardViewCnt
        TbBoardViewCntDTO tbBoardViewCntDTO = tbBoardViewCntMapper.toDto(tbBoardViewCnt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbBoardViewCntMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tbBoardViewCntDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TbBoardViewCnt in the database
        List<TbBoardViewCnt> tbBoardViewCntList = tbBoardViewCntRepository.findAll();
        assertThat(tbBoardViewCntList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTbBoardViewCntWithPatch() throws Exception {
        // Initialize the database
        tbBoardViewCntRepository.saveAndFlush(tbBoardViewCnt);

        int databaseSizeBeforeUpdate = tbBoardViewCntRepository.findAll().size();

        // Update the tbBoardViewCnt using partial update
        TbBoardViewCnt partialUpdatedTbBoardViewCnt = new TbBoardViewCnt();
        partialUpdatedTbBoardViewCnt.setId(tbBoardViewCnt.getId());

        partialUpdatedTbBoardViewCnt.viewCnt(UPDATED_VIEW_CNT);

        restTbBoardViewCntMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTbBoardViewCnt.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTbBoardViewCnt))
            )
            .andExpect(status().isOk());

        // Validate the TbBoardViewCnt in the database
        List<TbBoardViewCnt> tbBoardViewCntList = tbBoardViewCntRepository.findAll();
        assertThat(tbBoardViewCntList).hasSize(databaseSizeBeforeUpdate);
        TbBoardViewCnt testTbBoardViewCnt = tbBoardViewCntList.get(tbBoardViewCntList.size() - 1);
        assertThat(testTbBoardViewCnt.getViewCnt()).isEqualTo(UPDATED_VIEW_CNT);
    }

    @Test
    @Transactional
    void fullUpdateTbBoardViewCntWithPatch() throws Exception {
        // Initialize the database
        tbBoardViewCntRepository.saveAndFlush(tbBoardViewCnt);

        int databaseSizeBeforeUpdate = tbBoardViewCntRepository.findAll().size();

        // Update the tbBoardViewCnt using partial update
        TbBoardViewCnt partialUpdatedTbBoardViewCnt = new TbBoardViewCnt();
        partialUpdatedTbBoardViewCnt.setId(tbBoardViewCnt.getId());

        partialUpdatedTbBoardViewCnt.viewCnt(UPDATED_VIEW_CNT);

        restTbBoardViewCntMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTbBoardViewCnt.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTbBoardViewCnt))
            )
            .andExpect(status().isOk());

        // Validate the TbBoardViewCnt in the database
        List<TbBoardViewCnt> tbBoardViewCntList = tbBoardViewCntRepository.findAll();
        assertThat(tbBoardViewCntList).hasSize(databaseSizeBeforeUpdate);
        TbBoardViewCnt testTbBoardViewCnt = tbBoardViewCntList.get(tbBoardViewCntList.size() - 1);
        assertThat(testTbBoardViewCnt.getViewCnt()).isEqualTo(UPDATED_VIEW_CNT);
    }

    @Test
    @Transactional
    void patchNonExistingTbBoardViewCnt() throws Exception {
        int databaseSizeBeforeUpdate = tbBoardViewCntRepository.findAll().size();
        tbBoardViewCnt.setId(count.incrementAndGet());

        // Create the TbBoardViewCnt
        TbBoardViewCntDTO tbBoardViewCntDTO = tbBoardViewCntMapper.toDto(tbBoardViewCnt);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTbBoardViewCntMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tbBoardViewCntDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tbBoardViewCntDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbBoardViewCnt in the database
        List<TbBoardViewCnt> tbBoardViewCntList = tbBoardViewCntRepository.findAll();
        assertThat(tbBoardViewCntList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTbBoardViewCnt() throws Exception {
        int databaseSizeBeforeUpdate = tbBoardViewCntRepository.findAll().size();
        tbBoardViewCnt.setId(count.incrementAndGet());

        // Create the TbBoardViewCnt
        TbBoardViewCntDTO tbBoardViewCntDTO = tbBoardViewCntMapper.toDto(tbBoardViewCnt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbBoardViewCntMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tbBoardViewCntDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbBoardViewCnt in the database
        List<TbBoardViewCnt> tbBoardViewCntList = tbBoardViewCntRepository.findAll();
        assertThat(tbBoardViewCntList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTbBoardViewCnt() throws Exception {
        int databaseSizeBeforeUpdate = tbBoardViewCntRepository.findAll().size();
        tbBoardViewCnt.setId(count.incrementAndGet());

        // Create the TbBoardViewCnt
        TbBoardViewCntDTO tbBoardViewCntDTO = tbBoardViewCntMapper.toDto(tbBoardViewCnt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbBoardViewCntMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tbBoardViewCntDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TbBoardViewCnt in the database
        List<TbBoardViewCnt> tbBoardViewCntList = tbBoardViewCntRepository.findAll();
        assertThat(tbBoardViewCntList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTbBoardViewCnt() throws Exception {
        // Initialize the database
        tbBoardViewCntRepository.saveAndFlush(tbBoardViewCnt);

        int databaseSizeBeforeDelete = tbBoardViewCntRepository.findAll().size();

        // Delete the tbBoardViewCnt
        restTbBoardViewCntMockMvc
            .perform(delete(ENTITY_API_URL_ID, tbBoardViewCnt.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TbBoardViewCnt> tbBoardViewCntList = tbBoardViewCntRepository.findAll();
        assertThat(tbBoardViewCntList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
