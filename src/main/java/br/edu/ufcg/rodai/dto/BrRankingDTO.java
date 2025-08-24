package br.edu.ufcg.rodai.dto;

public class BrRankingDTO {
    private String br;
    private Long total;

    public BrRankingDTO() {}

    public BrRankingDTO(String br, Long total) {
        this.br = br;
        this.total = total;
    }

    public String getBr() { return br; }
    public void setBr(String br) { this.br = br; }

    public Long getTotal() { return total; }
    public void setTotal(Long total) { this.total = total; }
}
