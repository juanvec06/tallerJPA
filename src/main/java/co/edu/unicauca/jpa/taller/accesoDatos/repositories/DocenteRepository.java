package co.edu.unicauca.jpa.taller.accesoDatos.repositories;

import co.edu.unicauca.jpa.taller.accesoDatos.model.Docente;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Integer> {

	Optional<Docente> findByCorreo(String correo);
	List<Docente> findByNombreGrupoStartingWithIgnoreCaseOrderByApellidosDocente(String nombreGrupo, String patronBusqueda);

	@EntityGraph(attributePaths = {"formatos"})
	Optional<Docente> findConFormatosByIdDocente(Integer idDocente);
}