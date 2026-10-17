import java.util.Locale;

public class Hospital {
    private static final int MAX_PACIENTES = 10;

    private Paciente[] pacientes;
    private int contador;
    private Jaula[] jaulas;

    public Hospital() {
        pacientes = new Paciente[MAX_PACIENTES];
        contador = 0;
        jaulas = new Jaula[6];
        jaulas[0] = new Jaula(1, 'S', 'A');
        jaulas[1] = new Jaula(2, 'M', 'A');
        jaulas[2] = new Jaula(3, 'M', 'A');
        jaulas[3] = new Jaula(4, 'L', 'A');
        jaulas[4] = new Jaula(5, 'S', 'B');
        jaulas[5] = new Jaula(6, 'M', 'B');
        cargarDatosIniciales();
    }

    public void cargarDatosIniciales() {
        pacientes[contador++] = new Perro("Rocky", 30.0, 4, "labrador", true);
        pacientes[contador++] = new Gato("Misifu", 4.0, 3, 0);
        pacientes[contador++] = new AveExotica("Kiwi", 0.8, 2, 70);
    }

    // ---------- Utilidades ----------

    private int buscar(String nombre) {
        for (int i = 0; i < contador; i++) {
            if (pacientes[i].getNombre().equalsIgnoreCase(nombre)) return i;
        }
        return -1;
    }

    private static String num(double d) {
        if (d == Math.rint(d) && Math.abs(d) < 1e15) return String.valueOf((long) d);
        return String.valueOf(d);
    }

    // ---------- Ingresos (R1, R2, R3) ----------

    public String ingresarPerro(String nombre, double peso, int edad, String raza, boolean vacunado) {
        Perro p;
        try {
            p = new Perro(nombre, peso, edad, raza, vacunado);
        } catch (IllegalArgumentException e) {
            return "ERROR: datos invalidos";
        }
        return registrar(p);
    }

    public String ingresarGato(String nombre, double peso, int edad, int estres) {
        Gato g;
        try {
            g = new Gato(nombre, peso, edad, estres);
        } catch (IllegalArgumentException e) {
            return "ERROR: datos invalidos";
        }
        return registrar(g);
    }

    public String ingresarAve(String nombre, double peso, int edad, double envergadura) {
        AveExotica a;
        try {
            a = new AveExotica(nombre, peso, edad, envergadura);
        } catch (IllegalArgumentException e) {
            return "ERROR: datos invalidos";
        }
        return registrar(a);
    }

    // Orden R2: datos (ya validados), duplicado, lleno

    private String registrar(Paciente p) {
        if (buscar(p.getNombre()) != -1) return "ERROR: nombre duplicado";
        if (contador >= MAX_PACIENTES) return "ERROR: hospital lleno";
        pacientes[contador++] = p;
        if (Paciente.CUARENTENA.equals(p.getEstado())) {
            return "OK: " + p.getNombre() + " ingresado en CUARENTENA";
        }
        return "OK: " + p.getNombre() + " ingresado";
    }

    // ---------- Vacunar ----------

    public String vacunar(String nombre) {
        int i = buscar(nombre);
        if (i == -1) return "ERROR: paciente no encontrado";
        if (!(pacientes[i] instanceof Perro)) return "ERROR: solo aplica a perros";
        Perro perro = (Perro) pacientes[i];
        if (perro.isVacunado()) return "ERROR: ya vacunado";
        perro.vacunar();
        return "OK: " + perro.getNombre() + " vacunado (pasa a INGRESADO)";
    }

    // ---------- Asignar jaula (R6) ----------

    public String asignar(String nombre) {
        int i = buscar(nombre);
        if (i == -1) return "ERROR: paciente no encontrado";
        Paciente p = pacientes[i];
        if (Paciente.CUARENTENA.equals(p.getEstado())) return "ERROR: paciente en cuarentena";
        if (p.getJaula() != null) return "ERROR: paciente ya tiene jaula";
        for (int j = 0; j < jaulas.length; j++) {
            if (jaulas[j].estaLibre() && jaulas[j].esCompatible(p)) {
                jaulas[j].asignar(p);
                return "OK: " + p.getNombre() + " asignado a jaula " + jaulas[j].getNumero();
            }
        }
        return "ERROR: no hay jaula compatible";
    }

    // ---------- Medicar (R7, R8, R9) ----------

    public String medicar(String nombre, double mg) {
        int i = buscar(nombre);
        if (i == -1) return "ERROR: paciente no encontrado";
        if (!(mg > 0)) return "ERROR: datos invalidos";
        Paciente p = pacientes[i];
        if (Paciente.CUARENTENA.equals(p.getEstado())) return "ERROR: paciente en cuarentena";
        if (p.getJaula() == null) return "ERROR: paciente sin jaula";
        if (p instanceof Gato && ((Gato) p).tieneEstresAlto()) return "ERROR: gato con estres alto";
        if (mg > p.dosisMaxima()) return "ERROR: dosis excede el maximo";
        if (p.getDosisAcumulada() + mg > p.topeAcumulado()) return "ERROR: tope acumulado superado";

        p.setDosisAcumulada(p.getDosisAcumulada() + mg);
        p.setEstado(Paciente.EN_TRATAMIENTO);
        if (p instanceof Gato) ((Gato) p).aumentarEstres();
        return "OK: " + p.getNombre() + " medicado con " + num(mg) + " mg";
    }

    // ---------- Alta (R10) ----------

    public String alta(String nombre) {
        int i = buscar(nombre);
        if (i == -1) return "ERROR: paciente no encontrado";
        Paciente p = pacientes[i];
        if (!Paciente.EN_TRATAMIENTO.equals(p.getEstado())) return "ERROR: paciente no apto para alta";
        if (p.getJaula() != null) p.getJaula().liberar();
        // compactar el vector
        for (int k = i; k < contador - 1; k++) {
            pacientes[k] = pacientes[k + 1];
        }
        pacientes[contador - 1] = null;
        contador--;
        return "OK: " + p.getNombre() + " dado de alta";
    }

    // ---------- Reporte ----------

    public String reporte() {
        StringBuilder sb = new StringBuilder();
        sb.append("--- REPORTE ---\n");
        sb.append("Pacientes: ").append(contador).append("/").append(MAX_PACIENTES).append("\n");
        for (int i = 0; i < contador; i++) {
            Paciente p = pacientes[i];
            String j = (p.getJaula() == null) ? "-" : String.valueOf(p.getJaula().getNumero());
            sb.append(p.getNombre()).append(" | ")
                    .append(p.getTipo()).append(" | ")
                    .append(String.format(Locale.US, "%.1f", p.getPeso())).append(" kg | ")
                    .append(p.getEstado()).append(" | jaula: ")
                    .append(j).append("\n");
        }
        int libres = 0;
        for (int j = 0; j < jaulas.length; j++) {
            if (jaulas[j].estaLibre()) libres++;
        }
        sb.append("Jaulas libres: ").append(libres);
        return sb.toString();
    }
}
