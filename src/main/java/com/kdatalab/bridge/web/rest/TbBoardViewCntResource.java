package com.kdatalab.bridge.web.rest;

import com.kdatalab.bridge.repository.TbBoardViewCntRepository;
import com.kdatalab.bridge.service.TbBoardViewCntService;
import com.kdatalab.bridge.service.dto.TbBoardViewCntDTO;
import com.kdatalab.bridge.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.kdatalab.bridge.domain.TbBoardViewCnt}.
 */
@RestController
@RequestMapping("/api")
public class TbBoardViewCntResource {

    private final Logger log = LoggerFactory.getLogger(TbBoardViewCntResource.class);

    private static final String ENTITY_NAME = "tbBoardViewCnt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TbBoardViewCntService tbBoardViewCntService;

    private final TbBoardViewCntRepository tbBoardViewCntRepository;

    public TbBoardViewCntResource(TbBoardViewCntService tbBoardViewCntService, TbBoardViewCntRepository tbBoardViewCntRepository) {
        this.tbBoardViewCntService = tbBoardViewCntService;
        this.tbBoardViewCntRepository = tbBoardViewCntRepository;
    }

    /**
     * {@code POST  /tb-board-view-cnts} : Create a new tbBoardViewCnt.
     *
     * @param tbBoardViewCntDTO the tbBoardViewCntDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tbBoardViewCntDTO, or with status {@code 400 (Bad Request)} if the tbBoardViewCnt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tb-board-view-cnts")
    public ResponseEntity<TbBoardViewCntDTO> createTbBoardViewCnt(@RequestBody TbBoardViewCntDTO tbBoardViewCntDTO)
        throws URISyntaxException {
        log.debug("REST request to save TbBoardViewCnt : {}", tbBoardViewCntDTO);
        if (tbBoardViewCntDTO.getId() != null) {
            throw new BadRequestAlertException("A new tbBoardViewCnt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TbBoardViewCntDTO result = tbBoardViewCntService.save(tbBoardViewCntDTO);
        return ResponseEntity
            .created(new URI("/api/tb-board-view-cnts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tb-board-view-cnts/:id} : Updates an existing tbBoardViewCnt.
     *
     * @param id the id of the tbBoardViewCntDTO to save.
     * @param tbBoardViewCntDTO the tbBoardViewCntDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tbBoardViewCntDTO,
     * or with status {@code 400 (Bad Request)} if the tbBoardViewCntDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tbBoardViewCntDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tb-board-view-cnts/{id}")
    public ResponseEntity<TbBoardViewCntDTO> updateTbBoardViewCnt(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TbBoardViewCntDTO tbBoardViewCntDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TbBoardViewCnt : {}, {}", id, tbBoardViewCntDTO);
        if (tbBoardViewCntDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tbBoardViewCntDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tbBoardViewCntRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TbBoardViewCntDTO result = tbBoardViewCntService.update(tbBoardViewCntDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tbBoardViewCntDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tb-board-view-cnts/:id} : Partial updates given fields of an existing tbBoardViewCnt, field will ignore if it is null
     *
     * @param id the id of the tbBoardViewCntDTO to save.
     * @param tbBoardViewCntDTO the tbBoardViewCntDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tbBoardViewCntDTO,
     * or with status {@code 400 (Bad Request)} if the tbBoardViewCntDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tbBoardViewCntDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tbBoardViewCntDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tb-board-view-cnts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TbBoardViewCntDTO> partialUpdateTbBoardViewCnt(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TbBoardViewCntDTO tbBoardViewCntDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TbBoardViewCnt partially : {}, {}", id, tbBoardViewCntDTO);
        if (tbBoardViewCntDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tbBoardViewCntDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tbBoardViewCntRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TbBoardViewCntDTO> result = tbBoardViewCntService.partialUpdate(tbBoardViewCntDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tbBoardViewCntDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tb-board-view-cnts} : get all the tbBoardViewCnts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tbBoardViewCnts in body.
     */
    @GetMapping("/tb-board-view-cnts")
    public List<TbBoardViewCntDTO> getAllTbBoardViewCnts() {
        log.debug("REST request to get all TbBoardViewCnts");
        return tbBoardViewCntService.findAll();
    }

    /**
     * {@code GET  /tb-board-view-cnts/:id} : get the "id" tbBoardViewCnt.
     *
     * @param id the id of the tbBoardViewCntDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tbBoardViewCntDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tb-board-view-cnts/{id}")
    public ResponseEntity<TbBoardViewCntDTO> getTbBoardViewCnt(@PathVariable Long id) {
        log.debug("REST request to get TbBoardViewCnt : {}", id);
        Optional<TbBoardViewCntDTO> tbBoardViewCntDTO = tbBoardViewCntService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tbBoardViewCntDTO);
    }

    /**
     * {@code DELETE  /tb-board-view-cnts/:id} : delete the "id" tbBoardViewCnt.
     *
     * @param id the id of the tbBoardViewCntDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tb-board-view-cnts/{id}")
    public ResponseEntity<Void> deleteTbBoardViewCnt(@PathVariable Long id) {
        log.debug("REST request to delete TbBoardViewCnt : {}", id);
        tbBoardViewCntService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
