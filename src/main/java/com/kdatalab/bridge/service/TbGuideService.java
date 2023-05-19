package com.kdatalab.bridge.service;

import com.kdatalab.bridge.service.dto.TbGuideDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.kdatalab.bridge.domain.TbGuide}.
 */
public interface TbGuideService {
    /**
     * Save a tbGuide.
     *
     * @param tbGuideDTO the entity to save.
     * @return the persisted entity.
     */
    TbGuideDTO save(TbGuideDTO tbGuideDTO);

    /**
     * Updates a tbGuide.
     *
     * @param tbGuideDTO the entity to update.
     * @return the persisted entity.
     */
    TbGuideDTO update(TbGuideDTO tbGuideDTO);

    /**
     * Partially updates a tbGuide.
     *
     * @param tbGuideDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TbGuideDTO> partialUpdate(TbGuideDTO tbGuideDTO);

    /**
     * Get all the tbGuides.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TbGuideDTO> findAll(Pageable pageable);

    /**
     * Get the "id" tbGuide.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TbGuideDTO> findOne(Long id);

    /**
     * Delete the "id" tbGuide.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
