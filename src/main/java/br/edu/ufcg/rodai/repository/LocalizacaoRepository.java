package br.edu.ufcg.rodai.repository;

import br.edu.ufcg.rodai.model.LocalizacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalizacaoRepository extends JpaRepository<LocalizacaoModel, String> {
}
