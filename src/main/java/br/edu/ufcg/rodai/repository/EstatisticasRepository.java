package br.edu.ufcg.rodai.repository;

import br.edu.ufcg.rodai.dto.*;
import br.edu.ufcg.rodai.model.AcidenteModel;
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

    @Query("SELECT new br.edu.ufcg.rodai.dto.VitimasFataisPorAnoDTO(a.ano, SUM(e.mortos)) " +
            "FROM AcidenteModel a JOIN EstatisticasModel e ON a.id = e.idAcidente " +
            "GROUP BY a.ano ORDER BY a.ano")
    List<VitimasFataisPorAnoDTO> contarMortosPorAno();

    @Query("SELECT new br.edu.ufcg.rodai.dto.LocalizacaoAcidenteDTO(l.latitude, l.longitude, a.uf) " +
            "FROM AcidenteModel a JOIN LocalizacaoModel l ON a.id = l.idAcidente " +
            "WHERE a.ano BETWEEN :anoInicio AND :anoFim")
    List<LocalizacaoAcidenteDTO> listarLocalizacoes(@Param("anoInicio") Integer anoInicio,
                                                    @Param("anoFim") Integer anoFim);
}
