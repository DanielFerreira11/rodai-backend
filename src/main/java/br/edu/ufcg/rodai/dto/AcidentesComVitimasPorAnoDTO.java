package br.edu.ufcg.rodai.dto;

public class AcidentesComVitimasPorAnoDTO {
    private Integer ano;
    private Long totalVitimas;

    public AcidentesComVitimasPorAnoDTO(Integer ano, Long totalVitimas) {
        this.ano = ano;
        this.totalVitimas = totalVitimas;
    }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }
    public Long getTotalVitimas() { return totalVitimas; }
    public void setTotalVitimas(Long totalVitimas) { this.totalVitimas = totalVitimas; }
}
