package com.memoire.kital.raph.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.memoire.kital.raph.web.rest.TestUtil;

public class ClasseDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClasseDTO.class);
        ClasseDTO classeDTO1 = new ClasseDTO();
        classeDTO1.setId(null);
        ClasseDTO classeDTO2 = new ClasseDTO();
        assertThat(classeDTO1).isNotEqualTo(classeDTO2);
        classeDTO2.setId(classeDTO1.getId());
        assertThat(classeDTO1).isEqualTo(classeDTO2);
        classeDTO2.setId(null);
        assertThat(classeDTO1).isNotEqualTo(classeDTO2);
        classeDTO1.setId(null);
        assertThat(classeDTO1).isNotEqualTo(classeDTO2);
    }
}
