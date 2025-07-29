package br.edu.ufcg.rodai.controller;

import br.edu.ufcg.rodai.model.EstatisticasModel;
import br.edu.ufcg.rodai.repository.EstatisticasRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estatisticas")
public class EstatisticasController {

    private final EstatisticasRepository estatisticasRepository;

    public EstatisticasController(EstatisticasRepository estatisticasRepository) {
        this.estatisticasRepository = estatisticasRepository;
    }

    @GetMapping
    @Operation(summary = "Listar todas as estatísticas com paginação")
    public Page<EstatisticasModel> listarEstatisticas(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return estatisticasRepository.findAll(pageable);
    }
}
