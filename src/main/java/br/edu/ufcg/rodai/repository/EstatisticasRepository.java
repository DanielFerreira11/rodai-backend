package br.edu.ufcg.rodai.repository;

import br.edu.ufcg.rodai.model.EstatisticasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstatisticasRepository extends JpaRepository<EstatisticasModel, String> {
}
