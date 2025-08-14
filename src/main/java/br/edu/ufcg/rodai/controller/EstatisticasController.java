package br.edu.ufcg.rodai.controller;

import br.edu.ufcg.rodai.dto.*;
import br.edu.ufcg.rodai.repository.EstatisticasRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estatisticas")
public class EstatisticasController {

    private final EstatisticasRepository estatisticasRepository;

    public EstatisticasController(EstatisticasRepository estatisticasRepository) {
        this.estatisticasRepository = estatisticasRepository;
    }

    /**
     * Retorna o número total de acidentes por ano.
     *
     * Útil para construir séries temporais gerais da quantidade de acidentes.
     *
     * Exemplo:
     * GET /api/estatisticas/acidentes-por-ano
     */
    @GetMapping("/acidentes-por-ano")
    @Operation(summary = "Total de acidentes por ano")
    public List<AcidentesPorAnoDTO> acidentesPorAno() {
        return estatisticasRepository.contarAcidentesPorAno();
    }

    // EstatisticasController.java

    /**
     * Total de acidentes por UF em um período.
     * Se anoInicio/anoFim não forem informados, devolve o total histórico (LEGADO).
     *
     * Exemplos:
     *   GET /api/estatisticas/acidentes-por-uf?anoInicio=2015&anoFim=2020
     *   GET /api/estatisticas/acidentes-por-uf             (legado, total histórico)
     */
    @GetMapping("/acidentes-por-uf")
    @Operation(summary = "Total de acidentes por UF em um período")
    public List<AcidentesPorUFDTO> acidentesPorUF(
            @RequestParam(required = false) Integer anoInicio,
            @RequestParam(required = false) Integer anoFim
    ) {
        if (anoInicio != null && anoFim != null) {
            return estatisticasRepository.contarAcidentesPorUFNoPeriodo(anoInicio, anoFim);
        }
        // LEGADO: mantém o comportamento antigo quando os anos não são enviados
        return estatisticasRepository.contarAcidentesPorUF();
    }




    /**
     * Retorna o total de mortos por ano com filtros opcionais.
     *
     * Parâmetros opcionais:
     * - uf: ex. "PB"
     * - anoInicio: ex. 2015
     * - anoFim: ex. 2023
     * - tipoAcidente: ex. "Colisão frontal"
     * - minPessoas: mínimo de pessoas envolvidas, ex. 3
     *
     * Todos os filtros são ignorados se forem nulos.
     *
     * Exemplo:
     * GET /api/estatisticas/mortos-por-ano?uf=PB&anoInicio=2018&anoFim=2023
     */
    @GetMapping("/mortos-por-ano")
    @Operation(summary = "Número de vítimas fatais por ano (com filtros opcionais)")
    public List<VitimasFataisPorAnoDTO> mortosPorAno(
            @RequestParam(required = false) String uf,
            @RequestParam(required = false) Integer anoInicio,
            @RequestParam(required = false) Integer anoFim,
            @RequestParam(required = false) String tipoAcidente,
            @RequestParam(required = false) Integer minPessoas
    ) {
        return estatisticasRepository.contarMortosPorAnoComFiltros(uf, anoInicio, anoFim, tipoAcidente, minPessoas);
    }

    /**
     * Retorna a localização (latitude/longitude) dos acidentes entre dois anos.
     *
     * Resultado paginado — útil para visualização em mapas.
     *
     * Parâmetros obrigatórios:
     * - anoInicio: ex. 2017
     * - anoFim: ex. 2023
     *
     * Parâmetros opcionais:
     * - page: número da página (default 0)
     * - size: tamanho da página (default 500)
     *
     * Exemplo:
     * GET /api/estatisticas/localizacao?anoInicio=2020&anoFim=2023&page=0&size=1000
     */
    @GetMapping("/localizacao")
    public List<LocalizacaoAcidenteDTO> getLocalizacoes(@RequestParam Integer anoInicio,
                                                        @RequestParam Integer anoFim,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "500") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return estatisticasRepository.listarLocalizacoes(anoInicio, anoFim, pageable);
    }

    /**
     * Retorna o número de acidentes por tipo (ex: colisão, tombamento).
     *
     * Filtros opcionais:
     * - uf: ex. "PE"
     * - anoInicio: ex. 2018
     * - anoFim: ex. 2023
     *
     * Exemplo:
     * GET /api/estatisticas/tipo-acidente?uf=PB&anoInicio=2019&anoFim=2022
     */
    @GetMapping("/tipo-acidente")
    public List<TipoAcidenteDTO> obterTiposAcidentePorUF(
            @RequestParam(required = false) String uf,
            @RequestParam Integer anoInicio,
            @RequestParam Integer anoFim) {
        return estatisticasRepository.contarTipoAcidentePorUFAndAno(uf, anoInicio, anoFim);
    }

    /**
     * Retorna o número de feridos (leves + graves) por tipo de pista.
     *
     * Filtros opcionais:
     * - uf: ex. "SP"
     * - anoInicio: ex. 2016
     * - anoFim: ex. 2022
     * - mortosMin: número mínimo de mortos no acidente (ex: 1)
     *
     * Exemplo:
     * GET /api/estatisticas/feridos-por-tipo-pista?uf=SP&anoInicio=2019&anoFim=2023&mortosMin=2
     */
    @GetMapping("/feridos-por-tipo-pista")
    public List<FeridosPorTipoPistaDTO> feridosPorTipoPista(
            @RequestParam(required = false) String uf,
            @RequestParam(required = false) Integer anoInicio,
            @RequestParam(required = false) Integer anoFim,
            @RequestParam(required = false) Integer mortosMin
    ) {
        return estatisticasRepository.contarFeridosPorTipoPistaComFiltros(uf, anoInicio, anoFim, mortosMin);
    }

    /**
     * Retorna o total de vítimas fatais (mortos) por condição meteorológica.
     *
     * Útil para analisar como o clima afeta a gravidade dos acidentes.
     *
     * Parâmetros obrigatórios:
     * - anoInicio: ex. 2015
     * - anoFim: ex. 2023
     *
     * Exemplo:
     * GET /api/estatisticas/mortos-por-condicao?anoInicio=2018&anoFim=2023
     */
    @GetMapping("/mortos-por-condicao")
    @Operation(summary = "Total de vítimas fatais por condição meteorológica")
    public List<MortesPorCondicaoDTO> mortosPorCondicao(
            @RequestParam Integer anoInicio,
            @RequestParam Integer anoFim
    ) {
        return estatisticasRepository.contarMortesPorCondicao(anoInicio, anoFim);
    }

    /**
     * Retorna o total de vítimas fatais (mortos) por estado (UF) dentro de um período.
     *
     * Útil para identificar quais estados concentram maior número de mortes no trânsito.
     *
     * Parâmetros obrigatórios:
     * - anoInicio: ex. 2015
     * - anoFim: ex. 2023
     *
     * Exemplo:
     * GET /api/estatisticas/mortos-por-uf?anoInicio=2018&anoFim=2023
     */
    @GetMapping("/mortos-por-uf")
    @Operation(summary = "Total de vítimas fatais por UF em um período")
    public List<MortesPorUFDTO> mortosPorUF(
            @RequestParam Integer anoInicio,
            @RequestParam Integer anoFim
    ) {
        return estatisticasRepository.contarMortesPorUF(anoInicio, anoFim);
    }

    /**
     * Retorna o total de acidentes com vítimas (mortos + feridos) por ano.
     *
     * Ideal para comparar com o total geral de acidentes e verificar proporção com vítimas.
     *
     * Exemplo:
     * GET /api/estatisticas/acidentes-com-vitimas-por-ano
     */
    @GetMapping("/acidentes-com-vitimas-por-ano")
    @Operation(summary = "Total de acidentes com vítimas (mortos ou feridos) por ano")
    public List<AcidentesComVitimasPorAnoDTO> acidentesComVitimasPorAno() {
        return estatisticasRepository.contarVitimasPorAno();
    }


}

