package com.memoire.kital.raph.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ClasseMapperTest {

    private ClasseMapper classeMapper;

    @BeforeEach
    public void setUp() {
        classeMapper = new ClasseMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        String id =null;
        assertThat(classeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(classeMapper.fromId(null)).isNull();
    }
}
