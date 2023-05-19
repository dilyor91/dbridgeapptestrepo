package com.kdatalab.bridge.service.mapper;

import com.kdatalab.bridge.domain.TbBoard;
import com.kdatalab.bridge.domain.TbBoardViewCnt;
import com.kdatalab.bridge.service.dto.TbBoardDTO;
import com.kdatalab.bridge.service.dto.TbBoardViewCntDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TbBoardViewCnt} and its DTO {@link TbBoardViewCntDTO}.
 */
@Mapper(componentModel = "spring")
public interface TbBoardViewCntMapper extends EntityMapper<TbBoardViewCntDTO, TbBoardViewCnt> {
    @Mapping(target = "bdSeq", source = "bdSeq", qualifiedByName = "tbBoardId")
    TbBoardViewCntDTO toDto(TbBoardViewCnt s);

    @Named("tbBoardId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TbBoardDTO toDtoTbBoardId(TbBoard tbBoard);
}
