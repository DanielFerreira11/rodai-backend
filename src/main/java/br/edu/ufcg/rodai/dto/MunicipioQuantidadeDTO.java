package br.edu.ufcg.rodai.dto;

public class MunicipioQuantidadeDTO {
    private final String municipio;
    private final Long total;

    public MunicipioQuantidadeDTO(String municipio, Long total) {
        this.municipio = municipio;
        this.total = total;
    }

    public String getMunicipio() {
        return municipio;
    }

    public Long getTotal() {
        return total;
    }
}
