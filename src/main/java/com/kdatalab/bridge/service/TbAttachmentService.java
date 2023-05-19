package com.kdatalab.bridge.service;

import com.kdatalab.bridge.service.dto.TbAttachmentDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.kdatalab.bridge.domain.TbAttachment}.
 */
public interface TbAttachmentService {
    /**
     * Save a tbAttachment.
     *
     * @param tbAttachmentDTO the entity to save.
     * @return the persisted entity.
     */
    TbAttachmentDTO save(TbAttachmentDTO tbAttachmentDTO);

    /**
     * Updates a tbAttachment.
     *
     * @param tbAttachmentDTO the entity to update.
     * @return the persisted entity.
     */
    TbAttachmentDTO update(TbAttachmentDTO tbAttachmentDTO);

    /**
     * Partially updates a tbAttachment.
     *
     * @param tbAttachmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TbAttachmentDTO> partialUpdate(TbAttachmentDTO tbAttachmentDTO);

    /**
     * Get all the tbAttachments.
     *
     * @return the list of entities.
     */
    List<TbAttachmentDTO> findAll();

    /**
     * Get the "id" tbAttachment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TbAttachmentDTO> findOne(Long id);

    /**
     * Delete the "id" tbAttachment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
