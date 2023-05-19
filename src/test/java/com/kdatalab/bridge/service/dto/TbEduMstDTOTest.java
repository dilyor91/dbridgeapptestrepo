package com.kdatalab.bridge.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.kdatalab.bridge.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TbEduMstDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TbEduMstDTO.class);
        TbEduMstDTO tbEduMstDTO1 = new TbEduMstDTO();
        tbEduMstDTO1.setId(1L);
        TbEduMstDTO tbEduMstDTO2 = new TbEduMstDTO();
        assertThat(tbEduMstDTO1).isNotEqualTo(tbEduMstDTO2);
        tbEduMstDTO2.setId(tbEduMstDTO1.getId());
        assertThat(tbEduMstDTO1).isEqualTo(tbEduMstDTO2);
        tbEduMstDTO2.setId(2L);
        assertThat(tbEduMstDTO1).isNotEqualTo(tbEduMstDTO2);
        tbEduMstDTO1.setId(null);
        assertThat(tbEduMstDTO1).isNotEqualTo(tbEduMstDTO2);
    }
}
