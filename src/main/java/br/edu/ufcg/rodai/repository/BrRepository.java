// src/main/java/br/edu/ufcg/rodai/repository/BrRepository.java
package br.edu.ufcg.rodai.repository;

import br.edu.ufcg.rodai.repository.projection.BrPointProjection;
import br.edu.ufcg.rodai.repository.projection.BrRankingProjection;
import br.edu.ufcg.rodai.repository.projection.TraceRow;
import br.edu.ufcg.rodai.model.AcidenteModel; // ajuste se o pacote for outro
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrRepository extends JpaRepository<AcidenteModel, String> {

    @Query(value = """
        select l.latitude  as lat,
               l.longitude as lon,
               a.data_inversa as data
        from localizacao l
        join acidente a on a.id = l.id_acidente
        where a.br = :br
        order by a.data_inversa desc
        limit :limit
        """, nativeQuery = true)
    List<BrPointProjection> pontosPorBR(@Param("br") String br,
                                        @Param("limit") int limit);

    @Query(value = """
        select l.longitude as lon,
               l.latitude  as lat
        from localizacao l
        join acidente a on a.id = l.id_acidente
        where a.br = :br
        order by a.km nulls last, a.data_inversa
        limit :limit
        """, nativeQuery = true)
    List<TraceRow> tracePorBR(@Param("br") String br,
                              @Param("limit") int limit);


    // Top BRs por quantidade de acidentes
    @Query(value = """
        select a.br as br,
               count(*)::bigint as total
        from acidente a
        where a.br is not null and a.br <> ''
        group by a.br
        order by total desc
        limit :limit
        """, nativeQuery = true)
    List<BrRankingProjection> rankingAcidentes(@Param("limit") int limit);

    // Top BRs por número de mortos (requer tabela estatisticas com id_acidente e mortos)
    @Query(value = """
        select a.br as br,
               coalesce(sum(e.mortos),0)::bigint as total
        from acidente a
        left join estatisticas e on e.id_acidente = a.id
        where a.br is not null and a.br <> ''
        group by a.br
        order by total desc
        limit :limit
        """, nativeQuery = true)
    List<BrRankingProjection> rankingMortos(@Param("limit") int limit);
}
