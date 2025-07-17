package br.edu.ufcg.rodai.model;

import jakarta.persistence.*;

@Entity
@Table(name = "acidente", schema = "public")
public class AcidenteModel {

    @Id
    @Column(name = "id")
    private String id;

    private String data_inversa;
    private String horario;
    private String dia_semana;
    private String uf;
    private String br;
    private Double km;
    private String municipio;
    private String tipo_acidente;
    private String classificacao_acidente;
    private String causa_acidente;
    private Integer ano;
    private String id_original;

    // Getters e Setters
    // (Talvez usar Lombok futuramente, mas por enquanto vai ser explícito se preferir)

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}


    public String getData_inversa() { return data_inversa; }
    public void setData_inversa(String data_inversa) { this.data_inversa = data_inversa; }

    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }

    public String getDia_semana() { return dia_semana; }
    public void setDia_semana(String dia_semana) { this.dia_semana = dia_semana; }

    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }

    public String getBr() { return br; }
    public void setBr(String br) { this.br = br; }

    public Double getKm() { return km; }
    public void setKm(Double km) { this.km = km; }

    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }

    public String getTipo_acidente() { return tipo_acidente; }
    public void setTipo_acidente(String tipo_acidente) { this.tipo_acidente = tipo_acidente; }

    public String getClassificacao_acidente() { return classificacao_acidente; }
    public void setClassificacao_acidente(String classificacao_acidente) { this.classificacao_acidente = classificacao_acidente; }

    public String getCausa_acidente() { return causa_acidente; }
    public void setCausa_acidente(String causa_acidente) { this.causa_acidente = causa_acidente; }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }

    public String getId_original() { return id_original; }
    public void setId_original(String id_original) { this.id_original = id_original; }
}
