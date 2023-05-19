package com.kdatalab.bridge.repository;

import com.kdatalab.bridge.domain.TbAttachment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TbAttachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TbAttachmentRepository extends JpaRepository<TbAttachment, Long> {}
