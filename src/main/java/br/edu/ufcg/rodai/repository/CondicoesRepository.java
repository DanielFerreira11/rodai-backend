package br.edu.ufcg.rodai.repository;

import br.edu.ufcg.rodai.model.CondicoesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CondicoesRepository extends JpaRepository<CondicoesModel, String> {
}
