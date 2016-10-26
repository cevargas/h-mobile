package com.tn3.mobile.hermes.models;

import java.io.Serializable;
import java.util.List;

public class Rota implements Serializable {

    private Long id;
    private String descricao;
    private Manifestos manifestos;
    private List<Cidade> viagem;
    private Cidade origem;
    private Cidade destino;

    public Rota(){}

    public Rota(Long id, String descricao, List<Cidade> viagem, Cidade origem, Cidade destino, Manifestos manifestos) {
        this.id = id;
        this.descricao = descricao;
        this.viagem = viagem;
        this.origem = origem;
        this.destino = destino;
        this.manifestos = manifestos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<Cidade> getViagem() {
        return viagem;
    }

    public void setViagem(List<Cidade> viagem) {
        this.viagem = viagem;
    }

    public Cidade getOrigem() {
        return origem;
    }

    public void setOrigem(Cidade origem) {
        this.origem = origem;
    }

    public Cidade getDestino() {
        return destino;
    }

    public void setDestino(Cidade destino) {
        this.destino = destino;
    }

    public Manifestos getManifestos() {
        return manifestos;
    }

    public void setManifestos(Manifestos manifestos) {
        this.manifestos = manifestos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rota)) return false;

        Rota rota = (Rota) o;

        if (!getId().equals(rota.getId())) return false;
        return getDescricao().equals(rota.getDescricao());

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getDescricao().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Rota{" +
                "id=" + id +
                ", descricao='" + descricao +
                '}';
    }
}
