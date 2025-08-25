// src/main/java/br/edu/ufcg/rodai/dto/HoraAggDTO.java
package br.edu.ufcg.rodai.dto;

public class HoraAggDTO {
    private int hour;         // 0..23
    private long acidentes;
    private long mortos;
    private long value;

    public HoraAggDTO() {}

    public HoraAggDTO(int hour, long acidentes, long mortos, long value) {
        this.hour = hour;
        this.acidentes = acidentes;
        this.mortos = mortos;
        this.value = value;
    }

    public int getHour() { return hour; }
    public void setHour(int hour) { this.hour = hour; }

    public long getAcidentes() { return acidentes; }
    public void setAcidentes(long acidentes) { this.acidentes = acidentes; }

    public long getMortos() { return mortos; }
    public void setMortos(long mortos) { this.mortos = mortos; }

    public long getValue() { return value; }
    public void setValue(long value) { this.value = value; }
}
