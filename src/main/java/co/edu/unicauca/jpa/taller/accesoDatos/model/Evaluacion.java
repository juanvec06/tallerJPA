package co.edu.unicauca.jpa.taller.accesoDatos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "evaluaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evaluacion")
    private Integer idEvaluacion;

    @Column(name = "concepto", length = 255)
    private String concepto;

    @Column(name = "fecha_registro_concepto", nullable = false)
    private LocalDate fechaRegistroConcepto;

    @Column(name = "nombre_coordinador", length = 100)
    private String nombreCoordinador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idfk_formato_a", nullable = false)
    private FormatoA formatoA;

    @OneToMany(mappedBy = "evaluacion")
    private List<Observacion> observaciones = new ArrayList<>();
}
