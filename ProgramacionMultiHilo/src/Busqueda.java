import java.io.*;
import java.util.ArrayList;


public class Busqueda implements Runnable {

    private String libro;
    private String palabra;
    private int id;
    private int idActual = 0;

    public int numeroLinea = 0;
    public String contenidoLineas;
    public int contadorDeLineas = 0;


    public Busqueda(String libro, String palabra, int id){
        this.libro = libro;
        this.palabra = palabra;
        this.id = id;
    }

    public Busqueda() {

    }

    public int getIdActual() {
        return idActual;
    }

    public void setIdActual(int idActual) {
        this.idActual = idActual;
    }

    public int getNumeroLinea() {
        return numeroLinea;
    }

    public void setNumeroLinea(int numeroLinea) {
        this.numeroLinea = numeroLinea;
    }

    public String getContenidoLinea() {
        return contenidoLineas;
    }

    public void setContenidoLinea(String contenidoLinea) {
        this.contenidoLineas = contenidoLinea;
    }

    public int getContadorDeLineas() {
        return contadorDeLineas;
    }

    public void setContadorDeLineas(int contadorDeLineas) {
        this.contadorDeLineas = contadorDeLineas;
    }

    public String getLibro() {
        return libro;
    }

    public void setLibro(String libro) {
        this.libro = libro;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        Busqueda busqueda = new Busqueda();
        try {
            BufferedReader br = new BufferedReader(new FileReader(".\\Libros\\" + libro));
            String linea = br.readLine();
            while(linea != null){
                numeroLinea ++;
                if(linea.contains(palabra)){
                    Main.cabecera(linea,id,libro, palabra);
                    contadorDeLineas ++;
                    String numero = String.valueOf(numeroLinea);
                    contenidoLineas = "identificador" + id + " - " + numero + "  " + linea;
                    Main.lineas.add(contenidoLineas);
                    busqueda.setId(id);
                    busqueda.setLibro(libro);
                    busqueda.setPalabra(palabra);
                    busqueda.setNumeroLinea(contadorDeLineas);
                    Main.busquedas.add(busqueda);
                }
                linea = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
