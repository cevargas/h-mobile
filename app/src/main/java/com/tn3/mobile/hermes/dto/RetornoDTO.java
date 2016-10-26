package com.tn3.mobile.hermes.dto;

import java.util.List;

//data transfer objects
public class RetornoDTO {

    public List<ManifestosDTO> manifestos;

    public RetornoDTO() {
    }

    public RetornoDTO(List<ManifestosDTO> manifestos) {
        this.manifestos = manifestos;
    }
}
