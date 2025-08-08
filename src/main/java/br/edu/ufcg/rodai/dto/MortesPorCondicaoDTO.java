package br.edu.ufcg.rodai.dto;

public class MortesPorCondicaoDTO {
    private String condicaoMetereologica;
    private Long totalMortos;

    public MortesPorCondicaoDTO(String condicaoMetereologica, Long totalMortos) {
        this.condicaoMetereologica = condicaoMetereologica;
        this.totalMortos = totalMortos;
    }

    public String getCondicaoMetereologica() { return condicaoMetereologica; }
    public void setCondicaoMetereologica(String condicaoMetereologica) { this.condicaoMetereologica = condicaoMetereologica; }
    public Long getTotalMortos() { return totalMortos; }
    public void setTotalMortos(Long totalMortos) { this.totalMortos = totalMortos; }
}

