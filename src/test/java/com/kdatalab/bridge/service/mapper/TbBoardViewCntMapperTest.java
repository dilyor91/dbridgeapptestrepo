package com.kdatalab.bridge.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TbBoardViewCntMapperTest {

    private TbBoardViewCntMapper tbBoardViewCntMapper;

    @BeforeEach
    public void setUp() {
        tbBoardViewCntMapper = new TbBoardViewCntMapperImpl();
    }
}
