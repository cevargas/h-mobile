package com.tn3.mobile.hermes.dto;

public class ColetaDTO {

    public int id;
    public String setor;
    public String status;
    public String motivo;
    public String horafixa;
    public String horalimite;
    public String qtdnfe;
    public String valornfe;
    public String peso;
    public String qtdvolumes;
    public String chaveNfeList[];

    public EmissorDestinatarioDTO emissor;
    public EmissorDestinatarioDTO destinatario;

    public ColetaDTO(int id, String setor, String status, String motivo, String horafixa, String horalimite, String qtdnfe, String valornfe, String peso, String qtdvolumes, String[] chaveNfeList, EmissorDestinatarioDTO emissor, EmissorDestinatarioDTO destinatario) {
        this.id = id;
        this.setor = setor;
        this.status = status;
        this.motivo = motivo;
        this.horafixa = horafixa;
        this.horalimite = horalimite;
        this.qtdnfe = qtdnfe;
        this.valornfe = valornfe;
        this.peso = peso;
        this.qtdvolumes = qtdvolumes;
        this.chaveNfeList = chaveNfeList;
        this.emissor = emissor;
        this.destinatario = destinatario;
    }
}
