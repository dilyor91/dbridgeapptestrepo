package com.kdatalab.bridge.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.kdatalab.bridge.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TbAttachmentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TbAttachmentDTO.class);
        TbAttachmentDTO tbAttachmentDTO1 = new TbAttachmentDTO();
        tbAttachmentDTO1.setId(1L);
        TbAttachmentDTO tbAttachmentDTO2 = new TbAttachmentDTO();
        assertThat(tbAttachmentDTO1).isNotEqualTo(tbAttachmentDTO2);
        tbAttachmentDTO2.setId(tbAttachmentDTO1.getId());
        assertThat(tbAttachmentDTO1).isEqualTo(tbAttachmentDTO2);
        tbAttachmentDTO2.setId(2L);
        assertThat(tbAttachmentDTO1).isNotEqualTo(tbAttachmentDTO2);
        tbAttachmentDTO1.setId(null);
        assertThat(tbAttachmentDTO1).isNotEqualTo(tbAttachmentDTO2);
    }
}
