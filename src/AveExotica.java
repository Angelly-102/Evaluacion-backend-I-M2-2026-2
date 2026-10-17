public class AveExotica extends Paciente {
    private double envergadura; // cm

    public AveExotica(String nombre, double peso, int edad, double envergadura) {
        super(nombre, peso, edad, 3);
        setEnvergadura(envergadura);
    }

    @Override
    protected double getPesoMaximo() { return 15; }

    @Override
    public String getTipo() { return "Ave"; }

    @Override
    public char zonaRequerida() { return 'B'; }

    // R4: ave con envergadura > 60 cm necesita minimo M

    @Override
    public char tamanoRequerido() {
        char t = super.tamanoRequerido();
        if (envergadura > 60 && t == 'S') return 'M';
        return t;
    }

    public double getEnvergadura() { return envergadura; }
    public void setEnvergadura(double envergadura) {
        if (!(envergadura > 0))
            throw new IllegalArgumentException("envergadura invalida");
        this.envergadura = envergadura;
    }
}

