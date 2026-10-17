public class Jaula {
    private int numero;
    private char tamano; // S, M, L
    private char zona;   // A, B
    private Paciente ocupante;

    public Jaula(int numero, char tamano, char zona) {
        setNumero(numero);
        setTamano(tamano);
        setZona(zona);
        this.ocupante = null;
    }

    public int getNumero() { return numero; }
    public void setNumero(int numero) {
        if (numero <= 0) throw new IllegalArgumentException("numero invalido");
        this.numero = numero;
    }

    public char getTamano() { return tamano; }
    public void setTamano(char tamano) {
        if (tamano != 'S' && tamano != 'M' && tamano != 'L')
            throw new IllegalArgumentException("tamano invalido");
        this.tamano = tamano;
    }

    public char getZona() { return zona; }
    public void setZona(char zona) {
        if (zona != 'A' && zona != 'B')
            throw new IllegalArgumentException("zona invalida");
        this.zona = zona;
    }

    public Paciente getOcupante() { return ocupante; }

    public boolean estaLibre() { return ocupante == null; }

    public boolean esCompatible(Paciente p) {
        return tamano == p.tamanoRequerido() && zona == p.zonaRequerida();
    }

    // Enlaza jaula y paciente en ambos sentidos (R6)

    public void asignar(Paciente p) {
        this.ocupante = p;
        p.setJaula(this);
    }

    public void liberar() {
        if (ocupante != null) {
            ocupante.setJaula(null);
        }
        this.ocupante = null;
    }
}

