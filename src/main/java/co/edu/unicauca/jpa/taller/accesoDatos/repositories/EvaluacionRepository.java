package co.edu.unicauca.jpa.taller.accesoDatos.repositories;

import co.edu.unicauca.jpa.taller.accesoDatos.model.Evaluacion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Integer> {

    Optional<Evaluacion> findFirstByFormatoA_IdFormatoAOrderByFechaRegistroConceptoDescIdEvaluacionDesc(Integer idFormatoA);
}
