package co.edu.unicauca.jpa.taller.accesoDatos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "docentes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Docente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_docente")
    private Integer idDocente;

    @Column(name = "nombres_docente", length = 100, nullable = false)
    private String nombresDocente;

    @Column(name = "apellidos_docente", length = 100, nullable = false)
    private String apellidosDocente;

    @Column(name = "nombre_grupo", length = 50)
    private String nombreGrupo;

    @Column(name = "correo", length = 100)
    private String correo;

    @OneToMany(mappedBy = "docente")
    private List<FormatoA> formatos = new ArrayList<>();

    @OneToMany(mappedBy = "docente")
    private List<Historico> historicos = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "docentes_observaciones",
            joinColumns = @JoinColumn(name = "idfk_docente"),
            inverseJoinColumns = @JoinColumn(name = "idfk_observacion")
    )
    private List<Observacion> observaciones = new ArrayList<>();
}
