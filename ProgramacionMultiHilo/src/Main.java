import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static ArrayList<Thread> listaHilos = new ArrayList<>();
    static ArrayList<Busqueda> busquedas = new ArrayList<>();
    static ArrayList<Busqueda> listaBusqueda = new ArrayList<>();
    static ArrayList<String> lineas = new ArrayList<>();
    private static int id = 0;
    public static int idActual = 0;


    public static ArrayList<String> getLineas() {
        return lineas;
    }

    public static void main(String[] args) {
        mostrarMenu();
    }

    public static void mostrarMenu(){

        System.out.println("----------------------------------------------------");
        System.out.println("--------------------- M E N Ú ----------------------");
        System.out.println("----------------------------------------------------");
        System.out.println();
        System.out.println("1) lanzar búsqueda ");
        System.out.println("2) Mostrar estado de búsquedas ");
        System.out.println("3) Salir ");
        System.out.println();
        System.out.print("Elige una opción: ");
        String opcion = sc.nextLine();
        do {
            if (opcion.equals("1")) {
                lanzarBusqueda();
                mostrarMenu();
            } else if (opcion.equals("2")) {
                mostrarEstadoBusqueda();
                mostrarMenu();
            } else {
                return;
            }
        }while (opcion.equals("3"));
    }

    public static void lanzarBusqueda(){
        int contador = 0;
        do{
            id++;
            Busqueda busqueda = new Busqueda();
            busqueda.setId(id);
            System.out.println("Introduce el título de un libro: ");
            busqueda.setLibro(sc.nextLine());
            System.out.println("Introduce la palabra a buscar: ");
            busqueda.setPalabra(sc.nextLine());
            Thread t = new Thread(busqueda);
            listaBusqueda.add(busqueda);
            listaHilos.add(t);
            System.out.println("¿Desea introducir una nueva búsqueda? ('no' para Salir)");

        }while(sc.nextLine().equals("si"));
        for(Thread hilo: listaHilos){
            if(hilo.getState() == Thread.State.NEW){
                hilo.start();
                System.out.println("Lanzando la búsqueda numero " + listaBusqueda.get(contador).getId() + " ...");
                contador++;
            }
        }
    }

    public static void mostrarEstadoBusqueda(){
        String estadoHilo = null;
        String opcion = "";
        for(int i = 0; i < listaHilos.size(); i++){
            if(listaHilos.get(i).getState() == Thread.State.TERMINATED){
                estadoHilo = "FINALIZADO";
            }else{
                estadoHilo = "BUSCANDO";
            }
            int contador = contadorLineas(String.valueOf(listaBusqueda.get(i).getId()));
            System.out.println(listaBusqueda.get(i).getId() + "  " + listaBusqueda.get(i).getLibro() + "  " +
                    listaBusqueda.get(i).getPalabra() + "  " + contador  +  "  " + estadoHilo);
        }
        while (!opcion.equals("0")) {
            System.out.println("Indica el número de búsqueda sobre la que quieres ver los resultados: ('0' para Salir)");
            opcion = sc.nextLine();
            mostrarBusqueda(opcion);
        }
    }

    public static int contadorLineas(String opcion){
        int contador = 0;
        opcion = "identificador" + opcion;
        for(int i = 0; i < lineas.size(); i++){
            if(lineas.get(i).contains(opcion)){
                contador ++;
            }
        }return contador;
    }

    public static void mostrarBusqueda(String opcion){
        opcion = "identificador" + opcion;
        for(int i = 0; i < lineas.size(); i++){
            if(lineas.get(i).contains(opcion)){
                System.out.println(lineas.get(i));
            }
        }
    }

    public static synchronized void cabecera(String linea, int id, String libro, String palabra){
        File fichero = new File("Busquedas.txt");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(".\\Ficheros\\" + fichero, true));
            if (idActual == id){
                bw.write(linea);
                bw.newLine();
            }else{
                bw.write("-------------------------------------------------------------------------");
                bw.newLine();
                bw.write("Id de búsqueda: " + id + "   Archivo: " + libro + "   Palabra: " + palabra);
                bw.newLine();
                bw.write("-------------------------------------------------------------------------");
                bw.newLine();
                bw.write(linea);
                bw.newLine();
            }
            idActual = id;
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
