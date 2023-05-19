package com.kdatalab.bridge.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kdatalab.bridge.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TbBoardTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TbBoard.class);
        TbBoard tbBoard1 = new TbBoard();
        tbBoard1.setId(1L);
        TbBoard tbBoard2 = new TbBoard();
        tbBoard2.setId(tbBoard1.getId());
        assertThat(tbBoard1).isEqualTo(tbBoard2);
        tbBoard2.setId(2L);
        assertThat(tbBoard1).isNotEqualTo(tbBoard2);
        tbBoard1.setId(null);
        assertThat(tbBoard1).isNotEqualTo(tbBoard2);
    }
}
