package com.tn3.mobile.hermes.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Coletas implements Serializable {

    private Long id;
    private String nomeCliente;
    private String cnpjCpfCliente;
    private Endereco endereco;
    private String horaFixa;
    private String horaLimite;
    private Double valorTotal;
    private Double totalCarga;
    private String cubagem;
    private int volumes;
    private int qtdNotas;
    private String status;
    private String statusApp;
    private String motivoCancelamento;
    private int motivoTipoCancelamento;
    private List<String> chavesNfe;
    private byte[] fotoCancelamentoColeta;
    private boolean sincronizado;
    private Date dhsincronizado;

    public Coletas(){}

    public Coletas(Long id, String nomeCliente, String cnpjCpfCliente, Endereco endereco,
                   String horaFixa, String horaLimite, Double valorTotal, Double totalCarga,
                   String cubagem, int volumes, int qtdNotas, String status, String statusApp, List<String> chavesNfe,
                   String motivoCancelamento, int motivoTipoCancelamento, byte[] fotoCancelamentoColeta,
                   boolean sincronizado, Date dhsincronizado) {
        this.id = id;
        this.nomeCliente = nomeCliente;
        this.cnpjCpfCliente = cnpjCpfCliente;
        this.endereco = endereco;
        this.horaFixa = horaFixa;
        this.horaLimite = horaLimite;
        this.valorTotal = valorTotal;
        this.totalCarga = totalCarga;
        this.cubagem = cubagem;
        this.volumes = volumes;
        this.qtdNotas = qtdNotas;
        this.status = status;
        this.statusApp = statusApp;
        this.chavesNfe = chavesNfe;
        this.motivoCancelamento = motivoCancelamento;
        this.motivoTipoCancelamento = motivoTipoCancelamento;
        this.fotoCancelamentoColeta = fotoCancelamentoColeta;
        this.sincronizado = sincronizado;
        this.dhsincronizado = dhsincronizado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getCnpjCpfCliente() {
        return cnpjCpfCliente;
    }

    public void setCnpjCpfCliente(String cnpjCpfCliente) {
        this.cnpjCpfCliente = cnpjCpfCliente;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getHoraFixa() {
        return horaFixa;
    }

    public void setHoraFixa(String horaFixa) {
        this.horaFixa = horaFixa;
    }

    public String getHoraLimite() {
        return horaLimite;
    }

    public void setHoraLimite(String horaLimite) {
        this.horaLimite = horaLimite;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Double getTotalCarga() {
        return totalCarga;
    }

    public void setTotalCarga(Double totalCarga) {
        this.totalCarga = totalCarga;
    }

    public String getCubagem() {
        return cubagem;
    }

    public void setCubagem(String cubagem) {
        this.cubagem = cubagem;
    }

    public int getVolumes() {
        return volumes;
    }

    public void setVolumes(int volumes) {
        this.volumes = volumes;
    }

    public int getQtdNotas() {
        return qtdNotas;
    }

    public void setQtdNotas(int qtdNotas) {
        this.qtdNotas = qtdNotas;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusApp() {
        return statusApp;
    }

    public void setStatusApp(String statusApp) {
        this.statusApp = statusApp;
    }

    public List<String> getChavesNfe() {
        return chavesNfe;
    }

    public void setChavesNfe(List<String> chavesNfe) {
        this.chavesNfe = chavesNfe;
    }

    public String getMotivoCancelamento() {
        return motivoCancelamento;
    }

    public void setMotivoCancelamento(String motivoCancelamento) {
        this.motivoCancelamento = motivoCancelamento;
    }

    public int getMotivoTipoCancelamento() {
        return motivoTipoCancelamento;
    }

    public void setMotivoTipoCancelamento(int motivoTipoCancelamento) {
        this.motivoTipoCancelamento = motivoTipoCancelamento;
    }

    public byte[] getFotoCancelamentoColeta() {
        return fotoCancelamentoColeta;
    }

    public void setFotoCancelamentoColeta(byte[] fotoCancelamentoColeta) {
        this.fotoCancelamentoColeta = fotoCancelamentoColeta;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coletas)) return false;

        Coletas coletas = (Coletas) o;

        if (volumes != coletas.volumes) return false;
        if (qtdNotas != coletas.qtdNotas) return false;
        if (!id.equals(coletas.id)) return false;
        if (!nomeCliente.equals(coletas.nomeCliente)) return false;
        if (!cnpjCpfCliente.equals(coletas.cnpjCpfCliente)) return false;
        if (!endereco.equals(coletas.endereco)) return false;
        if (horaFixa != null ? !horaFixa.equals(coletas.horaFixa) : coletas.horaFixa != null)
            return false;
        if (horaLimite != null ? !horaLimite.equals(coletas.horaLimite) : coletas.horaLimite != null)
            return false;
        if (valorTotal != null ? !valorTotal.equals(coletas.valorTotal) : coletas.valorTotal != null)
            return false;
        if (totalCarga != null ? !totalCarga.equals(coletas.totalCarga) : coletas.totalCarga != null)
            return false;
        if (cubagem != null ? !cubagem.equals(coletas.cubagem) : coletas.cubagem != null)
            return false;
        return status != null ? status.equals(coletas.status) : coletas.status == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + nomeCliente.hashCode();
        result = 31 * result + cnpjCpfCliente.hashCode();
        result = 31 * result + endereco.hashCode();
        result = 31 * result + (horaFixa != null ? horaFixa.hashCode() : 0);
        result = 31 * result + (horaLimite != null ? horaLimite.hashCode() : 0);
        result = 31 * result + (valorTotal != null ? valorTotal.hashCode() : 0);
        result = 31 * result + (totalCarga != null ? totalCarga.hashCode() : 0);
        result = 31 * result + (cubagem != null ? cubagem.hashCode() : 0);
        result = 31 * result + volumes;
        result = 31 * result + qtdNotas;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}