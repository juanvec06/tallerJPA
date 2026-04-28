package co.edu.unicauca.jpa.taller;

import co.edu.unicauca.jpa.taller.accesoDatos.dto.FormatoADetalleDTO;
import co.edu.unicauca.jpa.taller.accesoDatos.model.Docente;
import co.edu.unicauca.jpa.taller.accesoDatos.model.Evaluacion;
import co.edu.unicauca.jpa.taller.accesoDatos.model.FormatoA;
import co.edu.unicauca.jpa.taller.accesoDatos.model.FormatoPPA;
import co.edu.unicauca.jpa.taller.accesoDatos.model.FormatoTIA;
import co.edu.unicauca.jpa.taller.accesoDatos.model.Historico;
import co.edu.unicauca.jpa.taller.accesoDatos.model.Observacion;
import co.edu.unicauca.jpa.taller.accesoDatos.model.Rol;
import co.edu.unicauca.jpa.taller.accesoDatos.repositories.DocenteRepository;
import co.edu.unicauca.jpa.taller.accesoDatos.repositories.EvaluacionRepository;
import co.edu.unicauca.jpa.taller.accesoDatos.repositories.FormatoARepository;
import co.edu.unicauca.jpa.taller.accesoDatos.repositories.HistoricoRepository;
import co.edu.unicauca.jpa.taller.accesoDatos.repositories.ObservacionRepository;
import co.edu.unicauca.jpa.taller.accesoDatos.repositories.RolRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class TallerApplication {

	private final FormatoARepository formatoARepository;
	private final DocenteRepository docenteRepository;
	private final EvaluacionRepository evaluacionRepository;
	private final ObservacionRepository observacionRepository;
	private final HistoricoRepository historicoRepository;
	private final RolRepository rolRepository;

	public TallerApplication(
			FormatoARepository formatoARepository,
			DocenteRepository docenteRepository,
			EvaluacionRepository evaluacionRepository,
			ObservacionRepository observacionRepository,
			HistoricoRepository historicoRepository,
			RolRepository rolRepository) {
		this.formatoARepository = formatoARepository;
		this.docenteRepository = docenteRepository;
		this.evaluacionRepository = evaluacionRepository;
		this.observacionRepository = observacionRepository;
		this.historicoRepository = historicoRepository;
		this.rolRepository = rolRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(TallerApplication.class, args);
	}

	@Bean
	CommandLineRunner demoRunner(TallerApplication app) {
		return args -> {
			// PARTE 1 (EJECUTAR ANTES DE TODOS)
			//app.ejecutarPruebas();

			// PARTE 2
			// Arteaga
			//app.buscarEvaluacionesPorFechasYNombreDocente(LocalDate.now().minusDays(10), LocalDate.now(), "Ana", "Zorra");
			//verificarExistenciaFormatoAPorTitulo("Formato TIA - Sistema de sisas");
			//app.actualizarEstadoFormatoA(1, "Aprobado");
			// ...
			// Vela
			buscarDocenteGrupoAPatronA();  // punto 1
			//getFormatosByDocente("Ana");
			//getInfoDetalladaFormatoA("Formato TIA - Sistema de sisas");
			//consultaCorreoDocente("ana.zorra@unicauca.edu.co");
		};
	}

	@Transactional(readOnly = false)
	public void ejecutarPruebas() {
		FormatoA f1 = crearFormatoA(
			true,
			"Formato TIA - Sistema de sisas",
			"Construir un sistema de sisas",
			"Levantar requisitos y sisas",
			"Ana",
			"Ano",
			"Grupo A",
			"ana.ano@unicauca.edu.co",
			"Laura Estudiante",
			"Carlos Asesor");
		FormatoA f2 = crearFormatoA(
			true,
			"Formato TIA - Sistema de kk",
			"Construir un sistema de kk",
			"Levantar requisitos y kk",
			"Ana",
			"Zorra",
			"Grupo A",
			"ana.zorra@unicauca.edu.co",
			"Laura Estudiante",
			"Carlos Asesor");
		FormatoA f3 = crearFormatoA(
				true,
				"Formato TIA - Sistema de test",
				"Construir un sistema de test",
				"Levantar requisitos y test",
				"Ano",
				"Test",
				"Grupo B",
				"ana.zorra@unicauca.edu.co",
				"Laura Estudiante",
				"Carlos Asesor");
		FormatoA f4 = crearFormatoA(
				true,
				"Formato TIA - Sistema de testeo",
				"Construir un sistema de testeo",
				"Levantar requisitos y testeo",
				"Anus",
				"Testeo",
				"Grupo B",
				"ana.zorra@unicauca.edu.co",
				"Laura Estudiante",
				"Carlos Asesor");
		FormatoA formatoTIA = crearFormatoA(
				true,
				"Formato TIA - Sistema de Alertas",
				"Construir un sistema de alertas academicas",
				"Levantar requisitos y validar prototipo",
				"Ana",
				"Diaz",
				"Grupo A",
				"ana.diaz@unicauca.edu.co",
				"Laura Estudiante",
				"Carlos Asesor");

		FormatoA formatoPPA = crearFormatoA(
				false,
				"Formato PPA - Seguimiento de Practica",
				"Monitorear avance de practica profesional",
				"Definir actividades y cronograma",
				"Pedro",
				"Lopez",
				"Grupo B",
				"pedro.lopez@unicauca.edu.co",
				"Mario Practicante",
				"Sandra Asesora");

		Integer docenteComiteId = formatoPPA.getDocente().getIdDocente();
		cargarMiembroComiteSiNoExiste(docenteComiteId, "Comite", LocalDate.now().minusMonths(2), null, true);

		crearObservacion(
				"Debe mejorar la redaccion del objetivo general.",
				formatoTIA.getIdFormatoA(),
				List.of(formatoTIA.getDocente().getIdDocente(), formatoPPA.getDocente().getIdDocente()));

		listarObservaciones(formatoTIA.getIdFormatoA());
		listarMiembrosComite();
		consultarFormatosAPorDocente(formatoTIA.getDocente().getIdDocente());

		//Asignamos varias evaluaciones con diferentes fechas para probar la consulta de búsqueda por fechas y nombre docente
		crearEvaluacionConFecha(formatoTIA.getIdFormatoA(), "Aprobado", LocalDate.now().minusDays(20), "Coordinador 1");
		crearEvaluacionConFecha(f1.getIdFormatoA(), "Rechazado", LocalDate.now().minusDays(10), "Coordinador 2");
		
	}

	@Transactional(readOnly = false)
	public FormatoA crearFormatoA(
			boolean esFormatoTIA,
			String titulo,
			String objetivoGeneral,
			String objetivosEspecificos,
			String nombresDocente,
			String apellidosDocente,
			String nombreGrupo,
			String correoDocente,
			String nombreEstudiante,
			String nombreAsesor) {
		Optional<FormatoA> existente = formatoARepository.findByTitulo(titulo);
		if (existente.isPresent()) {
			return existente.get();
		}

		Docente docente = docenteRepository.findByCorreo(correoDocente)
				.orElseGet(() -> {
					Docente nuevo = new Docente();
					nuevo.setNombresDocente(nombresDocente);
					nuevo.setApellidosDocente(apellidosDocente);
					nuevo.setNombreGrupo(nombreGrupo);
					nuevo.setCorreo(correoDocente);
					return nuevo;
				});

		FormatoA formato = esFormatoTIA ? new FormatoTIA() : new FormatoPPA();
		formato.setTitulo(titulo);
		formato.setObjetivoGeneral(objetivoGeneral);
		formato.setObjetivosEspecificos(objetivosEspecificos);
		formato.setDocente(docente);

		if (formato instanceof FormatoTIA formatoTIA) {
			formatoTIA.setNombreEstudiante(nombreEstudiante);
			formatoTIA.setNombreAsesor(nombreAsesor);
		}

		if (formato instanceof FormatoPPA formatoPPA) {
			formatoPPA.setNombreEstudiante(nombreEstudiante);
			formatoPPA.setNombreAsesor(nombreAsesor);
		}

		return formatoARepository.save(formato);
	}

	@Transactional(readOnly = false)
	public Observacion crearObservacion(String textoObservacion, Integer idFormatoA, List<Integer> idsDocentes) {
		FormatoA formato = formatoARepository.findById(idFormatoA)
				.orElseThrow(() -> new IllegalArgumentException("No existe FormatoA con id " + idFormatoA));

		Evaluacion evaluacion = evaluacionRepository
				.findFirstByFormatoA_IdFormatoAOrderByFechaRegistroConceptoDescIdEvaluacionDesc(idFormatoA)
				.orElseGet(() -> crearEvaluacionSinConcepto(formato));

		if (!"Por corregir".equalsIgnoreCase(valorSeguro(evaluacion.getConcepto()))) {
			evaluacion.setConcepto("Por corregir");
			evaluacion.setFechaRegistroConcepto(LocalDate.now());
			evaluacion = evaluacionRepository.save(evaluacion);
		}

		Evaluacion evaluacionRef = evaluacionRepository.getReferenceById(evaluacion.getIdEvaluacion());
		Observacion observacion = new Observacion();
		observacion.setObservacion(textoObservacion);
		observacion.setFechaRegistroObservacion(LocalDate.now());
		observacion.setEvaluacion(evaluacionRef);

		Observacion observacionGuardada = observacionRepository.save(observacion);

		List<Docente> docentesAutores = new ArrayList<>();
		for (Integer idDocente : idsDocentes.stream().distinct().toList()) {
			Docente docenteRef = docenteRepository.getReferenceById(idDocente);
			docenteRef.getObservaciones().add(observacionGuardada);
			docentesAutores.add(docenteRef);
		}

		observacionGuardada.setDocentes(docentesAutores);
		docenteRepository.saveAll(docentesAutores);
		return observacionGuardada;
	}

	@Transactional(readOnly = true)
	public void listarObservaciones(Integer idFormatoA) {
		FormatoA formato = formatoARepository.findDetalleByIdFormatoA(idFormatoA)
				.orElseThrow(() -> new IllegalArgumentException("No existe FormatoA con id " + idFormatoA));

		System.out.println("\n=== LISTAR OBSERVACIONES ===");
		System.out.println("FormatoA: " + formato.getIdFormatoA() + " - " + formato.getTitulo());
		System.out.println("Docente Director: " + nombreCompleto(formato.getDocente()));
		System.out.println("Estado: " + (formato.getEstado() == null ? "Sin estado" : formato.getEstado().getEstadoActual()));

		for (Evaluacion evaluacion : formato.getEvaluaciones()) {
			System.out.println("Evaluacion " + evaluacion.getIdEvaluacion() + " concepto: " + valorSeguro(evaluacion.getConcepto()));
			for (Observacion observacion : evaluacion.getObservaciones()) {
				String docentes = observacion.getDocentes().stream()
						.map(this::nombreCompleto)
						.collect(Collectors.joining(", "));
				System.out.println("  Observacion " + observacion.getIdObservacion() + ": " + observacion.getObservacion());
				System.out.println("    Docentes que la plantean: " + docentes);
			}
		}
	}

	@Transactional(readOnly = true)
	public void listarMiembrosComite() {
		System.out.println("\n=== LISTAR MIEMBROS DEL COMITE ===");
		List<Historico> miembros = historicoRepository.findByActivoTrue();
		for (Historico historico : miembros) {
			System.out.println(nombreCompleto(historico.getDocente())
					+ " | Rol: " + historico.getRol().getRolAsignado()
					+ " | Inicio: " + historico.getFechaInicio()
					+ " | Fin: " + historico.getFechaFin());
		}
	}

	@Transactional(readOnly = true)
	public void consultarFormatosAPorDocente(Integer idDocente) {
		Docente docente = docenteRepository.findConFormatosByIdDocente(idDocente)
				.orElseThrow(() -> new IllegalArgumentException("No existe Docente con id " + idDocente));

		System.out.println("\n=== CONSULTAR FORMATOS A POR DOCENTE ===");
		System.out.println("Docente: " + nombreCompleto(docente));

		for (FormatoA formato : docente.getFormatos()) {
			System.out.println("FormatoA " + formato.getIdFormatoA() + " - " + formato.getTitulo());
			List<Evaluacion> evaluacionesOrdenadas = formato.getEvaluaciones().stream()
					.sorted(Comparator.comparing(Evaluacion::getFechaRegistroConcepto, Comparator.nullsLast(Comparator.naturalOrder())))
					.toList();
			for (Evaluacion evaluacion : evaluacionesOrdenadas) {
				System.out.println("  Evaluacion " + evaluacion.getIdEvaluacion() + " concepto: " + valorSeguro(evaluacion.getConcepto()));
				for (Observacion observacion : evaluacion.getObservaciones()) {
					System.out.println("    Observacion " + observacion.getIdObservacion() + ": " + observacion.getObservacion());
				}
			}
		}
	}

	@Transactional(readOnly = false)
	public void cargarMiembroComiteSiNoExiste(Integer idDocente, String nombreRol, LocalDate fechaInicio, LocalDate fechaFin, boolean activo) {
		Rol rol = rolRepository.findByRolAsignado(nombreRol)
				.orElseGet(() -> {
					Rol nuevoRol = new Rol();
					nuevoRol.setRolAsignado(nombreRol);
					return rolRepository.save(nuevoRol);
				});

		boolean yaExiste = historicoRepository.findByActivoTrue().stream()
				.anyMatch(h -> h.getDocente().getIdDocente().equals(idDocente)
						&& h.getRol().getIdRol().equals(rol.getIdRol()));

		if (yaExiste) {
			return;
		}

		Historico historico = new Historico();
		historico.setDocente(docenteRepository.getReferenceById(idDocente));
		historico.setRol(rol);
		historico.setFechaInicio(fechaInicio);
		historico.setFechaFin(fechaFin);
		historico.setActivo(activo);
		historicoRepository.save(historico);
	}

	private Evaluacion crearEvaluacionSinConcepto(FormatoA formato) {
		Evaluacion nuevaEvaluacion = new Evaluacion();
		nuevaEvaluacion.setConcepto("Por establecer");
		nuevaEvaluacion.setFechaRegistroConcepto(LocalDate.now());
		nuevaEvaluacion.setNombreCoordinador("Por asignar");
		nuevaEvaluacion.setFormatoA(formato);
		return evaluacionRepository.save(nuevaEvaluacion);
	}

	private Evaluacion crearEvaluacionConFecha(Integer idFormatoA, String concepto, LocalDate fecha, String nombreCoordinador) {
		FormatoA formato = formatoARepository.findById(idFormatoA)
				.orElseThrow(() -> new IllegalArgumentException("No existe FormatoA con id " + idFormatoA));
		Evaluacion nuevaEvaluacion = new Evaluacion();
		nuevaEvaluacion.setConcepto(concepto);
		nuevaEvaluacion.setFechaRegistroConcepto(fecha);
		nuevaEvaluacion.setNombreCoordinador(nombreCoordinador);
		nuevaEvaluacion.setFormatoA(formato);
		return evaluacionRepository.save(nuevaEvaluacion);
	}

	private String nombreCompleto(Docente docente) {
		return docente.getNombresDocente() + " " + docente.getApellidosDocente();
	}

	private String valorSeguro(String valor) {
		return valor == null ? "" : valor;
	}

	// Listar docentes ordenados por apellidos_docente, filtrando por grupo y patrón de búsqueda (ignorando mayúsculas/minúsculas)
	// No encontre un nombre descriptivo para el metodo.
	private void buscarDocenteGrupoAPatronA() {
		String grupoFiltro = "Grupo B";

		List<Docente> docentesFiltrados = docenteRepository.findByNombreGrupoIgnoreCaseAndApellidosDocenteStartingWithIgnoreCaseOrderByApellidosDocente(grupoFiltro, "A");

		System.out.println("\n=== DOCENTES FILTRADOS Y ORDENADOS ===");
		for (Docente docente : docentesFiltrados) {
			System.out.println(nombreCompleto(docente) + " | Grupo: " + docente.getNombreGrupo());
		}

	}

	//	En el repositorio formatos obtener formatos de un docente específico ignorando mayúsculas y minúsculas
	private void getFormatosByDocente(String nombreDocente) {
		List<FormatoA> formatos = formatoARepository.findByDocente_NombresDocenteIgnoreCase(nombreDocente);
		System.out.println("\n=== FORMATOS DEL DOCENTE " + nombreDocente + " ===");
		for (FormatoA formato : formatos) {
			System.out.println("FormatoA " + formato.getIdFormatoA() + " - " + formato.getTitulo());
		}
	}
	// Crear una query que reciba el título de un formato A, y realice un join entre docentes usando JPQL.
	private void getInfoDetalladaFormatoA(String titulo) {
		List<FormatoADetalleDTO> filas = formatoARepository.findFormatoADetalladoPorTitulo(titulo);
		if (filas.isEmpty()) {
			System.out.println("No se encontró el FormatoA con título: " + titulo);
			return;
		}

		FormatoADetalleDTO encabezado = filas.get(0);
		System.out.println("\n=== INFORMACIÓN DETALLADA DEL FORMATO A ===");
		System.out.println("Título: " + encabezado.formatoTitulo());
		System.out.println("Docente: " + encabezado.nombreCompletoDirector());
		System.out.println("Estado: " + valorSeguro(encabezado.estadoActual()));

		Map<Integer, String> conceptosPorEvaluacion = new LinkedHashMap<>();
		Map<Integer, Map<Integer, String>> textoPorObservacion = new LinkedHashMap<>();
		Map<Integer, Map<Integer, List<String>>> docentesPorObservacion = new LinkedHashMap<>();

		for (FormatoADetalleDTO fila : filas) {
			Integer idEvaluacion = fila.evaluacionId();
			if (idEvaluacion == null) {
				continue;
			}

			conceptosPorEvaluacion.putIfAbsent(idEvaluacion, valorSeguro(fila.evaluacionConcepto()));
			textoPorObservacion.computeIfAbsent(idEvaluacion, k -> new LinkedHashMap<>());
			docentesPorObservacion.computeIfAbsent(idEvaluacion, k -> new LinkedHashMap<>());

			Integer idObservacion = fila.observacionId();
			if (idObservacion == null) {
				continue;
			}

			textoPorObservacion.get(idEvaluacion).putIfAbsent(idObservacion, valorSeguro(fila.observacionTexto()));
			docentesPorObservacion.get(idEvaluacion).computeIfAbsent(idObservacion, k -> new ArrayList<>());

			String docenteObservacion = fila.nombreCompletoDocenteObservacion();
			if (!docenteObservacion.isBlank() && !docentesPorObservacion.get(idEvaluacion).get(idObservacion).contains(docenteObservacion)) {
				docentesPorObservacion.get(idEvaluacion).get(idObservacion).add(docenteObservacion);
			}
		}

		for (Map.Entry<Integer, String> evaluacion : conceptosPorEvaluacion.entrySet()) {
			Integer idEvaluacion = evaluacion.getKey();
			System.out.println("Evaluación " + idEvaluacion + " concepto: " + evaluacion.getValue());

			Map<Integer, String> observaciones = textoPorObservacion.getOrDefault(idEvaluacion, Map.of());
			for (Map.Entry<Integer, String> observacion : observaciones.entrySet()) {
				Integer idObservacion = observacion.getKey();
				String docentes = String.join(", ", docentesPorObservacion
						.getOrDefault(idEvaluacion, Map.of())
						.getOrDefault(idObservacion, List.of()));
				System.out.println("  Observación " + idObservacion + ": " + observacion.getValue());
				System.out.println("    Docentes que la plantean: " + docentes);
			}
		}
	}
	// Consulta si ya existe un docente con un correo específico usando una query nativa.
	private void consultaCorreoDocente(String correo) {
		if (docenteRepository.existeConCorreo(correo) > 0) {
			System.out.println("------------------------------------------------------");
			System.out.println("Ya existe un docente con el correo: " + correo);
			System.out.println("------------------------------------------------------");
		} else {
			System.out.println("------------------------------------------------------");
			System.out.println("No existe un docente con el correo: " + correo);
			System.out.println("------------------------------------------------------");
		}
	}

	// Método para probar: Buscar evaluaciones por fecha de inicio, fecha de fin, para una parte del nombre de un docente ignorando mayúsculas y minúsculas.
	@Transactional(readOnly = true)
	public void buscarEvaluacionesPorFechasYNombreDocente(LocalDate fechaInicio, LocalDate fechaFin, String nombre, String apellido) {
		List<Evaluacion> evaluaciones = evaluacionRepository.findByFechaRegistroConceptoBetweenAndFormatoA_Docente_NombresDocenteContainingIgnoreCaseOrFormatoA_Docente_ApellidosDocenteContainingIgnoreCase(fechaInicio, fechaFin, nombre, apellido);
		System.out.println("\n=== BUSCAR EVALUACIONES POR FECHAS Y NOMBRE DOCENTE ===");
		System.out.println("Fechas: " + fechaInicio + " a " + fechaFin + " | Nombre: " + nombre + " | Apellido: " + apellido);
		for (Evaluacion evaluacion : evaluaciones) {
			System.out.println("Evaluacion ID: " + evaluacion.getIdEvaluacion() + " | Concepto: " + valorSeguro(evaluacion.getConcepto()) + " | Fecha: " + evaluacion.getFechaRegistroConcepto() + " | Docente: " + nombreCompleto(evaluacion.getFormatoA().getDocente()));
		}
		if (evaluaciones.isEmpty()) {
			System.out.println("No se encontraron evaluaciones.");
		}
	}

	// Método para probar: Query SQL nativo - Verificar si existe un formato A con un título dado.
	@Transactional(readOnly = true)
	public void verificarExistenciaFormatoAPorTitulo(String titulo) {
		Integer existe = formatoARepository.existsByTituloNative(titulo);
		System.out.println("\n=== VERIFICAR EXISTENCIA FORMATO A POR TÍTULO (SQL NATIVO) ===");
		System.out.println("Título: " + titulo);
		System.out.println("¿Existe?: " + (existe > 0 ? "Sí" : "No"));
	}

	// Método para probar: Query JPQL - Actualizar el estado de un formato A.
	@Transactional
	public void actualizarEstadoFormatoA(Integer idFormatoA, String nuevoEstado) {
		formatoARepository.updateEstadoByFormatoAId(idFormatoA, nuevoEstado);
		System.out.println("\n=== ACTUALIZAR ESTADO FORMATO A (JPQL) ===");
		System.out.println("ID Formato A: " + idFormatoA + " | Nuevo Estado: " + nuevoEstado);
		System.out.println("Estado actualizado exitosamente.");
	}
}
