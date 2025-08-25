// src/main/java/br/edu/ufcg/rodai/dto/DiaAggDTO.java
package br.edu.ufcg.rodai.dto;

public class DiaAggDTO {
    private int dow;          // 0..6 (Dom..Sáb)
    private String label;     // "Dom".."Sáb"
    private long acidentes;
    private long mortos;
    private long value;       // acidentes ou mortos (conforme "metrica")

    public DiaAggDTO() {}

    public DiaAggDTO(int dow, String label, long acidentes, long mortos, long value) {
        this.dow = dow;
        this.label = label;
        this.acidentes = acidentes;
        this.mortos = mortos;
        this.value = value;
    }

    public int getDow() { return dow; }
    public void setDow(int dow) { this.dow = dow; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public long getAcidentes() { return acidentes; }
    public void setAcidentes(long acidentes) { this.acidentes = acidentes; }

    public long getMortos() { return mortos; }
    public void setMortos(long mortos) { this.mortos = mortos; }

    public long getValue() { return value; }
    public void setValue(long value) { this.value = value; }
}
