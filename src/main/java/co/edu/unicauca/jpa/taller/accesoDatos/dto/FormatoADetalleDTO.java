package co.edu.unicauca.jpa.taller.accesoDatos.dto;

public record FormatoADetalleDTO(
        Integer formatoAId,
        String formatoTitulo,
        String docenteDirectorNombres,
        String docenteDirectorApellidos,
        String estadoActual,
        Integer evaluacionId,
        String evaluacionConcepto,
        Integer observacionId,
        String observacionTexto,
        String docenteObservacionNombres,
        String docenteObservacionApellidos) {

    public String nombreCompletoDirector() {
        return unirNombre(docenteDirectorNombres, docenteDirectorApellidos);
    }

    public String nombreCompletoDocenteObservacion() {
        return unirNombre(docenteObservacionNombres, docenteObservacionApellidos);
    }

    private static String unirNombre(String nombres, String apellidos) {
        String n = nombres == null ? "" : nombres.trim();
        String a = apellidos == null ? "" : apellidos.trim();
        String combinado = (n + " " + a).trim();
        return combinado;
    }
}
