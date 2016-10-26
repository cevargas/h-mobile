package com.tn3.mobile.hermes.models;

import java.io.Serializable;

public class Endereco implements Serializable{

    private Long id;
    private Coletas coleta;
    private String logradouro;
    private int numero;
    private String complemento;
    private String cidade;
    private String estado;
    private String bairro;
    private String setor;

    public Endereco(){}

    public Endereco(Long id, String logradouro, int numero, String complemento, String cidade, String estado,
                    String bairro, String setor, Coletas coleta) {
        this.id = id;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.cidade = cidade;
        this.estado = estado;
        this.bairro = bairro;
        this.setor = setor;
        this.coleta = coleta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public Coletas getColeta() {
        return coleta;
    }

    public void setColeta(Coletas coleta) {
        this.coleta = coleta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Endereco)) return false;

        Endereco endereco = (Endereco) o;

        if (getNumero() != endereco.getNumero()) return false;
        if (!getId().equals(endereco.getId())) return false;
        if (getLogradouro() != null ? !getLogradouro().equals(endereco.getLogradouro()) : endereco.getLogradouro() != null)
            return false;
        if (getComplemento() != null ? !getComplemento().equals(endereco.getComplemento()) : endereco.getComplemento() != null)
            return false;
        if (!getCidade().equals(endereco.getCidade())) return false;
        if (getBairro() != null ? !getBairro().equals(endereco.getBairro()) : endereco.getBairro() != null)
            return false;
        return getSetor() != null ? getSetor().equals(endereco.getSetor()) : endereco.getSetor() == null;

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + (getLogradouro() != null ? getLogradouro().hashCode() : 0);
        result = 31 * result + getNumero();
        result = 31 * result + (getComplemento() != null ? getComplemento().hashCode() : 0);
        result = 31 * result + getCidade().hashCode();
        result = 31 * result + (getBairro() != null ? getBairro().hashCode() : 0);
        result = 31 * result + (getSetor() != null ? getSetor().hashCode() : 0);
        return result;
    }
}
