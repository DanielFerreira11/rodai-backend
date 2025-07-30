package br.edu.ufcg.rodai.dto;

public class VitimasFataisPorAnoDTO {
    private Integer ano;
    private Long quantidade;

    public VitimasFataisPorAnoDTO(Integer ano, Long quantidade) {
        this.ano = ano;
        this.quantidade = quantidade;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }
}