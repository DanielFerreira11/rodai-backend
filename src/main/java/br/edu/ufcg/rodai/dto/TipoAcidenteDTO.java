package br.edu.ufcg.rodai.dto;

public class TipoAcidenteDTO {
    private String tipoAcidente;
    private Long quantidade;

    public TipoAcidenteDTO(String tipoAcidente, Long quantidade) {
        this.tipoAcidente = tipoAcidente;
        this.quantidade = quantidade;
    }

    public String getTipoAcidente() {
        return tipoAcidente;
    }

    public void setTipoAcidente(String tipoAcidente) {
        this.tipoAcidente = tipoAcidente;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }
}
