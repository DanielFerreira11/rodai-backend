package br.edu.ufcg.rodai.controller;

import br.edu.ufcg.rodai.dto.*;
import br.edu.ufcg.rodai.repository.EstatisticasRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/estatisticas")
public class EstatisticasController {

    private final EstatisticasRepository repo;
    private final JdbcTemplate jdbc;

    // cache da detecção do tipo de coluna
    private volatile Boolean municipioIsBytea = null;

    public EstatisticasController(EstatisticasRepository repo, JdbcTemplate jdbc) {
        this.repo = repo;
        this.jdbc = jdbc;
    }

    /** Detecta uma vez se a coluna acidente.municipio é BYTEA. */
    private boolean isMunicipioBytea() {
        if (municipioIsBytea != null) return municipioIsBytea;
        try {
            String sql = """
                select data_type
                from information_schema.columns
                where table_schema = current_schema()
                  and table_name = 'acidente'
                  and column_name = 'municipio'
                """;
            String dt = jdbc.queryForObject(sql, String.class);
            municipioIsBytea = dt != null && dt.equalsIgnoreCase("bytea");
        } catch (Exception e) {
            // fallback conservador: assume TEXT
            municipioIsBytea = false;
        }
        return municipioIsBytea;
    }

    // ---------------------------------------------------------------------
    // SÉRIES TEMPORAIS
    // ---------------------------------------------------------------------

    /**
     * Total de acidentes por ano, com filtros opcionais.
     * Agora aceita `municipio` também.
     */
    @GetMapping("/acidentes-por-ano")
    @Operation(summary = "Total de acidentes por ano (com filtros opcionais, inclusive município)")
    public List<AcidentesPorAnoDTO> acidentesPorAno(
            @RequestParam(required = false) String uf,
            @RequestParam(required = false) String municipio,
            @RequestParam(required = false) Integer anoInicio,
            @RequestParam(required = false) Integer anoFim,
            @RequestParam(required = false) String tipoAcidente
    ) {
        final boolean hasMunicipio = municipio != null && !municipio.isBlank();
        if (!hasMunicipio) {
            final boolean semFiltros = uf == null && anoInicio == null && anoFim == null && tipoAcidente == null;
            if (semFiltros) return repo.contarAcidentesPorAno();
            return repo.contarAcidentesPorAnoComFiltros(uf, anoInicio, anoFim, tipoAcidente);
        }

        // com município: escolhe a consulta compatível com o tipo da coluna
        List<Object[]> rows = isMunicipioBytea()
                ? repo.contarAcidentesPorAnoMunicipioBytea(uf, municipio, anoInicio, anoFim, tipoAcidente)
                : repo.contarAcidentesPorAnoMunicipioText(uf, municipio, anoInicio, anoFim, tipoAcidente);

        List<AcidentesPorAnoDTO> out = new ArrayList<>(rows.size());
        for (Object[] r : rows) {
            int ano = ((Number) r[0]).intValue();
            long total = ((Number) r[1]).longValue();
            out.add(new AcidentesPorAnoDTO(ano, total));
        }
        return out;
    }

    /**
     * Acidentes com vítimas (legado).
     */
    @GetMapping("/acidentes-com-vitimas-por-ano")
    @Operation(summary = "Total de acidentes com vítimas (mortos ou feridos) por ano")
    public List<AcidentesComVitimasPorAnoDTO> acidentesComVitimasPorAno() {
        return repo.contarVitimasPorAno();
    }

    /**
     * Mortos por ano (com filtros opcionais, inclusive município).
     */
    @GetMapping("/mortos-por-ano")
    @Operation(summary = "Número de vítimas fatais por ano (com filtros opcionais, inclusive município)")
    public List<VitimasFataisPorAnoDTO> mortosPorAno(
            @RequestParam(required = false) String uf,
            @RequestParam(required = false) String municipio,
            @RequestParam(required = false) Integer anoInicio,
            @RequestParam(required = false) Integer anoFim,
            @RequestParam(required = false) String tipoAcidente,
            @RequestParam(required = false) Integer minPessoas
    ) {
        final boolean hasMunicipio = municipio != null && !municipio.isBlank();
        if (!hasMunicipio) {
            return repo.contarMortosPorAnoComFiltros(uf, anoInicio, anoFim, tipoAcidente, minPessoas);
        }

        List<Object[]> rows = isMunicipioBytea()
                ? repo.contarMortosPorAnoMunicipioBytea(uf, municipio, anoInicio, anoFim, tipoAcidente, minPessoas)
                : repo.contarMortosPorAnoMunicipioText(uf, municipio, anoInicio, anoFim, tipoAcidente, minPessoas);

        List<VitimasFataisPorAnoDTO> out = new ArrayList<>(rows.size());
        for (Object[] r : rows) {
            int ano = ((Number) r[0]).intValue();
            long total = ((Number) r[1]).longValue();
            out.add(new VitimasFataisPorAnoDTO(ano, total));
        }
        return out;
    }

    // ---------------------------------------------------------------------
    // AGREGAÇÕES POR UF
    // ---------------------------------------------------------------------

    @GetMapping("/acidentes-por-uf")
    @Operation(summary = "Total de acidentes por UF em um período")
    public List<AcidentesPorUFDTO> acidentesPorUF(
            @RequestParam(required = false) Integer anoInicio,
            @RequestParam(required = false) Integer anoFim
    ) {
        if (anoInicio != null && anoFim != null) {
            return repo.contarAcidentesPorUFNoPeriodo(anoInicio, anoFim);
        }
        return repo.contarAcidentesPorUF();
    }

    @GetMapping("/mortos-por-uf")
    @Operation(summary = "Total de vítimas fatais por UF em um período")
    public List<MortesPorUFDTO> mortosPorUF(
            @RequestParam Integer anoInicio,
            @RequestParam Integer anoFim
    ) {
        return repo.contarMortesPorUF(anoInicio, anoFim);
    }

    // ---------------------------------------------------------------------
    // TIPO DE ACIDENTE / CATÁLOGOS
    // ---------------------------------------------------------------------

    @GetMapping("/tipo-acidente")
    @Operation(summary = "Contagem de acidentes por tipo (com filtros opcionais de UF e período)")
    public List<TipoAcidenteDTO> obterTiposAcidentePorUF(
            @RequestParam(required = false) String uf,
            @RequestParam Integer anoInicio,
            @RequestParam Integer anoFim
    ) {
        return repo.contarTipoAcidentePorUFAndAno(uf, anoInicio, anoFim);
    }

    @GetMapping("/catalogos/tipos-acidente")
    @Operation(summary = "Lista de tipos de acidente distintos (para selects)")
    public List<String> catalogoTiposAcidente(
            @RequestParam(required = false) String uf,
            @RequestParam(required = false) Integer anoInicio,
            @RequestParam(required = false) Integer anoFim
    ) {
        return repo.listarTiposAcidenteDistinct(uf, anoInicio, anoFim);
    }

    /**
     * Catálogo de municípios (DISTINCT) com busca textual opcional e filtro por UF.
     * Usa uma consulta compatível com o tipo real da coluna.
     */
    @GetMapping("/catalogos/municipios")
    @Operation(summary = "Lista de municípios distintos (para busca/combobox)")
    public List<String> catalogoMunicipios(
            @RequestParam(required = false) String uf,
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "1000") int limit
    ) {
        int lim = Math.min(Math.max(limit, 1), 10000);
        if (isMunicipioBytea()) {
            return repo.listarMunicipiosDistinctBytea(uf, q, lim);
        }
        return repo.listarMunicipiosDistinctText(uf, q, lim);
    }

    // ---------------------------------------------------------------------
    // OUTRAS AGREGAÇÕES
    // ---------------------------------------------------------------------

    @GetMapping("/feridos-por-tipo-pista")
    @Operation(summary = "Feridos (leves + graves) por tipo de pista (com filtros opcionais)")
    public List<FeridosPorTipoPistaDTO> feridosPorTipoPista(
            @RequestParam(required = false) String uf,
            @RequestParam(required = false) Integer anoInicio,
            @RequestParam(required = false) Integer anoFim,
            @RequestParam(required = false) Integer mortosMin
    ) {
        return repo.contarFeridosPorTipoPistaComFiltros(uf, anoInicio, anoFim, mortosMin);
    }

    @GetMapping("/mortos-por-condicao")
    @Operation(summary = "Total de vítimas fatais por condição meteorológica")
    public List<MortesPorCondicaoDTO> mortosPorCondicao(
            @RequestParam Integer anoInicio,
            @RequestParam Integer anoFim
    ) {
        return repo.contarMortesPorCondicao(anoInicio, anoFim);
    }

    // ---------------------------------------------------------------------
    // GEO (MAPA)
    // ---------------------------------------------------------------------

    @GetMapping("/localizacao")
    @Operation(summary = "Localizações (latitude/longitude) dos acidentes em um período (paginado)")
    public List<LocalizacaoAcidenteDTO> getLocalizacoes(@RequestParam Integer anoInicio,
                                                        @RequestParam Integer anoFim,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "500") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repo.listarLocalizacoes(anoInicio, anoFim, pageable);
    }

    // ---------------------------------------------------------------------
    // TOP MUNICÍPIOS (mapa/modal)
    // ---------------------------------------------------------------------

    @GetMapping("/top-municipios")
    @Operation(summary = "Top municípios por UF/período, por acidentes ou mortos")
    public List<MunicipioQuantidadeDTO> topMunicipios(
            @RequestParam String uf,
            @RequestParam(required = false) Integer anoInicio,
            @RequestParam(required = false) Integer anoFim,
            @RequestParam(required = false) String tipoAcidente,
            @RequestParam(defaultValue = "acidentes") String metric,
            @RequestParam(defaultValue = "10") int limit
    ) {
        int size = Math.min(Math.max(limit, 1), 100);
        Pageable p = PageRequest.of(0, size);
        if ("mortos".equalsIgnoreCase(metric)) {
            return repo.topMunicipiosPorMortos(uf, anoInicio, anoFim, tipoAcidente, p);
        }
        return repo.topMunicipiosPorAcidentes(uf, anoInicio, anoFim, tipoAcidente, p);
    }
}
