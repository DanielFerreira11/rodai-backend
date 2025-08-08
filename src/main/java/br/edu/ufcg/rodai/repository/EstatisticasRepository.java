package br.edu.ufcg.rodai.repository;

import br.edu.ufcg.rodai.dto.*;
import br.edu.ufcg.rodai.model.AcidenteModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EstatisticasRepository extends JpaRepository<AcidenteModel, String> {

    @Query("SELECT new br.edu.ufcg.rodai.dto.AcidentesPorAnoDTO(a.ano, COUNT(a)) " +
            "FROM AcidenteModel a GROUP BY a.ano ORDER BY a.ano")
    List<AcidentesPorAnoDTO> contarAcidentesPorAno();

    @Query("SELECT new br.edu.ufcg.rodai.dto.AcidentesPorUFDTO(a.uf, COUNT(a)) " +
            "FROM AcidenteModel a GROUP BY a.uf ORDER BY COUNT(a) DESC")
    List<AcidentesPorUFDTO> contarAcidentesPorUF();

    @Query("""
    SELECT new br.edu.ufcg.rodai.dto.VitimasFataisPorAnoDTO(a.ano, SUM(e.mortos)) 
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



    @Query("SELECT new br.edu.ufcg.rodai.dto.LocalizacaoAcidenteDTO(l.latitude, l.longitude, a.uf) " +
            "FROM AcidenteModel a JOIN LocalizacaoModel l ON a.id = l.idAcidente " +
            "WHERE a.ano BETWEEN :anoInicio AND :anoFim")
    List<LocalizacaoAcidenteDTO> listarLocalizacoes(@Param("anoInicio") Integer anoInicio,
                                                    @Param("anoFim") Integer anoFim,
                                                    Pageable pageable);

    @Query("SELECT new br.edu.ufcg.rodai.dto.TipoAcidenteDTO(a.tipoAcidente, COUNT(a)) " +
            "FROM AcidenteModel a " +
            "WHERE (:uf IS NULL OR a.uf = :uf) " +
            "AND a.ano BETWEEN :anoInicio AND :anoFim " +
            "GROUP BY a.tipoAcidente " +
            "ORDER BY COUNT(a) DESC")
    List<TipoAcidenteDTO> contarTipoAcidentePorUFAndAno(@Param("uf") String uf,
                                                        @Param("anoInicio") Integer anoInicio,
                                                        @Param("anoFim") Integer anoFim);

    // 1. Total de mortos por condição meteorológica
    @Query("SELECT new br.edu.ufcg.rodai.dto.MortesPorCondicaoDTO(c.condicaoMeteorologica, SUM(e.mortos)) " +
            "FROM AcidenteModel a " +
            "JOIN EstatisticasModel e ON a.id = e.idAcidente " +
            "JOIN CondicoesModel c ON a.id = c.idAcidente " +
            "WHERE a.ano BETWEEN :anoInicio AND :anoFim " +
            "GROUP BY c.condicaoMeteorologica " +
            "ORDER BY SUM(e.mortos) DESC")
    List<MortesPorCondicaoDTO> contarMortesPorCondicao(@Param("anoInicio") Integer anoInicio,
                                                       @Param("anoFim") Integer anoFim);




    // 2. Total de feridos por tipo de pista
    @Query("""
    SELECT new br.edu.ufcg.rodai.dto.FeridosPorTipoPistaDTO(c.tipoPista, SUM(e.feridosLeves + e.feridosGraves)) 
    FROM AcidenteModel a 
    JOIN EstatisticasModel e ON a.id = e.idAcidente 
    JOIN CondicoesModel c ON a.id = c.idAcidente 
    WHERE (:uf IS NULL OR a.uf = :uf)
      AND (:anoInicio IS NULL OR a.ano >= :anoInicio)
      AND (:anoFim IS NULL OR a.ano <= :anoFim)
      AND (:mortosMin IS NULL OR e.mortos >= :mortosMin)
    GROUP BY c.tipoPista 
    ORDER BY SUM(e.feridosLeves + e.feridosGraves) DESC
""")
    List<FeridosPorTipoPistaDTO> contarFeridosPorTipoPistaComFiltros(
            @Param("uf") String uf,
            @Param("anoInicio") Integer anoInicio,
            @Param("anoFim") Integer anoFim,
            @Param("mortosMin") Integer mortosMin
    );





    // 3. Total de mortos por UF no período
    @Query("SELECT new br.edu.ufcg.rodai.dto.MortesPorUFDTO(a.uf, SUM(e.mortos)) " +
            "FROM AcidenteModel a " +
            "JOIN EstatisticasModel e ON a.id = e.idAcidente " +
            "WHERE a.ano BETWEEN :anoInicio AND :anoFim " +
            "GROUP BY a.uf " +
            "ORDER BY SUM(e.mortos) DESC")
    List<MortesPorUFDTO> contarMortesPorUF(@Param("anoInicio") Integer anoInicio,
                                           @Param("anoFim") Integer anoFim);



    // 4. Total de acidentes com vítimas (mortos + feridos) por ano
    @Query("SELECT new br.edu.ufcg.rodai.dto.AcidentesComVitimasPorAnoDTO(a.ano, " +
            "SUM(COALESCE(e.mortos, 0) + COALESCE(e.feridosLeves, 0) + COALESCE(e.feridosGraves, 0))) " +
            "FROM AcidenteModel a " +
            "JOIN EstatisticasModel e ON a.id = e.idAcidente " +
            "GROUP BY a.ano " +
            "ORDER BY a.ano")
    List<AcidentesComVitimasPorAnoDTO> contarVitimasPorAno();


    @Query(value = """
    SELECT 
        c.tipo_pista AS tipoPista, 
        SUM(e.feridos_leves + e.feridos_graves) AS quantidade
    FROM 
        estatisticas e
    JOIN 
        condicoes c ON e.id_acidente = c.id_acidente
    GROUP BY 
        c.tipo_pista
    ORDER BY 
        quantidade DESC
""", nativeQuery = true)
    List<Object[]> contarFeridosPorTipoPistaRaw();


}
