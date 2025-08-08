package br.edu.ufcg.rodai.dto;

public class MortesPorUFDTO {
    private String uf;
    private Long totalMortos;

    public MortesPorUFDTO(String uf, Long totalMortos) {
        this.uf = uf;
        this.totalMortos = totalMortos;
    }

    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }
    public Long getTotalMortos() { return totalMortos; }
    public void setTotalMortos(Long totalMortos) { this.totalMortos = totalMortos; }
}
