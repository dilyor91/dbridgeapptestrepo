package com.kdatalab.bridge.repository;

import com.kdatalab.bridge.domain.TbGuide;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TbGuide entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TbGuideRepository extends JpaRepository<TbGuide, Long> {}
