package com.kdatalab.bridge.web.rest;

import com.kdatalab.bridge.repository.TbBoardRepository;
import com.kdatalab.bridge.service.TbBoardService;
import com.kdatalab.bridge.service.dto.TbBoardDTO;
import com.kdatalab.bridge.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.kdatalab.bridge.domain.TbBoard}.
 */
@RestController
@RequestMapping("/api")
public class TbBoardResource {

    private final Logger log = LoggerFactory.getLogger(TbBoardResource.class);

    private static final String ENTITY_NAME = "tbBoard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TbBoardService tbBoardService;

    private final TbBoardRepository tbBoardRepository;

    public TbBoardResource(TbBoardService tbBoardService, TbBoardRepository tbBoardRepository) {
        this.tbBoardService = tbBoardService;
        this.tbBoardRepository = tbBoardRepository;
    }

    /**
     * {@code POST  /tb-boards} : Create a new tbBoard.
     *
     * @param tbBoardDTO the tbBoardDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tbBoardDTO, or with status {@code 400 (Bad Request)} if the tbBoard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tb-boards")
    public ResponseEntity<TbBoardDTO> createTbBoard(@RequestBody TbBoardDTO tbBoardDTO) throws URISyntaxException {
        log.debug("REST request to save TbBoard : {}", tbBoardDTO);
        if (tbBoardDTO.getId() != null) {
            throw new BadRequestAlertException("A new tbBoard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TbBoardDTO result = tbBoardService.save(tbBoardDTO);
        return ResponseEntity
            .created(new URI("/api/tb-boards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tb-boards/:id} : Updates an existing tbBoard.
     *
     * @param id the id of the tbBoardDTO to save.
     * @param tbBoardDTO the tbBoardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tbBoardDTO,
     * or with status {@code 400 (Bad Request)} if the tbBoardDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tbBoardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tb-boards/{id}")
    public ResponseEntity<TbBoardDTO> updateTbBoard(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TbBoardDTO tbBoardDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TbBoard : {}, {}", id, tbBoardDTO);
        if (tbBoardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tbBoardDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tbBoardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TbBoardDTO result = tbBoardService.update(tbBoardDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tbBoardDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tb-boards/:id} : Partial updates given fields of an existing tbBoard, field will ignore if it is null
     *
     * @param id the id of the tbBoardDTO to save.
     * @param tbBoardDTO the tbBoardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tbBoardDTO,
     * or with status {@code 400 (Bad Request)} if the tbBoardDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tbBoardDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tbBoardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tb-boards/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TbBoardDTO> partialUpdateTbBoard(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TbBoardDTO tbBoardDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TbBoard partially : {}, {}", id, tbBoardDTO);
        if (tbBoardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tbBoardDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tbBoardRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TbBoardDTO> result = tbBoardService.partialUpdate(tbBoardDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tbBoardDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tb-boards} : get all the tbBoards.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tbBoards in body.
     */
    @GetMapping("/tb-boards")
    public ResponseEntity<List<TbBoardDTO>> getAllTbBoards(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TbBoards");
        Page<TbBoardDTO> page = tbBoardService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tb-boards/:id} : get the "id" tbBoard.
     *
     * @param id the id of the tbBoardDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tbBoardDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tb-boards/{id}")
    public ResponseEntity<TbBoardDTO> getTbBoard(@PathVariable Long id) {
        log.debug("REST request to get TbBoard : {}", id);
        Optional<TbBoardDTO> tbBoardDTO = tbBoardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tbBoardDTO);
    }

    /**
     * {@code DELETE  /tb-boards/:id} : delete the "id" tbBoard.
     *
     * @param id the id of the tbBoardDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tb-boards/{id}")
    public ResponseEntity<Void> deleteTbBoard(@PathVariable Long id) {
        log.debug("REST request to delete TbBoard : {}", id);
        tbBoardService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
