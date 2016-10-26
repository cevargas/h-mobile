package com.tn3.mobile.hermes.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Manifestos implements Serializable {

    private Long id;
    private Date dataManifesto;
    private List<Coletas> coletas;
    private Rota rota;
    private int totalColetados;
    private int totalCancelado;
    private int totalRestantes;
    private String situacaoManifesto;
    private String tipoManifesto;
    private int odometro;
    private boolean sincronizado;
    private Date dhsincronizado;

    public Manifestos() {
    }

    public Manifestos(Long id, Date dataManifesto, List<Coletas> coletas, Rota rota, int totalColetados, int totalCancelado,
                      int totalRestantes, String situacaoManifesto, String tipoManifesto, int odometro, boolean sincronizado, Date dhsincronizado) {
        this.id = id;
        this.dataManifesto = dataManifesto;
        this.coletas = coletas;
        this.rota = rota;
        this.totalColetados = totalColetados;
        this.totalCancelado = totalCancelado;
        this.totalRestantes = totalRestantes;
        this.situacaoManifesto = situacaoManifesto;
        this.odometro = odometro;
        this.sincronizado = sincronizado;
        this.dhsincronizado = dhsincronizado;
        this.tipoManifesto = tipoManifesto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataManifesto() {
        return dataManifesto;
    }

    public void setDataManifesto(Date dataManifesto) {
        this.dataManifesto = dataManifesto;
    }

    public List<Coletas> getColetas() {
        return coletas;
    }

    public void setColetas(List<Coletas> coletas) {
        this.coletas = coletas;
    }

    public Rota getRota() {
        return rota;
    }

    public void setRota(Rota rota) {
        this.rota = rota;
    }

    public int getTotalColetados() {
        return totalColetados;
    }

    public void setTotalColetados(int totalColetados) {
        this.totalColetados = totalColetados;
    }

    public int getTotalCancelado() {
        return totalCancelado;
    }

    public void setTotalCancelado(int totalCancelado) {
        this.totalCancelado = totalCancelado;
    }

    public int getTotalRestantes() {
        return totalRestantes;
    }

    public void setTotalRestantes(int totalRestantes) {
        this.totalRestantes = totalRestantes;
    }

    public String getSituacaoManifesto() {
        return situacaoManifesto;
    }

    public void setSituacaoManifesto(String situacaoManifesto) {
        this.situacaoManifesto = situacaoManifesto;
    }

    public int getOdometro() {
        return odometro;
    }

    public void setOdometro(int odometro) {
        this.odometro = odometro;
    }

    public boolean isSincronizado() {
        return sincronizado;
    }

    public void setSincronizado(boolean sincronizado) {
        this.sincronizado = sincronizado;
    }

    public Date getDhsincronizado() {
        return dhsincronizado;
    }

    public void setDhsincronizado(Date dhsincronizado) {
        this.dhsincronizado = dhsincronizado;
    }

    public String getTipoManifesto() {
        return tipoManifesto;
    }

    public void setTipoManifesto(String tipoManifesto) {
        this.tipoManifesto = tipoManifesto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Manifestos)) return false;

        Manifestos that = (Manifestos) o;

        if (getTotalColetados() != that.getTotalColetados()) return false;
        if (getTotalCancelado() != that.getTotalCancelado()) return false;
        if (getTotalRestantes() != that.getTotalRestantes()) return false;
        if (!getId().equals(that.getId())) return false;
        if (!getDataManifesto().equals(that.getDataManifesto())) return false;
        if (!getColetas().equals(that.getColetas())) return false;
        if (!getRota().equals(that.getRota())) return false;
        return getSituacaoManifesto() != null ? getSituacaoManifesto().equals(that.getSituacaoManifesto()) : that.getSituacaoManifesto() == null;

    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getDataManifesto().hashCode();
        result = 31 * result + getColetas().hashCode();
        result = 31 * result + getRota().hashCode();
        result = 31 * result + getTotalColetados();
        result = 31 * result + getTotalCancelado();
        result = 31 * result + getTotalRestantes();
        result = 31 * result + (getSituacaoManifesto() != null ? getSituacaoManifesto().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Manifestos{" +
                "id=" + id +
                ", dataManifesto=" + dataManifesto +
                '}';
    }
}
