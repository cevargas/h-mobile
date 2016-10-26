package com.tn3.mobile.hermes.dto;

public class RotaDTO {

    public int id;
    public String desc;
    public CidadeDTO origem;
    public CidadeDTO destino;
    public CidadeDTO[] intermediarias;

    public RotaDTO(int id, String desc, CidadeDTO origem, CidadeDTO destino, CidadeDTO[] intermediarias) {
        this.id = id;
        this.desc = desc;
        this.origem = origem;
        this.destino = destino;
        this.intermediarias = intermediarias;
    }
}
