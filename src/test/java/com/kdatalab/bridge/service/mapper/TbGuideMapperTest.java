package com.kdatalab.bridge.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TbGuideMapperTest {

    private TbGuideMapper tbGuideMapper;

    @BeforeEach
    public void setUp() {
        tbGuideMapper = new TbGuideMapperImpl();
    }
}
