package br.edu.ufcg.rodai.controller;

import br.edu.ufcg.rodai.model.CondicoesModel;
import br.edu.ufcg.rodai.repository.CondicoesRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/condicoes")
public class CondicoesController {

    private final CondicoesRepository condicoesRepository;

    public CondicoesController(CondicoesRepository condicoesRepository) {
        this.condicoesRepository = condicoesRepository;
    }

    @GetMapping
    @Operation(summary = "Listar todas as condições com paginação")
    public Page<CondicoesModel> listarCondicoes(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return condicoesRepository.findAll(pageable);
    }
}
