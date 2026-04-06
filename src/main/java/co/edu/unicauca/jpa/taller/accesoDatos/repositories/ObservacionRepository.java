package co.edu.unicauca.jpa.taller.accesoDatos.repositories;

import co.edu.unicauca.jpa.taller.accesoDatos.model.Observacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObservacionRepository extends JpaRepository<Observacion, Integer> {
}
