package br.edu.ufcg.rodai.repository;

import br.edu.ufcg.rodai.model.AcidenteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcidenteRepository extends JpaRepository<AcidenteModel, Long> {
    // Podemos adicionar métodos customizados depois, se necessário
}
