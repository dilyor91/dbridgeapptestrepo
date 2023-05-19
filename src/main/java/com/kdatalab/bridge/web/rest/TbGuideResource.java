package com.kdatalab.bridge.web.rest;

import com.kdatalab.bridge.repository.TbGuideRepository;
import com.kdatalab.bridge.service.TbGuideService;
import com.kdatalab.bridge.service.dto.TbGuideDTO;
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
 * REST controller for managing {@link com.kdatalab.bridge.domain.TbGuide}.
 */
@RestController
@RequestMapping("/api")
public class TbGuideResource {

    private final Logger log = LoggerFactory.getLogger(TbGuideResource.class);

    private static final String ENTITY_NAME = "tbGuide";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TbGuideService tbGuideService;

    private final TbGuideRepository tbGuideRepository;

    public TbGuideResource(TbGuideService tbGuideService, TbGuideRepository tbGuideRepository) {
        this.tbGuideService = tbGuideService;
        this.tbGuideRepository = tbGuideRepository;
    }

    /**
     * {@code POST  /tb-guides} : Create a new tbGuide.
     *
     * @param tbGuideDTO the tbGuideDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tbGuideDTO, or with status {@code 400 (Bad Request)} if the tbGuide has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tb-guides")
    public ResponseEntity<TbGuideDTO> createTbGuide(@RequestBody TbGuideDTO tbGuideDTO) throws URISyntaxException {
        log.debug("REST request to save TbGuide : {}", tbGuideDTO);
        if (tbGuideDTO.getId() != null) {
            throw new BadRequestAlertException("A new tbGuide cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TbGuideDTO result = tbGuideService.save(tbGuideDTO);
        return ResponseEntity
            .created(new URI("/api/tb-guides/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tb-guides/:id} : Updates an existing tbGuide.
     *
     * @param id the id of the tbGuideDTO to save.
     * @param tbGuideDTO the tbGuideDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tbGuideDTO,
     * or with status {@code 400 (Bad Request)} if the tbGuideDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tbGuideDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tb-guides/{id}")
    public ResponseEntity<TbGuideDTO> updateTbGuide(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TbGuideDTO tbGuideDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TbGuide : {}, {}", id, tbGuideDTO);
        if (tbGuideDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tbGuideDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tbGuideRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TbGuideDTO result = tbGuideService.update(tbGuideDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tbGuideDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tb-guides/:id} : Partial updates given fields of an existing tbGuide, field will ignore if it is null
     *
     * @param id the id of the tbGuideDTO to save.
     * @param tbGuideDTO the tbGuideDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tbGuideDTO,
     * or with status {@code 400 (Bad Request)} if the tbGuideDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tbGuideDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tbGuideDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tb-guides/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TbGuideDTO> partialUpdateTbGuide(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TbGuideDTO tbGuideDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TbGuide partially : {}, {}", id, tbGuideDTO);
        if (tbGuideDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tbGuideDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tbGuideRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TbGuideDTO> result = tbGuideService.partialUpdate(tbGuideDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tbGuideDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tb-guides} : get all the tbGuides.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tbGuides in body.
     */
    @GetMapping("/tb-guides")
    public ResponseEntity<List<TbGuideDTO>> getAllTbGuides(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TbGuides");
        Page<TbGuideDTO> page = tbGuideService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tb-guides/:id} : get the "id" tbGuide.
     *
     * @param id the id of the tbGuideDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tbGuideDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tb-guides/{id}")
    public ResponseEntity<TbGuideDTO> getTbGuide(@PathVariable Long id) {
        log.debug("REST request to get TbGuide : {}", id);
        Optional<TbGuideDTO> tbGuideDTO = tbGuideService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tbGuideDTO);
    }

    /**
     * {@code DELETE  /tb-guides/:id} : delete the "id" tbGuide.
     *
     * @param id the id of the tbGuideDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tb-guides/{id}")
    public ResponseEntity<Void> deleteTbGuide(@PathVariable Long id) {
        log.debug("REST request to delete TbGuide : {}", id);
        tbGuideService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
