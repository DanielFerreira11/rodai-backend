package br.edu.ufcg.rodai.model;

import jakarta.persistence.*;

@Entity
@Table(name = "acidente", schema = "public")
public class AcidenteModel {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "data_inversa")
    private String dataInversa;

    @Column(name = "horario")
    private String horario;

    @Column(name = "dia_semana")
    private String diaSemana;

    @Column(name = "uf")
    private String uf;

    @Column(name = "br")
    private String br;

    @Column(name = "km")
    private Double km;

    @Column(name = "municipio")
    private String municipio;

    @Column(name = "tipo_acidente")
    private String tipoAcidente;

    @Column(name = "classificacao_acidente")
    private String classificacaoAcidente;

    @Column(name = "causa_acidente")
    private String causaAcidente;

    @Column(name = "ano")
    private Integer ano;

    @Column(name = "id_original")
    private String idOriginal;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataInversa() {
        return dataInversa;
    }

    public void setDataInversa(String dataInversa) {
        this.dataInversa = dataInversa;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getBr() {
        return br;
    }

    public void setBr(String br) {
        this.br = br;
    }

    public Double getKm() {
        return km;
    }

    public void setKm(Double km) {
        this.km = km;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getTipoAcidente() {
        return tipoAcidente;
    }

    public void setTipoAcidente(String tipoAcidente) {
        this.tipoAcidente = tipoAcidente;
    }

    public String getClassificacaoAcidente() {
        return classificacaoAcidente;
    }

    public void setClassificacaoAcidente(String classificacaoAcidente) {
        this.classificacaoAcidente = classificacaoAcidente;
    }

    public String getCausaAcidente() {
        return causaAcidente;
    }

    public void setCausaAcidente(String causaAcidente) {
        this.causaAcidente = causaAcidente;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getIdOriginal() {
        return idOriginal;
    }

    public void setIdOriginal(String idOriginal) {
        this.idOriginal = idOriginal;
    }
}
