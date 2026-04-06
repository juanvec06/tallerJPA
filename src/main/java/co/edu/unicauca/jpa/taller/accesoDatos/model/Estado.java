package co.edu.unicauca.jpa.taller.accesoDatos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado")
    private Integer idEstado;

    @Column(name = "estado_actual", length = 50, nullable = false)
    private String estadoActual;

    @Column(name = "fecha_registro_estado", nullable = false)
    private LocalDate fechaRegistroEstado;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idfk_formato_a", nullable = false, unique = true)
    private FormatoA formatoA;
}
