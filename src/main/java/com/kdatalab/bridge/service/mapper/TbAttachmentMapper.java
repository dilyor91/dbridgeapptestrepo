package com.kdatalab.bridge.service.mapper;

import com.kdatalab.bridge.domain.TbAttachment;
import com.kdatalab.bridge.domain.TbBoard;
import com.kdatalab.bridge.domain.TbGuide;
import com.kdatalab.bridge.service.dto.TbAttachmentDTO;
import com.kdatalab.bridge.service.dto.TbBoardDTO;
import com.kdatalab.bridge.service.dto.TbGuideDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TbAttachment} and its DTO {@link TbAttachmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface TbAttachmentMapper extends EntityMapper<TbAttachmentDTO, TbAttachment> {
    @Mapping(target = "bdSeq", source = "bdSeq", qualifiedByName = "tbBoardId")
    @Mapping(target = "gdSeq", source = "gdSeq", qualifiedByName = "tbGuideId")
    TbAttachmentDTO toDto(TbAttachment s);

    @Named("tbBoardId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TbBoardDTO toDtoTbBoardId(TbBoard tbBoard);

    @Named("tbGuideId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TbGuideDTO toDtoTbGuideId(TbGuide tbGuide);
}
