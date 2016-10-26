package com.tn3.mobile.hermes.dto;

import com.tn3.mobile.hermes.models.Coletas;

import java.util.List;

public class PostJsonDTO {

    List<Coletas> content;
    private String imei;
    private String token;

    public PostJsonDTO() {
    }

    public List<Coletas> getColetasList() {
        return content;
    }

    public void setColetasList(List<Coletas> content) {
        this.content = content;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
