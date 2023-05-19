package com.kdatalab.bridge.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kdatalab.bridge.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TbAttachmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TbAttachment.class);
        TbAttachment tbAttachment1 = new TbAttachment();
        tbAttachment1.setId(1L);
        TbAttachment tbAttachment2 = new TbAttachment();
        tbAttachment2.setId(tbAttachment1.getId());
        assertThat(tbAttachment1).isEqualTo(tbAttachment2);
        tbAttachment2.setId(2L);
        assertThat(tbAttachment1).isNotEqualTo(tbAttachment2);
        tbAttachment1.setId(null);
        assertThat(tbAttachment1).isNotEqualTo(tbAttachment2);
    }
}
