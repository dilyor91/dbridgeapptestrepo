package com.kdatalab.bridge.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kdatalab.bridge.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TbEduMstTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TbEduMst.class);
        TbEduMst tbEduMst1 = new TbEduMst();
        tbEduMst1.setId(1L);
        TbEduMst tbEduMst2 = new TbEduMst();
        tbEduMst2.setId(tbEduMst1.getId());
        assertThat(tbEduMst1).isEqualTo(tbEduMst2);
        tbEduMst2.setId(2L);
        assertThat(tbEduMst1).isNotEqualTo(tbEduMst2);
        tbEduMst1.setId(null);
        assertThat(tbEduMst1).isNotEqualTo(tbEduMst2);
    }
}
