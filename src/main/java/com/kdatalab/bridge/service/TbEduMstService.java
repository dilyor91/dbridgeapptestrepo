package com.kdatalab.bridge.service;

import com.kdatalab.bridge.service.dto.TbEduMstDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.kdatalab.bridge.domain.TbEduMst}.
 */
public interface TbEduMstService {
    /**
     * Save a tbEduMst.
     *
     * @param tbEduMstDTO the entity to save.
     * @return the persisted entity.
     */
    TbEduMstDTO save(TbEduMstDTO tbEduMstDTO);

    /**
     * Updates a tbEduMst.
     *
     * @param tbEduMstDTO the entity to update.
     * @return the persisted entity.
     */
    TbEduMstDTO update(TbEduMstDTO tbEduMstDTO);

    /**
     * Partially updates a tbEduMst.
     *
     * @param tbEduMstDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TbEduMstDTO> partialUpdate(TbEduMstDTO tbEduMstDTO);

    /**
     * Get all the tbEduMsts.
     *
     * @return the list of entities.
     */
    List<TbEduMstDTO> findAll();

    /**
     * Get the "id" tbEduMst.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TbEduMstDTO> findOne(Long id);

    /**
     * Delete the "id" tbEduMst.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
