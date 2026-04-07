package co.edu.unicauca.jpa.taller.accesoDatos.repositories;

import co.edu.unicauca.jpa.taller.accesoDatos.model.FormatoA;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormatoARepository extends JpaRepository<FormatoA, Integer> {

	Optional<FormatoA> findByTitulo(String titulo);

	@EntityGraph(attributePaths = {"docente", "estado", "evaluaciones", "evaluaciones.observaciones", "evaluaciones.observaciones.docentes"})
	Optional<FormatoA> findDetalleByIdFormatoA(Integer idFormatoA);
}
