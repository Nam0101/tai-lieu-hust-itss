package com.hust.itss.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.hust.itss.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UrlDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UrlDTO.class);
        UrlDTO urlDTO1 = new UrlDTO();
        urlDTO1.setId(1L);
        UrlDTO urlDTO2 = new UrlDTO();
        assertThat(urlDTO1).isNotEqualTo(urlDTO2);
        urlDTO2.setId(urlDTO1.getId());
        assertThat(urlDTO1).isEqualTo(urlDTO2);
        urlDTO2.setId(2L);
        assertThat(urlDTO1).isNotEqualTo(urlDTO2);
        urlDTO1.setId(null);
        assertThat(urlDTO1).isNotEqualTo(urlDTO2);
    }
}
