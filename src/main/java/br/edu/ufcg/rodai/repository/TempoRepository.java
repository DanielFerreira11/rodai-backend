package br.edu.ufcg.rodai.repository;

import br.edu.ufcg.rodai.dto.DimCountDTO;
import br.edu.ufcg.rodai.dto.HeatmapCellDTO;
import br.edu.ufcg.rodai.dto.LabelTotalDTO;
import br.edu.ufcg.rodai.dto.MatrizDTO;
import br.edu.ufcg.rodai.model.AcidenteModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

// TempoRepository.java
@Repository
public interface TempoRepository extends JpaRepository<AcidenteModel, String> {

    // --- Matriz: dia(lower) x hora ---
    @Query(value = """
    SELECT LOWER(a.dia_semana)      AS dia,
           EXTRACT(HOUR FROM a.horario)::int AS hora,
           CASE
             WHEN :metric = 'mortos' THEN COALESCE(SUM(e.mortos), 0)
             ELSE COUNT(*)::bigint
           END                      AS total
    FROM acidente a
    LEFT JOIN estatisticas e ON e.id_acidente = a.id
    WHERE 1=1
      AND (:uf IS NULL OR a.uf = :uf)
      AND (:tipoAcidente IS NULL OR a.tipo_acidente = :tipoAcidente)
      AND (:anoInicio IS NULL OR a.ano >= :anoInicio)
      AND (:anoFim IS NULL OR a.ano <= :anoFim)
      -- município (case-insensitive):
      AND (:municipio IS NULL OR a.municipio ILIKE CONCAT('%', :municipio, '%'))
      -- Se quiser tolerant a acentos e tiver extensão unaccent:
      -- AND (:municipio IS NULL OR unaccent(LOWER(a.municipio)) = unaccent(LOWER(:municipio)))
    GROUP BY LOWER(a.dia_semana), EXTRACT(HOUR FROM a.horario)
    ORDER BY 1, 2
  """, nativeQuery = true)
    List<MatrizDTO> matriz(String metric, String uf, String municipio, String tipoAcidente,
                           Integer anoInicio, Integer anoFim);

    // --- Agregado por dia da semana ---
    @Query(value = """
    SELECT LOWER(a.dia_semana) AS label,
           CASE
             WHEN :metric = 'mortos' THEN COALESCE(SUM(e.mortos), 0)
             ELSE COUNT(*)::bigint
           END               AS total
    FROM acidente a
    LEFT JOIN estatisticas e ON e.id_acidente = a.id
    WHERE 1=1
      AND (:uf IS NULL OR a.uf = :uf)
      AND (:tipoAcidente IS NULL OR a.tipo_acidente = :tipoAcidente)
      AND (:anoInicio IS NULL OR a.ano >= :anoInicio)
      AND (:anoFim IS NULL OR a.ano <= :anoFim)
      AND (:municipio IS NULL OR a.municipio ILIKE CONCAT('%', :municipio, '%'))
    GROUP BY LOWER(a.dia_semana)
    ORDER BY
      CASE
        WHEN LOWER(a.dia_semana)='segunda-feira' THEN 1
        WHEN LOWER(a.dia_semana)='terça-feira'   THEN 2
        WHEN LOWER(a.dia_semana)='quarta-feira'  THEN 3
        WHEN LOWER(a.dia_semana)='quinta-feira'  THEN 4
        WHEN LOWER(a.dia_semana)='sexta-feira'   THEN 5
        WHEN LOWER(a.dia_semana)='sábado'        THEN 6
        WHEN LOWER(a.dia_semana)='domingo'       THEN 7
        ELSE 8 END
  """, nativeQuery = true)
    List<LabelTotalDTO> dias(String metric, String uf, String municipio, String tipoAcidente,
                             Integer anoInicio, Integer anoFim);

    // --- Agregado por hora do dia ---
    @Query(value = """
    SELECT EXTRACT(HOUR FROM a.horario)::int AS label,
           CASE
             WHEN :metric = 'mortos' THEN COALESCE(SUM(e.mortos), 0)
             ELSE COUNT(*)::bigint
           END                        AS total
    FROM acidente a
    LEFT JOIN estatisticas e ON e.id_acidente = a.id
    WHERE 1=1
      AND (:uf IS NULL OR a.uf = :uf)
      AND (:tipoAcidente IS NULL OR a.tipo_acidente = :tipoAcidente)
      AND (:anoInicio IS NULL OR a.ano >= :anoInicio)
      AND (:anoFim IS NULL OR a.ano <= :anoFim)
      AND (:municipio IS NULL OR a.municipio ILIKE CONCAT('%', :municipio, '%'))
    GROUP BY EXTRACT(HOUR FROM a.horario)
    ORDER BY label
  """, nativeQuery = true)
    List<LabelTotalDTO> horas(String metric, String uf, String municipio, String tipoAcidente,
                              Integer anoInicio, Integer anoFim);
}

