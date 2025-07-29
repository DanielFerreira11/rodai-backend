package br.edu.ufcg.rodai.model;

import jakarta.persistence.*;

@Entity
@Table(name = "localizacao", schema = "public")
public class LocalizacaoModel {

    @Id
    @Column(name = "id_acidente")
    private String idAcidente;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "regional")
    private String regional;

    @Column(name = "delegacia")
    private String delegacia;

    @Column(name = "uop")
    private String uop;

    public String getIdAcidente() {
        return idAcidente;
    }

    public void setIdAcidente(String idAcidente) {
        this.idAcidente = idAcidente;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getRegional() {
        return regional;
    }

    public void setRegional(String regional) {
        this.regional = regional;
    }

    public String getDelegacia() {
        return delegacia;
    }

    public void setDelegacia(String delegacia) {
        this.delegacia = delegacia;
    }

    public String getUop() {
        return uop;
    }

    public void setUop(String uop) {
        this.uop = uop;
    }
}


