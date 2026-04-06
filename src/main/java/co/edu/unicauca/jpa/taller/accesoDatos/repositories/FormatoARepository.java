package co.edu.unicauca.jpa.taller.accesoDatos.repositories;

import co.edu.unicauca.jpa.taller.accesoDatos.model.FormatoA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormatoARepository extends JpaRepository<FormatoA, Integer> {
}
