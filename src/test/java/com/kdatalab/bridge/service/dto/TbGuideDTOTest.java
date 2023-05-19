package com.kdatalab.bridge.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.kdatalab.bridge.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TbGuideDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TbGuideDTO.class);
        TbGuideDTO tbGuideDTO1 = new TbGuideDTO();
        tbGuideDTO1.setId(1L);
        TbGuideDTO tbGuideDTO2 = new TbGuideDTO();
        assertThat(tbGuideDTO1).isNotEqualTo(tbGuideDTO2);
        tbGuideDTO2.setId(tbGuideDTO1.getId());
        assertThat(tbGuideDTO1).isEqualTo(tbGuideDTO2);
        tbGuideDTO2.setId(2L);
        assertThat(tbGuideDTO1).isNotEqualTo(tbGuideDTO2);
        tbGuideDTO1.setId(null);
        assertThat(tbGuideDTO1).isNotEqualTo(tbGuideDTO2);
    }
}
