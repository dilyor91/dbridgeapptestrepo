package com.kdatalab.bridge.repository;

import com.kdatalab.bridge.domain.TbBoard;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TbBoard entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TbBoardRepository extends JpaRepository<TbBoard, Long> {}
