public abstract class Paciente {
    public static final String
    INGRESADO="INGRESADO";
    public static final String
    CUARENTENA="CUARENTENA";
    public static final String
    EN_TRATAMIENTO="EN_TRATAMIENTO";

    private String nombre;
    private double peso;
    private int edad;
    private String estado;
    private final double factorDosis;
    private double dosisAcumulada;
    private Jaula jaula;


    protected Paciente(String nombre,double peso,int edad,double factorDosis) {
        this.factorDosis = factorDosis;
        setNombre(nombre);
        setPeso(peso);
        setEdad(edad);
        this.estado= INGRESADO;
        this.dosisAcumulada=0;
        this.jaula=null;
    }


    // Peso maximo permitido segun la especie (R1)

    protected abstract double getPesoMaximo();
    public abstract String getTipo();
    // Zona de jaula que necesita la especie (R5)
    public abstract char zonaRequerida();

    // Tamano de jaula segun peso (R4): <5 S, 5..25 M, >25 L

    public char tamanoRequerido() {
        if (peso < 5) return 'S';
        if (peso <= 25) return 'M';
        return 'L';
    }

    // R7

    public double dosisMaxima() {
        return peso * factorDosis;
    }

    public double topeAcumulado() {
        return 3 * dosisMaxima();
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("nombre invalido");
        this.nombre = nombre.trim();
    }

    public double getPeso() { return peso; }
    public void setPeso(double peso) {
        if (!(peso > 0 && peso <= getPesoMaximo()))
            throw new IllegalArgumentException("peso invalido");
        this.peso = peso;
    }

    public int getEdad() { return edad; }
    public void setEdad(int edad) {
        if (edad < 0 || edad > 30)
            throw new IllegalArgumentException("edad invalida");
        this.edad = edad;
    }

    public String getEstado() { return estado; }
    public void setEstado(String estado) {
        if (!INGRESADO.equals(estado) && !CUARENTENA.equals(estado) && !EN_TRATAMIENTO.equals(estado))
            throw new IllegalArgumentException("estado invalido");
        this.estado = estado;
    }

    public double getFactorDosis() { return factorDosis; }

    public double getDosisAcumulada() { return dosisAcumulada; }
    public void setDosisAcumulada(double dosisAcumulada) {
        if (dosisAcumulada < 0)
            throw new IllegalArgumentException("dosis invalida");
        this.dosisAcumulada = dosisAcumulada;
    }

    public Jaula getJaula() { return jaula; }
    public void setJaula(Jaula jaula) { this.jaula = jaula; }
}


