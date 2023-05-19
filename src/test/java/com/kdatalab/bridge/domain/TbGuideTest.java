package com.kdatalab.bridge.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kdatalab.bridge.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TbGuideTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TbGuide.class);
        TbGuide tbGuide1 = new TbGuide();
        tbGuide1.setId(1L);
        TbGuide tbGuide2 = new TbGuide();
        tbGuide2.setId(tbGuide1.getId());
        assertThat(tbGuide1).isEqualTo(tbGuide2);
        tbGuide2.setId(2L);
        assertThat(tbGuide1).isNotEqualTo(tbGuide2);
        tbGuide1.setId(null);
        assertThat(tbGuide1).isNotEqualTo(tbGuide2);
    }
}
