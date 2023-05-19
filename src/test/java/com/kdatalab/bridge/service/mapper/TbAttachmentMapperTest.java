package com.kdatalab.bridge.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TbAttachmentMapperTest {

    private TbAttachmentMapper tbAttachmentMapper;

    @BeforeEach
    public void setUp() {
        tbAttachmentMapper = new TbAttachmentMapperImpl();
    }
}
