package com.tn3.mobile.hermes.models;

import java.io.Serializable;

public class Cidade implements Serializable {

    private Long id;
    private Rota rota;
    private String nome;
    private String uf;

    public Cidade(){}

    public Cidade(Long id, String nome, String uf, Rota rota) {
        this.id = id;
        this.nome = nome;
        this.uf = uf;
        this.rota = rota;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Rota getRota() {
        return rota;
    }

    public void setRota(Rota rota) {
        this.rota = rota;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cidade)) return false;

        Cidade cidade = (Cidade) o;

        if (getId() != null ? !getId().equals(cidade.getId()) : cidade.getId() != null)
            return false;
        if (getRota() != null ? !getRota().equals(cidade.getRota()) : cidade.getRota() != null)
            return false;
        if (getNome() != null ? !getNome().equals(cidade.getNome()) : cidade.getNome() != null)
            return false;
        return getUf() != null ? getUf().equals(cidade.getUf()) : cidade.getUf() == null;

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getRota() != null ? getRota().hashCode() : 0);
        result = 31 * result + (getNome() != null ? getNome().hashCode() : 0);
        result = 31 * result + (getUf() != null ? getUf().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Cidade{" +
                "id=" + id +
                ", rota=" + rota +
                ", nome='" + nome + '\'' +
                ", uf='" + uf + '\'' +
                '}';
    }
}
