// src/main/java/br/edu/ufcg/rodai/repository/projection/BrPointProjection.java
package br.edu.ufcg.rodai.repository.projection;

import java.sql.Date;

public interface BrPointProjection {
    double getLat();
    double getLon();
    Date getData(); // coluna DATE do Postgres (ex.: a.data_inversa)
}
