package com.kdatalab.bridge.repository;

import com.kdatalab.bridge.domain.TbBoardViewCnt;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TbBoardViewCnt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TbBoardViewCntRepository extends JpaRepository<TbBoardViewCnt, Long> {}
