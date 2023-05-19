package com.kdatalab.bridge.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kdatalab.bridge.IntegrationTest;
import com.kdatalab.bridge.domain.TbEduMst;
import com.kdatalab.bridge.repository.TbEduMstRepository;
import com.kdatalab.bridge.service.dto.TbEduMstDTO;
import com.kdatalab.bridge.service.mapper.TbEduMstMapper;
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
 * Integration tests for the {@link TbEduMstResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TbEduMstResourceIT {

    private static final Long DEFAULT_VIEW_CNT = 1L;
    private static final Long UPDATED_VIEW_CNT = 2L;

    private static final String ENTITY_API_URL = "/api/tb-edu-msts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TbEduMstRepository tbEduMstRepository;

    @Autowired
    private TbEduMstMapper tbEduMstMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTbEduMstMockMvc;

    private TbEduMst tbEduMst;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TbEduMst createEntity(EntityManager em) {
        TbEduMst tbEduMst = new TbEduMst().viewCnt(DEFAULT_VIEW_CNT);
        return tbEduMst;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TbEduMst createUpdatedEntity(EntityManager em) {
        TbEduMst tbEduMst = new TbEduMst().viewCnt(UPDATED_VIEW_CNT);
        return tbEduMst;
    }

    @BeforeEach
    public void initTest() {
        tbEduMst = createEntity(em);
    }

    @Test
    @Transactional
    void createTbEduMst() throws Exception {
        int databaseSizeBeforeCreate = tbEduMstRepository.findAll().size();
        // Create the TbEduMst
        TbEduMstDTO tbEduMstDTO = tbEduMstMapper.toDto(tbEduMst);
        restTbEduMstMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tbEduMstDTO)))
            .andExpect(status().isCreated());

        // Validate the TbEduMst in the database
        List<TbEduMst> tbEduMstList = tbEduMstRepository.findAll();
        assertThat(tbEduMstList).hasSize(databaseSizeBeforeCreate + 1);
        TbEduMst testTbEduMst = tbEduMstList.get(tbEduMstList.size() - 1);
        assertThat(testTbEduMst.getViewCnt()).isEqualTo(DEFAULT_VIEW_CNT);
    }

    @Test
    @Transactional
    void createTbEduMstWithExistingId() throws Exception {
        // Create the TbEduMst with an existing ID
        tbEduMst.setId(1L);
        TbEduMstDTO tbEduMstDTO = tbEduMstMapper.toDto(tbEduMst);

        int databaseSizeBeforeCreate = tbEduMstRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbEduMstMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tbEduMstDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TbEduMst in the database
        List<TbEduMst> tbEduMstList = tbEduMstRepository.findAll();
        assertThat(tbEduMstList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTbEduMsts() throws Exception {
        // Initialize the database
        tbEduMstRepository.saveAndFlush(tbEduMst);

        // Get all the tbEduMstList
        restTbEduMstMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbEduMst.getId().intValue())))
            .andExpect(jsonPath("$.[*].viewCnt").value(hasItem(DEFAULT_VIEW_CNT.intValue())));
    }

    @Test
    @Transactional
    void getTbEduMst() throws Exception {
        // Initialize the database
        tbEduMstRepository.saveAndFlush(tbEduMst);

        // Get the tbEduMst
        restTbEduMstMockMvc
            .perform(get(ENTITY_API_URL_ID, tbEduMst.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tbEduMst.getId().intValue()))
            .andExpect(jsonPath("$.viewCnt").value(DEFAULT_VIEW_CNT.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingTbEduMst() throws Exception {
        // Get the tbEduMst
        restTbEduMstMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTbEduMst() throws Exception {
        // Initialize the database
        tbEduMstRepository.saveAndFlush(tbEduMst);

        int databaseSizeBeforeUpdate = tbEduMstRepository.findAll().size();

        // Update the tbEduMst
        TbEduMst updatedTbEduMst = tbEduMstRepository.findById(tbEduMst.getId()).get();
        // Disconnect from session so that the updates on updatedTbEduMst are not directly saved in db
        em.detach(updatedTbEduMst);
        updatedTbEduMst.viewCnt(UPDATED_VIEW_CNT);
        TbEduMstDTO tbEduMstDTO = tbEduMstMapper.toDto(updatedTbEduMst);

        restTbEduMstMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tbEduMstDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tbEduMstDTO))
            )
            .andExpect(status().isOk());

        // Validate the TbEduMst in the database
        List<TbEduMst> tbEduMstList = tbEduMstRepository.findAll();
        assertThat(tbEduMstList).hasSize(databaseSizeBeforeUpdate);
        TbEduMst testTbEduMst = tbEduMstList.get(tbEduMstList.size() - 1);
        assertThat(testTbEduMst.getViewCnt()).isEqualTo(UPDATED_VIEW_CNT);
    }

    @Test
    @Transactional
    void putNonExistingTbEduMst() throws Exception {
        int databaseSizeBeforeUpdate = tbEduMstRepository.findAll().size();
        tbEduMst.setId(count.incrementAndGet());

        // Create the TbEduMst
        TbEduMstDTO tbEduMstDTO = tbEduMstMapper.toDto(tbEduMst);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTbEduMstMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tbEduMstDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tbEduMstDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbEduMst in the database
        List<TbEduMst> tbEduMstList = tbEduMstRepository.findAll();
        assertThat(tbEduMstList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTbEduMst() throws Exception {
        int databaseSizeBeforeUpdate = tbEduMstRepository.findAll().size();
        tbEduMst.setId(count.incrementAndGet());

        // Create the TbEduMst
        TbEduMstDTO tbEduMstDTO = tbEduMstMapper.toDto(tbEduMst);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbEduMstMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tbEduMstDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbEduMst in the database
        List<TbEduMst> tbEduMstList = tbEduMstRepository.findAll();
        assertThat(tbEduMstList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTbEduMst() throws Exception {
        int databaseSizeBeforeUpdate = tbEduMstRepository.findAll().size();
        tbEduMst.setId(count.incrementAndGet());

        // Create the TbEduMst
        TbEduMstDTO tbEduMstDTO = tbEduMstMapper.toDto(tbEduMst);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbEduMstMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tbEduMstDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TbEduMst in the database
        List<TbEduMst> tbEduMstList = tbEduMstRepository.findAll();
        assertThat(tbEduMstList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTbEduMstWithPatch() throws Exception {
        // Initialize the database
        tbEduMstRepository.saveAndFlush(tbEduMst);

        int databaseSizeBeforeUpdate = tbEduMstRepository.findAll().size();

        // Update the tbEduMst using partial update
        TbEduMst partialUpdatedTbEduMst = new TbEduMst();
        partialUpdatedTbEduMst.setId(tbEduMst.getId());

        restTbEduMstMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTbEduMst.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTbEduMst))
            )
            .andExpect(status().isOk());

        // Validate the TbEduMst in the database
        List<TbEduMst> tbEduMstList = tbEduMstRepository.findAll();
        assertThat(tbEduMstList).hasSize(databaseSizeBeforeUpdate);
        TbEduMst testTbEduMst = tbEduMstList.get(tbEduMstList.size() - 1);
        assertThat(testTbEduMst.getViewCnt()).isEqualTo(DEFAULT_VIEW_CNT);
    }

    @Test
    @Transactional
    void fullUpdateTbEduMstWithPatch() throws Exception {
        // Initialize the database
        tbEduMstRepository.saveAndFlush(tbEduMst);

        int databaseSizeBeforeUpdate = tbEduMstRepository.findAll().size();

        // Update the tbEduMst using partial update
        TbEduMst partialUpdatedTbEduMst = new TbEduMst();
        partialUpdatedTbEduMst.setId(tbEduMst.getId());

        partialUpdatedTbEduMst.viewCnt(UPDATED_VIEW_CNT);

        restTbEduMstMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTbEduMst.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTbEduMst))
            )
            .andExpect(status().isOk());

        // Validate the TbEduMst in the database
        List<TbEduMst> tbEduMstList = tbEduMstRepository.findAll();
        assertThat(tbEduMstList).hasSize(databaseSizeBeforeUpdate);
        TbEduMst testTbEduMst = tbEduMstList.get(tbEduMstList.size() - 1);
        assertThat(testTbEduMst.getViewCnt()).isEqualTo(UPDATED_VIEW_CNT);
    }

    @Test
    @Transactional
    void patchNonExistingTbEduMst() throws Exception {
        int databaseSizeBeforeUpdate = tbEduMstRepository.findAll().size();
        tbEduMst.setId(count.incrementAndGet());

        // Create the TbEduMst
        TbEduMstDTO tbEduMstDTO = tbEduMstMapper.toDto(tbEduMst);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTbEduMstMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tbEduMstDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tbEduMstDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbEduMst in the database
        List<TbEduMst> tbEduMstList = tbEduMstRepository.findAll();
        assertThat(tbEduMstList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTbEduMst() throws Exception {
        int databaseSizeBeforeUpdate = tbEduMstRepository.findAll().size();
        tbEduMst.setId(count.incrementAndGet());

        // Create the TbEduMst
        TbEduMstDTO tbEduMstDTO = tbEduMstMapper.toDto(tbEduMst);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbEduMstMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tbEduMstDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbEduMst in the database
        List<TbEduMst> tbEduMstList = tbEduMstRepository.findAll();
        assertThat(tbEduMstList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTbEduMst() throws Exception {
        int databaseSizeBeforeUpdate = tbEduMstRepository.findAll().size();
        tbEduMst.setId(count.incrementAndGet());

        // Create the TbEduMst
        TbEduMstDTO tbEduMstDTO = tbEduMstMapper.toDto(tbEduMst);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbEduMstMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tbEduMstDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TbEduMst in the database
        List<TbEduMst> tbEduMstList = tbEduMstRepository.findAll();
        assertThat(tbEduMstList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTbEduMst() throws Exception {
        // Initialize the database
        tbEduMstRepository.saveAndFlush(tbEduMst);

        int databaseSizeBeforeDelete = tbEduMstRepository.findAll().size();

        // Delete the tbEduMst
        restTbEduMstMockMvc
            .perform(delete(ENTITY_API_URL_ID, tbEduMst.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TbEduMst> tbEduMstList = tbEduMstRepository.findAll();
        assertThat(tbEduMstList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
