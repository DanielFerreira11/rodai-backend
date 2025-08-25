// src/main/java/br/edu/ufcg/rodai/dto/DimCountDTO.java
package br.edu.ufcg.rodai.dto;

public class DimCountDTO {
    private String label; // dia da semana ("segunda-feira") OU hora em string ("0","1",...,"23")
    private Long total;

    public DimCountDTO(String label, Long total) {
        this.label = label;
        this.total = total;
    }
    public String getLabel() { return label; }
    public Long getTotal() { return total; }
}
