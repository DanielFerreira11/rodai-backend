package br.edu.ufcg.rodai.model;

import jakarta.persistence.*;

@Entity
@Table(name = "estatisticas", schema = "public")
public class EstatisticasModel {

    @Id
    @Column(name = "id_acidente")
    private String idAcidente;

    @Column(name = "feridos_leves")
    private Integer feridosLeves;

    @Column(name = "feridos_graves")
    private Integer feridosGraves;

    @Column(name = "ilesos")
    private Integer ilesos;

    @Column(name = "mortos")
    private Integer mortos;

    @Column(name = "pessoas")
    private Integer pessoas;

    @Column(name = "veiculos")
    private Integer veiculos;

    @Column(name = "ignorados")
    private Integer ignorados;

    public String getIdAcidente() {
        return idAcidente;
    }

    public void setIdAcidente(String idAcidente) {
        this.idAcidente = idAcidente;
    }

    public Integer getFeridosLeves() {
        return feridosLeves;
    }

    public void setFeridosLeves(Integer feridosLeves) {
        this.feridosLeves = feridosLeves;
    }

    public Integer getFeridosGraves() {
        return feridosGraves;
    }

    public void setFeridosGraves(Integer feridosGraves) {
        this.feridosGraves = feridosGraves;
    }

    public Integer getIlesos() {
        return ilesos;
    }

    public void setIlesos(Integer ilesos) {
        this.ilesos = ilesos;
    }

    public Integer getMortos() {
        return mortos;
    }

    public void setMortos(Integer mortos) {
        this.mortos = mortos;
    }

    public Integer getPessoas() {
        return pessoas;
    }

    public void setPessoas(Integer pessoas) {
        this.pessoas = pessoas;
    }

    public Integer getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(Integer veiculos) {
        this.veiculos = veiculos;
    }

    public Integer getIgnorados() {
        return ignorados;
    }

    public void setIgnorados(Integer ignorados) {
        this.ignorados = ignorados;
    }
}

