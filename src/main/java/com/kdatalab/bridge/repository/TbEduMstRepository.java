package com.kdatalab.bridge.repository;

import com.kdatalab.bridge.domain.TbEduMst;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TbEduMst entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TbEduMstRepository extends JpaRepository<TbEduMst, Long> {}
