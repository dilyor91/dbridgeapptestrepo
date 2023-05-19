package com.kdatalab.bridge.web.rest;

import com.kdatalab.bridge.repository.TbEduMstRepository;
import com.kdatalab.bridge.service.TbEduMstService;
import com.kdatalab.bridge.service.dto.TbEduMstDTO;
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
 * REST controller for managing {@link com.kdatalab.bridge.domain.TbEduMst}.
 */
@RestController
@RequestMapping("/api")
public class TbEduMstResource {

    private final Logger log = LoggerFactory.getLogger(TbEduMstResource.class);

    private static final String ENTITY_NAME = "tbEduMst";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TbEduMstService tbEduMstService;

    private final TbEduMstRepository tbEduMstRepository;

    public TbEduMstResource(TbEduMstService tbEduMstService, TbEduMstRepository tbEduMstRepository) {
        this.tbEduMstService = tbEduMstService;
        this.tbEduMstRepository = tbEduMstRepository;
    }

    /**
     * {@code POST  /tb-edu-msts} : Create a new tbEduMst.
     *
     * @param tbEduMstDTO the tbEduMstDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tbEduMstDTO, or with status {@code 400 (Bad Request)} if the tbEduMst has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tb-edu-msts")
    public ResponseEntity<TbEduMstDTO> createTbEduMst(@RequestBody TbEduMstDTO tbEduMstDTO) throws URISyntaxException {
        log.debug("REST request to save TbEduMst : {}", tbEduMstDTO);
        if (tbEduMstDTO.getId() != null) {
            throw new BadRequestAlertException("A new tbEduMst cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TbEduMstDTO result = tbEduMstService.save(tbEduMstDTO);
        return ResponseEntity
            .created(new URI("/api/tb-edu-msts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tb-edu-msts/:id} : Updates an existing tbEduMst.
     *
     * @param id the id of the tbEduMstDTO to save.
     * @param tbEduMstDTO the tbEduMstDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tbEduMstDTO,
     * or with status {@code 400 (Bad Request)} if the tbEduMstDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tbEduMstDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tb-edu-msts/{id}")
    public ResponseEntity<TbEduMstDTO> updateTbEduMst(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TbEduMstDTO tbEduMstDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TbEduMst : {}, {}", id, tbEduMstDTO);
        if (tbEduMstDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tbEduMstDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tbEduMstRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TbEduMstDTO result = tbEduMstService.update(tbEduMstDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tbEduMstDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tb-edu-msts/:id} : Partial updates given fields of an existing tbEduMst, field will ignore if it is null
     *
     * @param id the id of the tbEduMstDTO to save.
     * @param tbEduMstDTO the tbEduMstDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tbEduMstDTO,
     * or with status {@code 400 (Bad Request)} if the tbEduMstDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tbEduMstDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tbEduMstDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tb-edu-msts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TbEduMstDTO> partialUpdateTbEduMst(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TbEduMstDTO tbEduMstDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TbEduMst partially : {}, {}", id, tbEduMstDTO);
        if (tbEduMstDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tbEduMstDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tbEduMstRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TbEduMstDTO> result = tbEduMstService.partialUpdate(tbEduMstDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tbEduMstDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tb-edu-msts} : get all the tbEduMsts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tbEduMsts in body.
     */
    @GetMapping("/tb-edu-msts")
    public List<TbEduMstDTO> getAllTbEduMsts() {
        log.debug("REST request to get all TbEduMsts");
        return tbEduMstService.findAll();
    }

    /**
     * {@code GET  /tb-edu-msts/:id} : get the "id" tbEduMst.
     *
     * @param id the id of the tbEduMstDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tbEduMstDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tb-edu-msts/{id}")
    public ResponseEntity<TbEduMstDTO> getTbEduMst(@PathVariable Long id) {
        log.debug("REST request to get TbEduMst : {}", id);
        Optional<TbEduMstDTO> tbEduMstDTO = tbEduMstService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tbEduMstDTO);
    }

    /**
     * {@code DELETE  /tb-edu-msts/:id} : delete the "id" tbEduMst.
     *
     * @param id the id of the tbEduMstDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tb-edu-msts/{id}")
    public ResponseEntity<Void> deleteTbEduMst(@PathVariable Long id) {
        log.debug("REST request to delete TbEduMst : {}", id);
        tbEduMstService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
