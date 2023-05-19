package com.kdatalab.bridge.service;

import com.kdatalab.bridge.service.dto.TbBoardViewCntDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.kdatalab.bridge.domain.TbBoardViewCnt}.
 */
public interface TbBoardViewCntService {
    /**
     * Save a tbBoardViewCnt.
     *
     * @param tbBoardViewCntDTO the entity to save.
     * @return the persisted entity.
     */
    TbBoardViewCntDTO save(TbBoardViewCntDTO tbBoardViewCntDTO);

    /**
     * Updates a tbBoardViewCnt.
     *
     * @param tbBoardViewCntDTO the entity to update.
     * @return the persisted entity.
     */
    TbBoardViewCntDTO update(TbBoardViewCntDTO tbBoardViewCntDTO);

    /**
     * Partially updates a tbBoardViewCnt.
     *
     * @param tbBoardViewCntDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TbBoardViewCntDTO> partialUpdate(TbBoardViewCntDTO tbBoardViewCntDTO);

    /**
     * Get all the tbBoardViewCnts.
     *
     * @return the list of entities.
     */
    List<TbBoardViewCntDTO> findAll();

    /**
     * Get the "id" tbBoardViewCnt.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TbBoardViewCntDTO> findOne(Long id);

    /**
     * Delete the "id" tbBoardViewCnt.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
