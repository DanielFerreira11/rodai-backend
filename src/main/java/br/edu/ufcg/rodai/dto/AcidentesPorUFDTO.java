package br.edu.ufcg.rodai.dto;

public class AcidentesPorUFDTO {
    private String uf;
    private Long quantidade;

    public AcidentesPorUFDTO(String uf, Long quantidade) {
        this.uf = uf;
        this.quantidade = quantidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }
}
