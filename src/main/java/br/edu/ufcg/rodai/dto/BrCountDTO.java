package br.edu.ufcg.rodai.dto;

public class BrCountDTO {
    private final String br;
    private final Long acidentes;
    private final Long mortos;

    public BrCountDTO(String br, Long acidentes, Long mortos) {
        this.br = br;
        this.acidentes = acidentes;
        this.mortos = mortos;
    }
    public String getBr() { return br; }
    public Long getAcidentes() { return acidentes; }
    public Long getMortos() { return mortos; }
}
