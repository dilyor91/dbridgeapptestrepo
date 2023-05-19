package com.kdatalab.bridge.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kdatalab.bridge.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TbBoardViewCntTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TbBoardViewCnt.class);
        TbBoardViewCnt tbBoardViewCnt1 = new TbBoardViewCnt();
        tbBoardViewCnt1.setId(1L);
        TbBoardViewCnt tbBoardViewCnt2 = new TbBoardViewCnt();
        tbBoardViewCnt2.setId(tbBoardViewCnt1.getId());
        assertThat(tbBoardViewCnt1).isEqualTo(tbBoardViewCnt2);
        tbBoardViewCnt2.setId(2L);
        assertThat(tbBoardViewCnt1).isNotEqualTo(tbBoardViewCnt2);
        tbBoardViewCnt1.setId(null);
        assertThat(tbBoardViewCnt1).isNotEqualTo(tbBoardViewCnt2);
    }
}
