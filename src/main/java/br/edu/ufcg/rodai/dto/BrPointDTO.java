// src/main/java/br/edu/ufcg/rodai/dto/BrPointDTO.java
package br.edu.ufcg.rodai.dto;

public class BrPointDTO {
    private double lat;
    private double lon;
    private String data; // yyyy-MM-dd

    // Campos “extras” (mantidos para compatibilidade se precisar em outro lugar)
    private String br;
    private Integer km;
    private String uf;
    private Integer ano;
    private String municipio;
    private String tipoAcidente;

    public BrPointDTO() {}

    // Construtor curto usado no /api/brs/pontos
    public BrPointDTO(double lat, double lon, String data) {
        this.lat = lat;
        this.lon = lon;
        this.data = data;
    }

    // Construtor completo (se algum endpoint antigo exigir)
    public BrPointDTO(double lat, double lon, String data,
                      String br, Integer km, String uf, Integer ano,
                      String municipio, String tipoAcidente) {
        this.lat = lat;
        this.lon = lon;
        this.data = data;
        this.br = br;
        this.km = km;
        this.uf = uf;
        this.ano = ano;
        this.municipio = municipio;
        this.tipoAcidente = tipoAcidente;
    }

    // Getters e Setters
    public double getLat() { return lat; }
    public void setLat(double lat) { this.lat = lat; }

    public double getLon() { return lon; }
    public void setLon(double lon) { this.lon = lon; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public String getBr() { return br; }
    public void setBr(String br) { this.br = br; }

    public Integer getKm() { return km; }
    public void setKm(Integer km) { this.km = km; }

    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }

    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }

    public String getTipoAcidente() { return tipoAcidente; }
    public void setTipoAcidente(String tipoAcidente) { this.tipoAcidente = tipoAcidente; }
}
