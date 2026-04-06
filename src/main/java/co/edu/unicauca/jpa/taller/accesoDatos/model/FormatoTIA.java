package co.edu.unicauca.jpa.taller.accesoDatos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "formatostia")
@PrimaryKeyJoinColumn(name = "id_formato_a")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormatoTIA extends FormatoA {

    @Column(name = "nombre_estudiante", length = 100)
    private String nombreEstudiante;

    @Column(name = "nombre_asesor", length = 100)
    private String nombreAsesor;
}
