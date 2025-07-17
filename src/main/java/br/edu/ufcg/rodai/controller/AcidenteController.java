package br.edu.ufcg.rodai.controller;

import br.edu.ufcg.rodai.model.AcidenteModel;
import br.edu.ufcg.rodai.repository.AcidenteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/acidentes")
public class AcidenteController {

    private final AcidenteRepository acidenteRepository;

    public AcidenteController(AcidenteRepository acidenteRepository) {
        this.acidenteRepository = acidenteRepository;
    }

    @GetMapping
    public Page<AcidenteModel> listarComPaginacao(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return acidenteRepository.findAll(pageable);
    }

}
