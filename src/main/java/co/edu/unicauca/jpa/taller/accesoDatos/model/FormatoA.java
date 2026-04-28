package co.edu.unicauca.jpa.taller.accesoDatos.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "formatosa")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FormatoA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_formato_a")
    private Integer idFormatoA;

    @Column(name = "titulo", length = 100, nullable = false)
    private String titulo;

    @Column(name = "objetivo_general", columnDefinition = "TEXT")
    private String objetivoGeneral;

    @Column(name = "objetivos_especificos", columnDefinition = "TEXT")
    private String objetivosEspecificos;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idfk_docente", nullable = false)
    private Docente docente;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "formatoA", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Estado estado;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "formatoA")
    private List<Evaluacion> evaluaciones = new ArrayList<>();

    @PrePersist
    private void registrarEstadoInicial() {
        if (estado == null) {
            Estado estadoInicial = new Estado();
            estadoInicial.setEstadoActual("En formulacion");
            estadoInicial.setFechaRegistroEstado(LocalDate.now());
            estadoInicial.setFormatoA(this);
            estado = estadoInicial;
        }
    }
}
