import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Hospital hospital = new Hospital();
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String linea = sc.nextLine().trim();
            if (linea.isEmpty()) continue;
            String[] t = linea.split("\\s+");
            String cmd = t[0].toLowerCase();

            if (cmd.equals("salir")) {
                break;
            }
            System.out.println(procesar(hospital, cmd, t));
        }
        sc.close();
    }

    private static String procesar(Hospital h, String cmd, String[] t) {
        try {
            switch (cmd) {
                case "ingresar":
                    return ingresar(h, t);
                case "vacunar":
                    if (t.length != 2) break;
                    return h.vacunar(t[1]);
                case "asignar":
                    if (t.length != 2) break;
                    return h.asignar(t[1]);
                case "medicar":
                    if (t.length != 3) break;
                    return h.medicar(t[1], Double.parseDouble(t[2]));
                case "alta":
                    if (t.length != 2) break;
                    return h.alta(t[1]);
                case "reporte":
                    if (t.length != 1) break;
                    return h.reporte();
                default:
                    break;
            }
        } catch (NumberFormatException e) {
            return "ERROR: datos invalidos";
        }
        return "ERROR: comando desconocido";
    }

    private static String ingresar(Hospital h, String[] t) {
        if (t.length < 2) return "ERROR: comando desconocido";
        String tipo = t[1].toLowerCase();
        try {
            switch (tipo) {
                case "perro": {
                    if (t.length != 7) return "ERROR: datos invalidos";
                    String sn = t[6].toLowerCase();
                    if (!sn.equals("si") && !sn.equals("no")) return "ERROR: datos invalidos";
                    return h.ingresarPerro(t[2], Double.parseDouble(t[3]),
                            Integer.parseInt(t[4]), t[5], sn.equals("si"));
                }
                case "gato":
                    if (t.length != 6) return "ERROR: datos invalidos";
                    return h.ingresarGato(t[2], Double.parseDouble(t[3]),
                            Integer.parseInt(t[4]), Integer.parseInt(t[5]));
                case "ave":
                    if (t.length != 6) return "ERROR: datos invalidos";
                    return h.ingresarAve(t[2], Double.parseDouble(t[3]),
                            Integer.parseInt(t[4]), Double.parseDouble(t[5]));
                default:
                    return "ERROR: comando desconocido";
            }
        } catch (NumberFormatException e) {
            return "ERROR: datos invalidos";
        }
    }
}
