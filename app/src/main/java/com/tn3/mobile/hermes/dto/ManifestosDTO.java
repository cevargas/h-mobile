package com.tn3.mobile.hermes.dto;
public class ManifestosDTO {

    public int id;
    public String data;
    public String status;
    public String motivo;
    public RotaDTO rota;
    public String tipo;
    public ColetaDTO[] coletaList;

    public ManifestosDTO(int id, String data, String status, String motivo, RotaDTO rota, String tipo, ColetaDTO[] coletaList) {
        this.id = id;
        this.data = data;
        this.status = status;
        this.motivo = motivo;
        this.rota = rota;
        this.tipo = tipo;
        this.coletaList = coletaList;
    }
}
