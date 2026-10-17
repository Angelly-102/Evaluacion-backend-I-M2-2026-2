public class Gato extends Paciente {
    private int nivelEstres;

    public Gato(String nombre, double peso, int edad, int nivelEstres) {
        super(nombre, peso, edad, 6);
        setNivelEstres(nivelEstres);
    }

    @Override
    protected double getPesoMaximo() { return 15; }

    @Override
    public String getTipo() { return "Gato"; }

    @Override
    public char zonaRequerida() { return 'A'; }

    public int getNivelEstres() { return nivelEstres; }
    public void setNivelEstres(int nivelEstres) {
        if (nivelEstres < 0 || nivelEstres > 10)
            throw new IllegalArgumentException("estres invalido");
        this.nivelEstres = nivelEstres;
    }

    // R8/R9: estres de 8 o mas

    public boolean tieneEstresAlto() { return nivelEstres >= 8; }

    // R9: sube 2 con tope 10

    public void aumentarEstres() {
        setNivelEstres(Math.min(10, nivelEstres + 2));
    }
}

