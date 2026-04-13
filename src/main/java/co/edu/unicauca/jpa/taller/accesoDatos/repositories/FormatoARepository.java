package co.edu.unicauca.jpa.taller.accesoDatos.repositories;

import co.edu.unicauca.jpa.taller.accesoDatos.dto.FormatoADetalleDTO;
import co.edu.unicauca.jpa.taller.accesoDatos.model.FormatoA;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FormatoARepository extends JpaRepository<FormatoA, Integer> {

	Optional<FormatoA> findByTitulo(String titulo);

	@EntityGraph(attributePaths = {"docente", "estado", "evaluaciones"})
	Optional<FormatoA> findDetalleByIdFormatoA(Integer idFormatoA);

	List<FormatoA> findByDocente_NombresDocenteIgnoreCase(String nombreDocente);

	/*
	Crear una query que reciba el título de un formato A, y realice un join entre docentes,
		formato A, evaluaciones y observaciones. Debe retornar como resultado información del
		formato A y un histórico de evaluaciones, observaciones y docente que la planteo. Usar JPQL
	 */
	@Query("SELECT new FormatoADetalleDTO(" +
			"f.idFormatoA, f.titulo, d.nombresDocente, d.apellidosDocente, es.estadoActual, " +
			"e.idEvaluacion, e.concepto, o.idObservacion, o.observacion, od.nombresDocente, od.apellidosDocente) " +
			"FROM FormatoA f " +
			"JOIN f.docente d " +
			"LEFT JOIN f.estado es " +
			"LEFT JOIN f.evaluaciones e " +
			"LEFT JOIN e.observaciones o " +
			"LEFT JOIN o.docentes od " +
			"WHERE f.titulo = :titulo " +
			"ORDER BY e.idEvaluacion, o.idObservacion, od.idDocente")
	List<FormatoADetalleDTO> findFormatoADetalladoPorTitulo(@Param("titulo") String titulo);
}
