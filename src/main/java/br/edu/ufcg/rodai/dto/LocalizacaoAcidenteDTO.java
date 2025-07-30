package br.edu.ufcg.rodai.dto;

public class LocalizacaoAcidenteDTO {
    private Double latitude;
    private Double longitude;
    private String uf;

    public LocalizacaoAcidenteDTO(Double latitude, Double longitude, String uf) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.uf = uf;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}