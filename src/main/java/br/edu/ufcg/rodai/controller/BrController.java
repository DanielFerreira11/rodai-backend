// src/main/java/br/edu/ufcg/rodai/controller/BrController.java
package br.edu.ufcg.rodai.controller;

import br.edu.ufcg.rodai.dto.BrPointDTO;
import br.edu.ufcg.rodai.dto.BrRankingDTO;
import br.edu.ufcg.rodai.repository.BrRepository;
import br.edu.ufcg.rodai.repository.projection.BrPointProjection;
import br.edu.ufcg.rodai.repository.projection.BrRankingProjection;
import br.edu.ufcg.rodai.repository.projection.TraceRow;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/brs")
public class BrController {

    private final BrRepository brRepo;

    public BrController(BrRepository brRepo) {
        this.brRepo = brRepo;
    }

    @GetMapping("/pontos")
    public List<BrPointDTO> pontos(@RequestParam String br,
                                   @RequestParam(defaultValue = "5000") int limit) {

        List<BrPointProjection> rows = brRepo.pontosPorBR(br, limit);
        DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE;

        return rows.stream()
                .map(p -> new BrPointDTO(
                        p.getLat(),
                        p.getLon(),
                        p.getData() == null ? null : p.getData().toLocalDate().format(fmt)
                ))
                .toList();
    }

    @GetMapping("/trace")
    public Map<String, Object> trace(@RequestParam String br,
                                     @RequestParam(defaultValue = "5000") int limit) {

        List<double[]> coords = brRepo.tracePorBR(br, limit).stream()
                .map(r -> new double[]{ r.getLon(), r.getLat() }) // [lon, lat] para usar direto no mapa
                .toList();

        Map<String, Object> res = new HashMap<>();
        res.put("br", br);
        res.put("coords", coords); // sempre presente (pode ser lista vazia)
        return res;
    }

    @GetMapping("/ranking")
    public List<BrRankingDTO> ranking(
            @RequestParam(defaultValue = "acidentes") String metric,
            @RequestParam(defaultValue = "12") int limit) {

        List<BrRankingProjection> rows =
                "mortos".equalsIgnoreCase(metric)
                        ? brRepo.rankingMortos(limit)
                        : brRepo.rankingAcidentes(limit);

        return rows.stream()
                .map(r -> new BrRankingDTO(r.getBr(), r.getTotal()))
                .toList();
    }
}
