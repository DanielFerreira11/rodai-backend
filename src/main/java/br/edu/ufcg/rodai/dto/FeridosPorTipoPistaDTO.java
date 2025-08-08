// src/main/java/br/edu/ufcg/rodai/dto/FeridosPorTipoPistaDTO.java
package br.edu.ufcg.rodai.dto;

public class FeridosPorTipoPistaDTO {
    private String tipoPista;
    private Long quantidade;

    public FeridosPorTipoPistaDTO(String tipoPista, Long quantidade) {
        this.tipoPista = tipoPista;
        this.quantidade = quantidade;
    }

    public String getTipoPista() {
        return tipoPista;
    }

    public void setTipoPista(String tipoPista) {
        this.tipoPista = tipoPista;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }
}
