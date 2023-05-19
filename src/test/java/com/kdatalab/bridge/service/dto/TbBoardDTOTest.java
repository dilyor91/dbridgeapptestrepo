package com.kdatalab.bridge.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.kdatalab.bridge.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TbBoardDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TbBoardDTO.class);
        TbBoardDTO tbBoardDTO1 = new TbBoardDTO();
        tbBoardDTO1.setId(1L);
        TbBoardDTO tbBoardDTO2 = new TbBoardDTO();
        assertThat(tbBoardDTO1).isNotEqualTo(tbBoardDTO2);
        tbBoardDTO2.setId(tbBoardDTO1.getId());
        assertThat(tbBoardDTO1).isEqualTo(tbBoardDTO2);
        tbBoardDTO2.setId(2L);
        assertThat(tbBoardDTO1).isNotEqualTo(tbBoardDTO2);
        tbBoardDTO1.setId(null);
        assertThat(tbBoardDTO1).isNotEqualTo(tbBoardDTO2);
    }
}
