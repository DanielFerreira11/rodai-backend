// src/main/java/br/edu/ufcg/rodai/support/TempoLabels.java
package br.edu.ufcg.rodai.support;

public final class TempoLabels {
    private TempoLabels() {}
    private static final String[] DOW = {"Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb"};
    public static String dowLabel(int i) {
        return (i >= 0 && i < DOW.length) ? DOW[i] : "?";
    }
}
