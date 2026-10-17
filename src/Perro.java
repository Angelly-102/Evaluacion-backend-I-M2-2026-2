public class Perro extends Paciente {
        private String raza;
        private boolean vacunado;

        public Perro(String nombre, double peso, int edad, String raza, boolean vacunado) {
            super(nombre, peso, edad, 10);
            setRaza(raza);
            this.vacunado = vacunado;
            if (!vacunado) {              // R3
                setEstado(CUARENTENA);
            }
        }

        @Override
        protected double getPesoMaximo() { return 80; }

        @Override
        public String getTipo() { return "Perro"; }

        @Override
        public char zonaRequerida() { return 'A'; }

        public String getRaza() { return raza; }
        public void setRaza(String raza) {
            if (raza == null || raza.trim().isEmpty())
                throw new IllegalArgumentException("raza invalida");
            this.raza = raza.trim();
        }

        public boolean isVacunado() { return vacunado; }

        // Vacuna al perro; si estaba en cuarentena pasa a INGRESADO

        public void vacunar() {
            this.vacunado = true;
            if (CUARENTENA.equals(getEstado())) {
                setEstado(INGRESADO);
            }
        }
    }

