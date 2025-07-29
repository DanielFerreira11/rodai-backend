package br.edu.ufcg.rodai.model;

import jakarta.persistence.*;

@Entity
@Table(name = "condicoes", schema = "public")
public class CondicoesModel {

    @Id
    @Column(name = "id_acidente")
    private String idAcidente;

    @Column(name = "fase_dia")
    private String faseDia;

    @Column(name = "sentido_via")
    private String sentidoVia;

    @Column(name = "tipo_pista")
    private String tipoPista;

    @Column(name = "tracado_via")
    private String tracadoVia;

    @Column(name = "condicao_meteorologica")
    private String condicaoMeteorologica;

    // Getters e Setters

    public String getIdAcidente() {
        return idAcidente;
    }

    public void setIdAcidente(String idAcidente) {
        this.idAcidente = idAcidente;
    }

    public String getFaseDia() {
        return faseDia;
    }

    public void setFaseDia(String faseDia) {
        this.faseDia = faseDia;
    }

    public String getSentidoVia() {
        return sentidoVia;
    }

    public void setSentidoVia(String sentidoVia) {
        this.sentidoVia = sentidoVia;
    }

    public String getTipoPista() {
        return tipoPista;
    }

    public void setTipoPista(String tipoPista) {
        this.tipoPista = tipoPista;
    }

    public String getTracadoVia() {
        return tracadoVia;
    }

    public void setTracadoVia(String tracadoVia) {
        this.tracadoVia = tracadoVia;
    }

    public String getCondicaoMeteorologica() {
        return condicaoMeteorologica;
    }

    public void setCondicaoMeteorologica(String condicaoMeteorologica) {
        this.condicaoMeteorologica = condicaoMeteorologica;
    }
}
