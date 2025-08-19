package br.edu.ufcg.rodai.repository;

import br.edu.ufcg.rodai.dto.*;
import br.edu.ufcg.rodai.model.AcidenteModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EstatisticasRepository extends JpaRepository<AcidenteModel, String> {

    // ----------------- Séries temporais -----------------

    @Query("""
           SELECT new br.edu.ufcg.rodai.dto.AcidentesPorAnoDTO(a.ano, COUNT(a))
           FROM AcidenteModel a
           GROUP BY a.ano
           ORDER BY a.ano
           """)
    List<AcidentesPorAnoDTO> contarAcidentesPorAno();

    @Query("""
           SELECT new br.edu.ufcg.rodai.dto.AcidentesPorAnoDTO(a.ano, COUNT(a))
           FROM AcidenteModel a
           WHERE (:uf IS NULL OR a.uf = :uf)
             AND (:tipoAcidente IS NULL OR a.tipoAcidente = :tipoAcidente)
             AND (:anoInicio IS NULL OR a.ano >= :anoInicio)
             AND (:anoFim IS NULL OR a.ano <= :anoFim)
           GROUP BY a.ano
           ORDER BY a.ano
           """)
    List<AcidentesPorAnoDTO> contarAcidentesPorAnoComFiltros(
            @Param("uf") String uf,
            @Param("anoInicio") Integer anoInicio,
            @Param("anoFim") Integer anoFim,
            @Param("tipoAcidente") String tipoAcidente
    );

    // --- versões NATIVAS quando filtra por MUNICÍPIO (compatíveis com BYTEA e com TEXT) ---

    // coluna municipio = BYTEA
    @Query(value = """
            SELECT a.ano AS ano, COUNT(*) AS total
            FROM acidente a
            WHERE (:uf IS NULL OR a.uf = :uf)
              AND convert_from(a.municipio,'UTF8') ILIKE :municipio
              AND (:tipoAcidente IS NULL OR a.tipo_acidente = :tipoAcidente)
              AND (:anoInicio IS NULL OR a.ano >= :anoInicio)
              AND (:anoFim IS NULL OR a.ano <= :anoFim)
            GROUP BY a.ano
            ORDER BY a.ano
            """,
            nativeQuery = true)
    List<Object[]> contarAcidentesPorAnoMunicipioBytea(@Param("uf") String uf,
                                                       @Param("municipio") String municipioEquals,
                                                       @Param("anoInicio") Integer anoInicio,
                                                       @Param("anoFim") Integer anoFim,
                                                       @Param("tipoAcidente") String tipoAcidente);

    // coluna municipio = TEXT / VARCHAR
    @Query(value = """
            SELECT a.ano AS ano, COUNT(*) AS total
            FROM acidente a
            WHERE (:uf IS NULL OR a.uf = :uf)
              AND a.municipio ILIKE :municipio
              AND (:tipoAcidente IS NULL OR a.tipo_acidente = :tipoAcidente)
              AND (:anoInicio IS NULL OR a.ano >= :anoInicio)
              AND (:anoFim IS NULL OR a.ano <= :anoFim)
            GROUP BY a.ano
            ORDER BY a.ano
            """,
            nativeQuery = true)
    List<Object[]> contarAcidentesPorAnoMunicipioText(@Param("uf") String uf,
                                                      @Param("municipio") String municipioEquals,
                                                      @Param("anoInicio") Integer anoInicio,
                                                      @Param("anoFim") Integer anoFim,
                                                      @Param("tipoAcidente") String tipoAcidente);

    // ----------------- Agregações por UF -----------------

    @Query("""
           SELECT new br.edu.ufcg.rodai.dto.AcidentesPorUFDTO(a.uf, COUNT(a))
           FROM AcidenteModel a
           GROUP BY a.uf
           ORDER BY COUNT(a) DESC
           """)
    List<AcidentesPorUFDTO> contarAcidentesPorUF();

    @Query("""
           SELECT new br.edu.ufcg.rodai.dto.AcidentesPorUFDTO(a.uf, COUNT(a))
           FROM AcidenteModel a
           WHERE a.ano BETWEEN :anoInicio AND :anoFim
           GROUP BY a.uf
           ORDER BY COUNT(a) DESC
           """)
    List<AcidentesPorUFDTO> contarAcidentesPorUFNoPeriodo(@Param("anoInicio") Integer anoInicio,
                                                          @Param("anoFim") Integer anoFim);

    // ----------------- Mortes (séries e agregações) -----------------

    @Query("""
           SELECT new br.edu.ufcg.rodai.dto.VitimasFataisPorAnoDTO(a.ano, SUM(COALESCE(e.mortos,0)))
           FROM AcidenteModel a
           JOIN EstatisticasModel e ON a.id = e.idAcidente
           WHERE (:uf IS NULL OR a.uf = :uf)
             AND (:anoInicio IS NULL OR a.ano >= :anoInicio)
             AND (:anoFim IS NULL OR a.ano <= :anoFim)
             AND (:tipoAcidente IS NULL OR a.tipoAcidente = :tipoAcidente)
             AND (:minPessoas IS NULL OR e.pessoas >= :minPessoas)
           GROUP BY a.ano
           ORDER BY a.ano
           """)
    List<VitimasFataisPorAnoDTO> contarMortosPorAnoComFiltros(
            @Param("uf") String uf,
            @Param("anoInicio") Integer anoInicio,
            @Param("anoFim") Integer anoFim,
            @Param("tipoAcidente") String tipoAcidente,
            @Param("minPessoas") Integer minPessoas
    );

    // com município — BYTEA
    @Query(value = """
            SELECT a.ano AS ano, SUM(COALESCE(e.mortos,0)) AS total
            FROM acidente a
            JOIN estatisticas e ON e.id_acidente = a.id
            WHERE (:uf IS NULL OR a.uf = :uf)
              AND convert_from(a.municipio,'UTF8') ILIKE :municipio
              AND (:tipoAcidente IS NULL OR a.tipo_acidente = :tipoAcidente)
              AND (:minPessoas IS NULL OR e.pessoas >= :minPessoas)
              AND (:anoInicio IS NULL OR a.ano >= :anoInicio)
              AND (:anoFim IS NULL OR a.ano <= :anoFim)
            GROUP BY a.ano
            ORDER BY a.ano
            """,
            nativeQuery = true)
    List<Object[]> contarMortosPorAnoMunicipioBytea(@Param("uf") String uf,
                                                    @Param("municipio") String municipioEquals,
                                                    @Param("anoInicio") Integer anoInicio,
                                                    @Param("anoFim") Integer anoFim,
                                                    @Param("tipoAcidente") String tipoAcidente,
                                                    @Param("minPessoas") Integer minPessoas);

    // com município — TEXT
    @Query(value = """
            SELECT a.ano AS ano, SUM(COALESCE(e.mortos,0)) AS total
            FROM acidente a
            JOIN estatisticas e ON e.id_acidente = a.id
            WHERE (:uf IS NULL OR a.uf = :uf)
              AND a.municipio ILIKE :municipio
              AND (:tipoAcidente IS NULL OR a.tipo_acidente = :tipoAcidente)
              AND (:minPessoas IS NULL OR e.pessoas >= :minPessoas)
              AND (:anoInicio IS NULL OR a.ano >= :anoInicio)
              AND (:anoFim IS NULL OR a.ano <= :anoFim)
            GROUP BY a.ano
            ORDER BY a.ano
            """,
            nativeQuery = true)
    List<Object[]> contarMortosPorAnoMunicipioText(@Param("uf") String uf,
                                                   @Param("municipio") String municipioEquals,
                                                   @Param("anoInicio") Integer anoInicio,
                                                   @Param("anoFim") Integer anoFim,
                                                   @Param("tipoAcidente") String tipoAcidente,
                                                   @Param("minPessoas") Integer minPessoas);

    @Query("""
           SELECT new br.edu.ufcg.rodai.dto.MortesPorUFDTO(a.uf, SUM(COALESCE(e.mortos,0)))
           FROM AcidenteModel a
           JOIN EstatisticasModel e ON a.id = e.idAcidente
           WHERE a.ano BETWEEN :anoInicio AND :anoFim
           GROUP BY a.uf
           ORDER BY SUM(COALESCE(e.mortos,0)) DESC
           """)
    List<MortesPorUFDTO> contarMortesPorUF(@Param("anoInicio") Integer anoInicio,
                                           @Param("anoFim") Integer anoFim);

    // ----------------- Localização (mapa) -----------------

    @Query("""
           SELECT new br.edu.ufcg.rodai.dto.LocalizacaoAcidenteDTO(l.latitude, l.longitude, a.uf)
           FROM AcidenteModel a
           JOIN LocalizacaoModel l ON a.id = l.idAcidente
           WHERE a.ano BETWEEN :anoInicio AND :anoFim
           """)
    List<LocalizacaoAcidenteDTO> listarLocalizacoes(@Param("anoInicio") Integer anoInicio,
                                                    @Param("anoFim") Integer anoFim,
                                                    Pageable pageable);

    // ----------------- Tipo de acidente -----------------

    @Query("""
           SELECT new br.edu.ufcg.rodai.dto.TipoAcidenteDTO(a.tipoAcidente, COUNT(a))
           FROM AcidenteModel a
           WHERE (:uf IS NULL OR a.uf = :uf)
             AND a.ano BETWEEN :anoInicio AND :anoFim
           GROUP BY a.tipoAcidente
           ORDER BY COUNT(a) DESC
           """)
    List<TipoAcidenteDTO> contarTipoAcidentePorUFAndAno(@Param("uf") String uf,
                                                        @Param("anoInicio") Integer anoInicio,
                                                        @Param("anoFim") Integer anoFim);

    @Query("""
           SELECT DISTINCT a.tipoAcidente
           FROM AcidenteModel a
           WHERE (:uf IS NULL OR a.uf = :uf)
             AND (:anoInicio IS NULL OR a.ano >= :anoInicio)
             AND (:anoFim IS NULL OR a.ano <= :anoFim)
           ORDER BY a.tipoAcidente
           """)
    List<String> listarTiposAcidenteDistinct(@Param("uf") String uf,
                                             @Param("anoInicio") Integer anoInicio,
                                             @Param("anoFim") Integer anoFim);

    // ----------------- Condições / feridos -----------------

    @Query("""
           SELECT new br.edu.ufcg.rodai.dto.MortesPorCondicaoDTO(c.condicaoMeteorologica, SUM(COALESCE(e.mortos,0)))
           FROM AcidenteModel a
           JOIN EstatisticasModel e ON a.id = e.idAcidente
           JOIN CondicoesModel c   ON a.id = c.idAcidente
           WHERE a.ano BETWEEN :anoInicio AND :anoFim
           GROUP BY c.condicaoMeteorologica
           ORDER BY SUM(COALESCE(e.mortos,0)) DESC
           """)
    List<MortesPorCondicaoDTO> contarMortesPorCondicao(@Param("anoInicio") Integer anoInicio,
                                                       @Param("anoFim") Integer anoFim);

    @Query("""
           SELECT new br.edu.ufcg.rodai.dto.FeridosPorTipoPistaDTO(c.tipoPista, SUM(COALESCE(e.feridosLeves,0) + COALESCE(e.feridosGraves,0)))
           FROM AcidenteModel a
           JOIN EstatisticasModel e ON a.id = e.idAcidente
           JOIN CondicoesModel c   ON a.id = c.idAcidente
           WHERE (:uf IS NULL OR a.uf = :uf)
             AND (:anoInicio IS NULL OR a.ano >= :anoInicio)
             AND (:anoFim IS NULL OR a.ano <= :anoFim)
             AND (:mortosMin IS NULL OR e.mortos >= :mortosMin)
           GROUP BY c.tipoPista
           ORDER BY SUM(COALESCE(e.feridosLeves,0) + COALESCE(e.feridosGraves,0)) DESC
           """)
    List<FeridosPorTipoPistaDTO> contarFeridosPorTipoPistaComFiltros(
            @Param("uf") String uf,
            @Param("anoInicio") Integer anoInicio,
            @Param("anoFim") Integer anoFim,
            @Param("mortosMin") Integer mortosMin
    );

    // ----------------- Acidentes com vítimas por ano -----------------

    @Query("""
           SELECT new br.edu.ufcg.rodai.dto.AcidentesComVitimasPorAnoDTO(
                    a.ano,
                    SUM(COALESCE(e.mortos,0) + COALESCE(e.feridosLeves,0) + COALESCE(e.feridosGraves,0))
                  )
           FROM AcidenteModel a
           JOIN EstatisticasModel e ON a.id = e.idAcidente
           GROUP BY a.ano
           ORDER BY a.ano
           """)
    List<AcidentesComVitimasPorAnoDTO> contarVitimasPorAno();

    // ----------------- Top municípios -----------------

    @Query("""
       SELECT new br.edu.ufcg.rodai.dto.MunicipioQuantidadeDTO(a.municipio, COUNT(a))
       FROM AcidenteModel a
       WHERE a.uf = :uf
         AND (:tipoAcidente IS NULL OR a.tipoAcidente = :tipoAcidente)
         AND (:anoInicio IS NULL OR a.ano >= :anoInicio)
         AND (:anoFim IS NULL OR a.ano <= :anoFim)
       GROUP BY a.municipio
       ORDER BY COUNT(a) DESC
       """)
    List<MunicipioQuantidadeDTO> topMunicipiosPorAcidentes(@Param("uf") String uf,
                                                           @Param("anoInicio") Integer anoInicio,
                                                           @Param("anoFim") Integer anoFim,
                                                           @Param("tipoAcidente") String tipoAcidente,
                                                           Pageable pageable);

    @Query("""
       SELECT new br.edu.ufcg.rodai.dto.MunicipioQuantidadeDTO(a.municipio, SUM(COALESCE(e.mortos,0)))
       FROM AcidenteModel a
       JOIN EstatisticasModel e ON a.id = e.idAcidente
       WHERE a.uf = :uf
         AND (:tipoAcidente IS NULL OR a.tipoAcidente = :tipoAcidente)
         AND (:anoInicio IS NULL OR a.ano >= :anoInicio)
         AND (:anoFim IS NULL OR a.ano <= :anoFim)
       GROUP BY a.municipio
       ORDER BY SUM(COALESCE(e.mortos,0)) DESC
       """)
    List<MunicipioQuantidadeDTO> topMunicipiosPorMortos(@Param("uf") String uf,
                                                        @Param("anoInicio") Integer anoInicio,
                                                        @Param("anoFim") Integer anoFim,
                                                        @Param("tipoAcidente") String tipoAcidente,
                                                        Pageable pageable);

    // ----------------- Catálogo de municípios (DISTINCT) -----------------

    // BYTEA
    @Query(value = """
            SELECT DISTINCT convert_from(a.municipio,'UTF8') AS nome
            FROM acidente a
            WHERE (:uf IS NULL OR a.uf = :uf)
              AND (:q IS NULL OR convert_from(a.municipio,'UTF8') ILIKE CONCAT('%', :q, '%'))
            ORDER BY nome
            LIMIT :limit
            """, nativeQuery = true)
    List<String> listarMunicipiosDistinctBytea(@Param("uf") String uf,
                                               @Param("q") String q,
                                               @Param("limit") int limit);

    // TEXT
    @Query(value = """
            SELECT DISTINCT a.municipio AS nome
            FROM acidente a
            WHERE (:uf IS NULL OR a.uf = :uf)
              AND (:q IS NULL OR a.municipio ILIKE CONCAT('%', :q, '%'))
            ORDER BY nome
            LIMIT :limit
            """, nativeQuery = true)
    List<String> listarMunicipiosDistinctText(@Param("uf") String uf,
                                              @Param("q") String q,
                                              @Param("limit") int limit);

    // ----------------- Exemplo nativo mantido -----------------

    @Query(value = """
            SELECT 
                c.tipo_pista AS tipoPista, 
                SUM(e.feridos_leves + e.feridos_graves) AS quantidade
            FROM estatisticas e
            JOIN condicoes c ON e.id_acidente = c.id_acidente
            GROUP BY c.tipo_pista
            ORDER BY quantidade DESC
            """, nativeQuery = true)
    List<Object[]> contarFeridosPorTipoPistaRaw();
}
