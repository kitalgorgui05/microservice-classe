package com.memoire.kital.raph.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.memoire.kital.raph.web.rest.TestUtil;

public class ClasseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Classe.class);
        Classe classe1 = new Classe();
        classe1.setId(null);
        Classe classe2 = new Classe();
        classe2.setId(classe1.getId());
        assertThat(classe1).isEqualTo(classe2);
        classe2.setId(null);
        assertThat(classe1).isNotEqualTo(classe2);
        classe1.setId(null);
        assertThat(classe1).isNotEqualTo(classe2);
    }
}
