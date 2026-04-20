package co.edu.unicauca.jpa.taller.accesoDatos.repositories;

import co.edu.unicauca.jpa.taller.accesoDatos.model.Evaluacion;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, Integer> {

    Optional<Evaluacion> findFirstByFormatoA_IdFormatoAOrderByFechaRegistroConceptoDescIdEvaluacionDesc(Integer idFormatoA);

    List<Evaluacion> findByFechaRegistroConceptoBetweenAndFormatoA_Docente_NombresDocenteContainingIgnoreCaseOrFormatoA_Docente_ApellidosDocenteContainingIgnoreCase(
    LocalDate fechaInicio,
    LocalDate fechaFin,
    String nombre,
    String apellido
);
}
