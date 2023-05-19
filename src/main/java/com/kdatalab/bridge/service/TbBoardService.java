package com.kdatalab.bridge.service;

import com.kdatalab.bridge.service.dto.TbBoardDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.kdatalab.bridge.domain.TbBoard}.
 */
public interface TbBoardService {
    /**
     * Save a tbBoard.
     *
     * @param tbBoardDTO the entity to save.
     * @return the persisted entity.
     */
    TbBoardDTO save(TbBoardDTO tbBoardDTO);

    /**
     * Updates a tbBoard.
     *
     * @param tbBoardDTO the entity to update.
     * @return the persisted entity.
     */
    TbBoardDTO update(TbBoardDTO tbBoardDTO);

    /**
     * Partially updates a tbBoard.
     *
     * @param tbBoardDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TbBoardDTO> partialUpdate(TbBoardDTO tbBoardDTO);

    /**
     * Get all the tbBoards.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TbBoardDTO> findAll(Pageable pageable);

    /**
     * Get the "id" tbBoard.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TbBoardDTO> findOne(Long id);

    /**
     * Delete the "id" tbBoard.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
