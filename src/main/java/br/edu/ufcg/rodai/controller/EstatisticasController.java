package br.edu.ufcg.rodai.controller;

import br.edu.ufcg.rodai.dto.*;
import br.edu.ufcg.rodai.repository.EstatisticasRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estatisticas")
public class EstatisticasController {

    private final EstatisticasRepository estatisticasRepository;

    public EstatisticasController(EstatisticasRepository estatisticasRepository) {
        this.estatisticasRepository = estatisticasRepository;
    }

    @GetMapping("/acidentes-por-ano")
    @Operation(summary = "Total de acidentes por ano")
    public List<AcidentesPorAnoDTO> acidentesPorAno() {
        return estatisticasRepository.contarAcidentesPorAno();
    }

    @GetMapping("/acidentes-por-uf")
    @Operation(summary = "Total de acidentes por UF")
    public List<AcidentesPorUFDTO> acidentesPorUF() {
        return estatisticasRepository.contarAcidentesPorUF();
    }

    @GetMapping("/mortos-por-ano")
    @Operation(summary = "Número de vítimas fatais por ano")
    public List<VitimasFataisPorAnoDTO> mortosPorAno() {
        return estatisticasRepository.contarMortosPorAno();
    }

    @GetMapping("/localizacao")
    @Operation(summary = "Localização dos acidentes por faixa de ano")
    public List<LocalizacaoAcidenteDTO> localizacaoPorAno(@RequestParam(defaultValue = "2007") Integer anoInicio,
                                                          @RequestParam(defaultValue = "2024") Integer anoFim) {
        return estatisticasRepository.listarLocalizacoes(anoInicio, anoFim);
    }
}
