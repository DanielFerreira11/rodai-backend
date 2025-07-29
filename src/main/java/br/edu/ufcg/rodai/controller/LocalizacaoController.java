package br.edu.ufcg.rodai.controller;

import br.edu.ufcg.rodai.model.LocalizacaoModel;
import br.edu.ufcg.rodai.repository.LocalizacaoRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/localizacoes")
public class LocalizacaoController {

    private final LocalizacaoRepository localizacaoRepository;

    public LocalizacaoController(LocalizacaoRepository localizacaoRepository) {
        this.localizacaoRepository = localizacaoRepository;
    }

    @GetMapping
    @Operation(summary = "Listar todas as localizações com paginação")
    public Page<LocalizacaoModel> listarLocalizacoes(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return localizacaoRepository.findAll(pageable);
    }
}
