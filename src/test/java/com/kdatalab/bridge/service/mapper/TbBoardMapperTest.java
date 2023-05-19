package com.kdatalab.bridge.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TbBoardMapperTest {

    private TbBoardMapper tbBoardMapper;

    @BeforeEach
    public void setUp() {
        tbBoardMapper = new TbBoardMapperImpl();
    }
}
