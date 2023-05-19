package com.kdatalab.bridge.service.mapper;

import com.kdatalab.bridge.domain.TbEduMst;
import com.kdatalab.bridge.domain.TbGuide;
import com.kdatalab.bridge.service.dto.TbEduMstDTO;
import com.kdatalab.bridge.service.dto.TbGuideDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TbGuide} and its DTO {@link TbGuideDTO}.
 */
@Mapper(componentModel = "spring")
public interface TbGuideMapper extends EntityMapper<TbGuideDTO, TbGuide> {
    @Mapping(target = "eduSeq", source = "eduSeq", qualifiedByName = "tbEduMstId")
    TbGuideDTO toDto(TbGuide s);

    @Named("tbEduMstId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TbEduMstDTO toDtoTbEduMstId(TbEduMst tbEduMst);
}
