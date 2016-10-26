package com.tn3.mobile.hermes.dto;

public class EmissorDestinatarioDTO {

    public int id;
    public String nome;
    public String cnpjCpf;
    public CidadeDTO cidade;
    public String logradouro;
    public String logrCom;
    public String logrNum;
    public String bairro;

    public EmissorDestinatarioDTO(int id, String nome, String cnpjCpf, CidadeDTO cidade, String logradouro, String logrCom, String logrNum, String bairro) {
        this.id = id;
        this.nome = nome;
        this.cnpjCpf = cnpjCpf;
        this.cidade = cidade;
        this.logradouro = logradouro;
        this.logrCom = logrCom;
        this.logrNum = logrNum;
        this.bairro = bairro;
    }
}
