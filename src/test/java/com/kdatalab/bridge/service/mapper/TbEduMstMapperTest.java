package com.kdatalab.bridge.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TbEduMstMapperTest {

    private TbEduMstMapper tbEduMstMapper;

    @BeforeEach
    public void setUp() {
        tbEduMstMapper = new TbEduMstMapperImpl();
    }
}
