package co.edu.unicauca.jpa.taller.accesoDatos.repositories;

import co.edu.unicauca.jpa.taller.accesoDatos.model.FormatoA;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FormatoARepository extends JpaRepository<FormatoA, Integer> {

	Optional<FormatoA> findByTitulo(String titulo);

	@EntityGraph(attributePaths = {"docente", "estado", "evaluaciones"})
	Optional<FormatoA> findDetalleByIdFormatoA(Integer idFormatoA);

	List<FormatoA> findByDocente_NombresDocenteIgnoreCase(String nombreDocente);
}
