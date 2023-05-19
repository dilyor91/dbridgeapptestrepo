package com.kdatalab.bridge.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.kdatalab.bridge.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TbBoardViewCntDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TbBoardViewCntDTO.class);
        TbBoardViewCntDTO tbBoardViewCntDTO1 = new TbBoardViewCntDTO();
        tbBoardViewCntDTO1.setId(1L);
        TbBoardViewCntDTO tbBoardViewCntDTO2 = new TbBoardViewCntDTO();
        assertThat(tbBoardViewCntDTO1).isNotEqualTo(tbBoardViewCntDTO2);
        tbBoardViewCntDTO2.setId(tbBoardViewCntDTO1.getId());
        assertThat(tbBoardViewCntDTO1).isEqualTo(tbBoardViewCntDTO2);
        tbBoardViewCntDTO2.setId(2L);
        assertThat(tbBoardViewCntDTO1).isNotEqualTo(tbBoardViewCntDTO2);
        tbBoardViewCntDTO1.setId(null);
        assertThat(tbBoardViewCntDTO1).isNotEqualTo(tbBoardViewCntDTO2);
    }
}
