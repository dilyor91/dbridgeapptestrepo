package com.kdatalab.bridge.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.kdatalab.bridge.IntegrationTest;
import com.kdatalab.bridge.domain.TbBoard;
import com.kdatalab.bridge.repository.TbBoardRepository;
import com.kdatalab.bridge.service.dto.TbBoardDTO;
import com.kdatalab.bridge.service.mapper.TbBoardMapper;
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
 * Integration tests for the {@link TbBoardResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TbBoardResourceIT {

    private static final String DEFAULT_BD_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_BD_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_PUBLISHED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PUBLISHED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final Integer DEFAULT_BOARD_ORDER = 1;
    private static final Integer UPDATED_BOARD_ORDER = 2;

    private static final String DEFAULT_REG_USER = "AAAAAAAAAA";
    private static final String UPDATED_REG_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_REG_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REG_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MOD_USER = "AAAAAAAAAA";
    private static final String UPDATED_MOD_USER = "BBBBBBBBBB";

    private static final Instant DEFAULT_MOD_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MOD_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/tb-boards";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TbBoardRepository tbBoardRepository;

    @Autowired
    private TbBoardMapper tbBoardMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTbBoardMockMvc;

    private TbBoard tbBoard;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TbBoard createEntity(EntityManager em) {
        TbBoard tbBoard = new TbBoard()
            .bdType(DEFAULT_BD_TYPE)
            .title(DEFAULT_TITLE)
            .content(DEFAULT_CONTENT)
            .publishedDate(DEFAULT_PUBLISHED_DATE)
            .status(DEFAULT_STATUS)
            .boardOrder(DEFAULT_BOARD_ORDER)
            .regUser(DEFAULT_REG_USER)
            .regDate(DEFAULT_REG_DATE)
            .modUser(DEFAULT_MOD_USER)
            .modDate(DEFAULT_MOD_DATE);
        return tbBoard;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TbBoard createUpdatedEntity(EntityManager em) {
        TbBoard tbBoard = new TbBoard()
            .bdType(UPDATED_BD_TYPE)
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .publishedDate(UPDATED_PUBLISHED_DATE)
            .status(UPDATED_STATUS)
            .boardOrder(UPDATED_BOARD_ORDER)
            .regUser(UPDATED_REG_USER)
            .regDate(UPDATED_REG_DATE)
            .modUser(UPDATED_MOD_USER)
            .modDate(UPDATED_MOD_DATE);
        return tbBoard;
    }

    @BeforeEach
    public void initTest() {
        tbBoard = createEntity(em);
    }

    @Test
    @Transactional
    void createTbBoard() throws Exception {
        int databaseSizeBeforeCreate = tbBoardRepository.findAll().size();
        // Create the TbBoard
        TbBoardDTO tbBoardDTO = tbBoardMapper.toDto(tbBoard);
        restTbBoardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tbBoardDTO)))
            .andExpect(status().isCreated());

        // Validate the TbBoard in the database
        List<TbBoard> tbBoardList = tbBoardRepository.findAll();
        assertThat(tbBoardList).hasSize(databaseSizeBeforeCreate + 1);
        TbBoard testTbBoard = tbBoardList.get(tbBoardList.size() - 1);
        assertThat(testTbBoard.getBdType()).isEqualTo(DEFAULT_BD_TYPE);
        assertThat(testTbBoard.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testTbBoard.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testTbBoard.getPublishedDate()).isEqualTo(DEFAULT_PUBLISHED_DATE);
        assertThat(testTbBoard.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTbBoard.getBoardOrder()).isEqualTo(DEFAULT_BOARD_ORDER);
        assertThat(testTbBoard.getRegUser()).isEqualTo(DEFAULT_REG_USER);
        assertThat(testTbBoard.getRegDate()).isEqualTo(DEFAULT_REG_DATE);
        assertThat(testTbBoard.getModUser()).isEqualTo(DEFAULT_MOD_USER);
        assertThat(testTbBoard.getModDate()).isEqualTo(DEFAULT_MOD_DATE);
    }

    @Test
    @Transactional
    void createTbBoardWithExistingId() throws Exception {
        // Create the TbBoard with an existing ID
        tbBoard.setId(1L);
        TbBoardDTO tbBoardDTO = tbBoardMapper.toDto(tbBoard);

        int databaseSizeBeforeCreate = tbBoardRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTbBoardMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tbBoardDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TbBoard in the database
        List<TbBoard> tbBoardList = tbBoardRepository.findAll();
        assertThat(tbBoardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTbBoards() throws Exception {
        // Initialize the database
        tbBoardRepository.saveAndFlush(tbBoard);

        // Get all the tbBoardList
        restTbBoardMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tbBoard.getId().intValue())))
            .andExpect(jsonPath("$.[*].bdType").value(hasItem(DEFAULT_BD_TYPE)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].publishedDate").value(hasItem(DEFAULT_PUBLISHED_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].boardOrder").value(hasItem(DEFAULT_BOARD_ORDER)))
            .andExpect(jsonPath("$.[*].regUser").value(hasItem(DEFAULT_REG_USER)))
            .andExpect(jsonPath("$.[*].regDate").value(hasItem(DEFAULT_REG_DATE.toString())))
            .andExpect(jsonPath("$.[*].modUser").value(hasItem(DEFAULT_MOD_USER)))
            .andExpect(jsonPath("$.[*].modDate").value(hasItem(DEFAULT_MOD_DATE.toString())));
    }

    @Test
    @Transactional
    void getTbBoard() throws Exception {
        // Initialize the database
        tbBoardRepository.saveAndFlush(tbBoard);

        // Get the tbBoard
        restTbBoardMockMvc
            .perform(get(ENTITY_API_URL_ID, tbBoard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tbBoard.getId().intValue()))
            .andExpect(jsonPath("$.bdType").value(DEFAULT_BD_TYPE))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.publishedDate").value(DEFAULT_PUBLISHED_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.boardOrder").value(DEFAULT_BOARD_ORDER))
            .andExpect(jsonPath("$.regUser").value(DEFAULT_REG_USER))
            .andExpect(jsonPath("$.regDate").value(DEFAULT_REG_DATE.toString()))
            .andExpect(jsonPath("$.modUser").value(DEFAULT_MOD_USER))
            .andExpect(jsonPath("$.modDate").value(DEFAULT_MOD_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTbBoard() throws Exception {
        // Get the tbBoard
        restTbBoardMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTbBoard() throws Exception {
        // Initialize the database
        tbBoardRepository.saveAndFlush(tbBoard);

        int databaseSizeBeforeUpdate = tbBoardRepository.findAll().size();

        // Update the tbBoard
        TbBoard updatedTbBoard = tbBoardRepository.findById(tbBoard.getId()).get();
        // Disconnect from session so that the updates on updatedTbBoard are not directly saved in db
        em.detach(updatedTbBoard);
        updatedTbBoard
            .bdType(UPDATED_BD_TYPE)
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .publishedDate(UPDATED_PUBLISHED_DATE)
            .status(UPDATED_STATUS)
            .boardOrder(UPDATED_BOARD_ORDER)
            .regUser(UPDATED_REG_USER)
            .regDate(UPDATED_REG_DATE)
            .modUser(UPDATED_MOD_USER)
            .modDate(UPDATED_MOD_DATE);
        TbBoardDTO tbBoardDTO = tbBoardMapper.toDto(updatedTbBoard);

        restTbBoardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tbBoardDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tbBoardDTO))
            )
            .andExpect(status().isOk());

        // Validate the TbBoard in the database
        List<TbBoard> tbBoardList = tbBoardRepository.findAll();
        assertThat(tbBoardList).hasSize(databaseSizeBeforeUpdate);
        TbBoard testTbBoard = tbBoardList.get(tbBoardList.size() - 1);
        assertThat(testTbBoard.getBdType()).isEqualTo(UPDATED_BD_TYPE);
        assertThat(testTbBoard.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTbBoard.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testTbBoard.getPublishedDate()).isEqualTo(UPDATED_PUBLISHED_DATE);
        assertThat(testTbBoard.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTbBoard.getBoardOrder()).isEqualTo(UPDATED_BOARD_ORDER);
        assertThat(testTbBoard.getRegUser()).isEqualTo(UPDATED_REG_USER);
        assertThat(testTbBoard.getRegDate()).isEqualTo(UPDATED_REG_DATE);
        assertThat(testTbBoard.getModUser()).isEqualTo(UPDATED_MOD_USER);
        assertThat(testTbBoard.getModDate()).isEqualTo(UPDATED_MOD_DATE);
    }

    @Test
    @Transactional
    void putNonExistingTbBoard() throws Exception {
        int databaseSizeBeforeUpdate = tbBoardRepository.findAll().size();
        tbBoard.setId(count.incrementAndGet());

        // Create the TbBoard
        TbBoardDTO tbBoardDTO = tbBoardMapper.toDto(tbBoard);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTbBoardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tbBoardDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tbBoardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbBoard in the database
        List<TbBoard> tbBoardList = tbBoardRepository.findAll();
        assertThat(tbBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTbBoard() throws Exception {
        int databaseSizeBeforeUpdate = tbBoardRepository.findAll().size();
        tbBoard.setId(count.incrementAndGet());

        // Create the TbBoard
        TbBoardDTO tbBoardDTO = tbBoardMapper.toDto(tbBoard);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbBoardMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tbBoardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbBoard in the database
        List<TbBoard> tbBoardList = tbBoardRepository.findAll();
        assertThat(tbBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTbBoard() throws Exception {
        int databaseSizeBeforeUpdate = tbBoardRepository.findAll().size();
        tbBoard.setId(count.incrementAndGet());

        // Create the TbBoard
        TbBoardDTO tbBoardDTO = tbBoardMapper.toDto(tbBoard);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbBoardMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tbBoardDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TbBoard in the database
        List<TbBoard> tbBoardList = tbBoardRepository.findAll();
        assertThat(tbBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTbBoardWithPatch() throws Exception {
        // Initialize the database
        tbBoardRepository.saveAndFlush(tbBoard);

        int databaseSizeBeforeUpdate = tbBoardRepository.findAll().size();

        // Update the tbBoard using partial update
        TbBoard partialUpdatedTbBoard = new TbBoard();
        partialUpdatedTbBoard.setId(tbBoard.getId());

        partialUpdatedTbBoard
            .bdType(UPDATED_BD_TYPE)
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .status(UPDATED_STATUS)
            .boardOrder(UPDATED_BOARD_ORDER)
            .regUser(UPDATED_REG_USER)
            .regDate(UPDATED_REG_DATE)
            .modUser(UPDATED_MOD_USER);

        restTbBoardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTbBoard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTbBoard))
            )
            .andExpect(status().isOk());

        // Validate the TbBoard in the database
        List<TbBoard> tbBoardList = tbBoardRepository.findAll();
        assertThat(tbBoardList).hasSize(databaseSizeBeforeUpdate);
        TbBoard testTbBoard = tbBoardList.get(tbBoardList.size() - 1);
        assertThat(testTbBoard.getBdType()).isEqualTo(UPDATED_BD_TYPE);
        assertThat(testTbBoard.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTbBoard.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testTbBoard.getPublishedDate()).isEqualTo(DEFAULT_PUBLISHED_DATE);
        assertThat(testTbBoard.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTbBoard.getBoardOrder()).isEqualTo(UPDATED_BOARD_ORDER);
        assertThat(testTbBoard.getRegUser()).isEqualTo(UPDATED_REG_USER);
        assertThat(testTbBoard.getRegDate()).isEqualTo(UPDATED_REG_DATE);
        assertThat(testTbBoard.getModUser()).isEqualTo(UPDATED_MOD_USER);
        assertThat(testTbBoard.getModDate()).isEqualTo(DEFAULT_MOD_DATE);
    }

    @Test
    @Transactional
    void fullUpdateTbBoardWithPatch() throws Exception {
        // Initialize the database
        tbBoardRepository.saveAndFlush(tbBoard);

        int databaseSizeBeforeUpdate = tbBoardRepository.findAll().size();

        // Update the tbBoard using partial update
        TbBoard partialUpdatedTbBoard = new TbBoard();
        partialUpdatedTbBoard.setId(tbBoard.getId());

        partialUpdatedTbBoard
            .bdType(UPDATED_BD_TYPE)
            .title(UPDATED_TITLE)
            .content(UPDATED_CONTENT)
            .publishedDate(UPDATED_PUBLISHED_DATE)
            .status(UPDATED_STATUS)
            .boardOrder(UPDATED_BOARD_ORDER)
            .regUser(UPDATED_REG_USER)
            .regDate(UPDATED_REG_DATE)
            .modUser(UPDATED_MOD_USER)
            .modDate(UPDATED_MOD_DATE);

        restTbBoardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTbBoard.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTbBoard))
            )
            .andExpect(status().isOk());

        // Validate the TbBoard in the database
        List<TbBoard> tbBoardList = tbBoardRepository.findAll();
        assertThat(tbBoardList).hasSize(databaseSizeBeforeUpdate);
        TbBoard testTbBoard = tbBoardList.get(tbBoardList.size() - 1);
        assertThat(testTbBoard.getBdType()).isEqualTo(UPDATED_BD_TYPE);
        assertThat(testTbBoard.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testTbBoard.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testTbBoard.getPublishedDate()).isEqualTo(UPDATED_PUBLISHED_DATE);
        assertThat(testTbBoard.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTbBoard.getBoardOrder()).isEqualTo(UPDATED_BOARD_ORDER);
        assertThat(testTbBoard.getRegUser()).isEqualTo(UPDATED_REG_USER);
        assertThat(testTbBoard.getRegDate()).isEqualTo(UPDATED_REG_DATE);
        assertThat(testTbBoard.getModUser()).isEqualTo(UPDATED_MOD_USER);
        assertThat(testTbBoard.getModDate()).isEqualTo(UPDATED_MOD_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingTbBoard() throws Exception {
        int databaseSizeBeforeUpdate = tbBoardRepository.findAll().size();
        tbBoard.setId(count.incrementAndGet());

        // Create the TbBoard
        TbBoardDTO tbBoardDTO = tbBoardMapper.toDto(tbBoard);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTbBoardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tbBoardDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tbBoardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbBoard in the database
        List<TbBoard> tbBoardList = tbBoardRepository.findAll();
        assertThat(tbBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTbBoard() throws Exception {
        int databaseSizeBeforeUpdate = tbBoardRepository.findAll().size();
        tbBoard.setId(count.incrementAndGet());

        // Create the TbBoard
        TbBoardDTO tbBoardDTO = tbBoardMapper.toDto(tbBoard);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbBoardMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tbBoardDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TbBoard in the database
        List<TbBoard> tbBoardList = tbBoardRepository.findAll();
        assertThat(tbBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTbBoard() throws Exception {
        int databaseSizeBeforeUpdate = tbBoardRepository.findAll().size();
        tbBoard.setId(count.incrementAndGet());

        // Create the TbBoard
        TbBoardDTO tbBoardDTO = tbBoardMapper.toDto(tbBoard);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTbBoardMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tbBoardDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TbBoard in the database
        List<TbBoard> tbBoardList = tbBoardRepository.findAll();
        assertThat(tbBoardList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTbBoard() throws Exception {
        // Initialize the database
        tbBoardRepository.saveAndFlush(tbBoard);

        int databaseSizeBeforeDelete = tbBoardRepository.findAll().size();

        // Delete the tbBoard
        restTbBoardMockMvc
            .perform(delete(ENTITY_API_URL_ID, tbBoard.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TbBoard> tbBoardList = tbBoardRepository.findAll();
        assertThat(tbBoardList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
