package br.edu.ufcg.rodai.dto;

public interface LabelTotalDTO {
    String getLabel();   // para horas, o Spring converte int->string; se preferir int, troque o tipo
    Long getTotal();
}