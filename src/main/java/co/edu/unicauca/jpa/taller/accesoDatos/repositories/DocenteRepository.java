package co.edu.unicauca.jpa.taller.accesoDatos.repositories;

import co.edu.unicauca.jpa.taller.accesoDatos.model.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Integer> {
}