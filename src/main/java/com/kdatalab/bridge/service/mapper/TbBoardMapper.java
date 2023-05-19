package com.kdatalab.bridge.service.mapper;

import com.kdatalab.bridge.domain.TbBoard;
import com.kdatalab.bridge.service.dto.TbBoardDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TbBoard} and its DTO {@link TbBoardDTO}.
 */
@Mapper(componentModel = "spring")
public interface TbBoardMapper extends EntityMapper<TbBoardDTO, TbBoard> {}
