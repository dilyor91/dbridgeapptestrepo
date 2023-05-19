package com.kdatalab.bridge.web.rest;

import com.kdatalab.bridge.repository.TbAttachmentRepository;
import com.kdatalab.bridge.service.TbAttachmentService;
import com.kdatalab.bridge.service.dto.TbAttachmentDTO;
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
 * REST controller for managing {@link com.kdatalab.bridge.domain.TbAttachment}.
 */
@RestController
@RequestMapping("/api")
public class TbAttachmentResource {

    private final Logger log = LoggerFactory.getLogger(TbAttachmentResource.class);

    private static final String ENTITY_NAME = "tbAttachment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TbAttachmentService tbAttachmentService;

    private final TbAttachmentRepository tbAttachmentRepository;

    public TbAttachmentResource(TbAttachmentService tbAttachmentService, TbAttachmentRepository tbAttachmentRepository) {
        this.tbAttachmentService = tbAttachmentService;
        this.tbAttachmentRepository = tbAttachmentRepository;
    }

    /**
     * {@code POST  /tb-attachments} : Create a new tbAttachment.
     *
     * @param tbAttachmentDTO the tbAttachmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tbAttachmentDTO, or with status {@code 400 (Bad Request)} if the tbAttachment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tb-attachments")
    public ResponseEntity<TbAttachmentDTO> createTbAttachment(@RequestBody TbAttachmentDTO tbAttachmentDTO) throws URISyntaxException {
        log.debug("REST request to save TbAttachment : {}", tbAttachmentDTO);
        if (tbAttachmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new tbAttachment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TbAttachmentDTO result = tbAttachmentService.save(tbAttachmentDTO);
        return ResponseEntity
            .created(new URI("/api/tb-attachments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tb-attachments/:id} : Updates an existing tbAttachment.
     *
     * @param id the id of the tbAttachmentDTO to save.
     * @param tbAttachmentDTO the tbAttachmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tbAttachmentDTO,
     * or with status {@code 400 (Bad Request)} if the tbAttachmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tbAttachmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tb-attachments/{id}")
    public ResponseEntity<TbAttachmentDTO> updateTbAttachment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TbAttachmentDTO tbAttachmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TbAttachment : {}, {}", id, tbAttachmentDTO);
        if (tbAttachmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tbAttachmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tbAttachmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TbAttachmentDTO result = tbAttachmentService.update(tbAttachmentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tbAttachmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /tb-attachments/:id} : Partial updates given fields of an existing tbAttachment, field will ignore if it is null
     *
     * @param id the id of the tbAttachmentDTO to save.
     * @param tbAttachmentDTO the tbAttachmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tbAttachmentDTO,
     * or with status {@code 400 (Bad Request)} if the tbAttachmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tbAttachmentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tbAttachmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/tb-attachments/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TbAttachmentDTO> partialUpdateTbAttachment(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TbAttachmentDTO tbAttachmentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TbAttachment partially : {}, {}", id, tbAttachmentDTO);
        if (tbAttachmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tbAttachmentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tbAttachmentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TbAttachmentDTO> result = tbAttachmentService.partialUpdate(tbAttachmentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tbAttachmentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tb-attachments} : get all the tbAttachments.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tbAttachments in body.
     */
    @GetMapping("/tb-attachments")
    public List<TbAttachmentDTO> getAllTbAttachments() {
        log.debug("REST request to get all TbAttachments");
        return tbAttachmentService.findAll();
    }

    /**
     * {@code GET  /tb-attachments/:id} : get the "id" tbAttachment.
     *
     * @param id the id of the tbAttachmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tbAttachmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tb-attachments/{id}")
    public ResponseEntity<TbAttachmentDTO> getTbAttachment(@PathVariable Long id) {
        log.debug("REST request to get TbAttachment : {}", id);
        Optional<TbAttachmentDTO> tbAttachmentDTO = tbAttachmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tbAttachmentDTO);
    }

    /**
     * {@code DELETE  /tb-attachments/:id} : delete the "id" tbAttachment.
     *
     * @param id the id of the tbAttachmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tb-attachments/{id}")
    public ResponseEntity<Void> deleteTbAttachment(@PathVariable Long id) {
        log.debug("REST request to delete TbAttachment : {}", id);
        tbAttachmentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
