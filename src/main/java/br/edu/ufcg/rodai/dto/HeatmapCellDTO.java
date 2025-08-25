// src/main/java/br/edu/ufcg/rodai/dto/HeatmapCellDTO.java
package br.edu.ufcg.rodai.dto;

public class HeatmapCellDTO {
    private String dia; // ex.: "segunda-feira"
    private Integer hora; // 0..23
    private Long total;

    public HeatmapCellDTO(String dia, Integer hora, Long total) {
        this.dia = dia;
        this.hora = hora;
        this.total = total;
    }
    public String getDia() { return dia; }
    public Integer getHora() { return hora; }
    public Long getTotal() { return total; }
}
