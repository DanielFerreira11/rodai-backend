// src/main/java/br/edu/ufcg/rodai/dto/CelulaDTO.java
package br.edu.ufcg.rodai.dto;

public class CelulaDTO {
    private int dow;         // 0..6
    private int hour;        // 0..23
    private String labelDow; // "Dom".."Sáb"
    private long acidentes;
    private long mortos;
    private long value;

    public CelulaDTO() {}

    public CelulaDTO(int dow, int hour, String labelDow, long acidentes, long mortos, long value) {
        this.dow = dow;
        this.hour = hour;
        this.labelDow = labelDow;
        this.acidentes = acidentes;
        this.mortos = mortos;
        this.value = value;
    }

    public int getDow() { return dow; }
    public void setDow(int dow) { this.dow = dow; }

    public int getHour() { return hour; }
    public void setHour(int hour) { this.hour = hour; }

    public String getLabelDow() { return labelDow; }
    public void setLabelDow(String labelDow) { this.labelDow = labelDow; }

    public long getAcidentes() { return acidentes; }
    public void setAcidentes(long acidentes) { this.acidentes = acidentes; }

    public long getMortos() { return mortos; }
    public void setMortos(long mortos) { this.mortos = mortos; }

    public long getValue() { return value; }
    public void setValue(long value) { this.value = value; }
}
