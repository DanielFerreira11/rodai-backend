// src/main/java/br/edu/ufcg/rodai/controller/TempoController.java
package br.edu.ufcg.rodai.controller;

import br.edu.ufcg.rodai.dto.DimCountDTO;
import br.edu.ufcg.rodai.dto.HeatmapCellDTO;
import br.edu.ufcg.rodai.dto.LabelTotalDTO;
import br.edu.ufcg.rodai.dto.MatrizDTO;
import br.edu.ufcg.rodai.repository.TempoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TempoController.java
@RestController
@RequestMapping("/api/tempo")
public class TempoController {

    private final TempoRepository repo;

    public TempoController(TempoRepository repo) { this.repo = repo; }

    @GetMapping("/matriz")
    public List<MatrizDTO> matriz(
            @RequestParam String metric,
            @RequestParam(required = false) String uf,
            @RequestParam(required = false) String municipio,
            @RequestParam(required = false) String tipoAcidente,
            @RequestParam(required = false) Integer anoInicio,
            @RequestParam(required = false) Integer anoFim
    ) {
        return repo.matriz(metric, uf, municipio, tipoAcidente, anoInicio, anoFim);
    }

    @GetMapping("/dias")
    public List<LabelTotalDTO> dias(
            @RequestParam String metric,
            @RequestParam(required = false) String uf,
            @RequestParam(required = false) String municipio,
            @RequestParam(required = false) String tipoAcidente,
            @RequestParam(required = false) Integer anoInicio,
            @RequestParam(required = false) Integer anoFim
    ) {
        return repo.dias(metric, uf, municipio, tipoAcidente, anoInicio, anoFim);
    }

    @GetMapping("/horas")
    public List<LabelTotalDTO> horas(
            @RequestParam String metric,
            @RequestParam(required = false) String uf,
            @RequestParam(required = false) String municipio,
            @RequestParam(required = false) String tipoAcidente,
            @RequestParam(required = false) Integer anoInicio,
            @RequestParam(required = false) Integer anoFim
    ) {
        return repo.horas(metric, uf, municipio, tipoAcidente, anoInicio, anoFim);
    }
}

