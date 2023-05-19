package com.kdatalab.bridge.service.mapper;

import com.kdatalab.bridge.domain.TbEduMst;
import com.kdatalab.bridge.service.dto.TbEduMstDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TbEduMst} and its DTO {@link TbEduMstDTO}.
 */
@Mapper(componentModel = "spring")
public interface TbEduMstMapper extends EntityMapper<TbEduMstDTO, TbEduMst> {}
