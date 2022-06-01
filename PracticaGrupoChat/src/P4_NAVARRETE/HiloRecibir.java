package P4_NAVARRETE;

import java.io.*;
import java.net.Socket;


//JOSE MIGUEL NAVARRETE VERA

public class HiloRecibir implements Runnable{

    Socket hiloEntrada;

    //Constructor de la clase que recibe por parámetro desde el cliente, el socket del cliente
    public HiloRecibir(Socket hiloEntrada) {
        this.hiloEntrada = hiloEntrada;
    }

    @Override
    public void run() {
        String linea;
        try {
            //Bucle infinito para que no dejen de recibir mensajes los clientes mientras se estén enviando
            while (true) {
                //Flujo de entrada de los mensajes que se reciben
                InputStream entrada = hiloEntrada.getInputStream();
                DataInputStream flujoEntrada = new DataInputStream(entrada);
                linea = flujoEntrada.readUTF();
                System.out.println(linea);
            }
        } catch (IOException e) {
            //Dejamos la excepción para recibirla en caso de error y evitar que nos muestre el error al cerrar el socket del cliente
            e.getMessage();
            //Y mostramos por pantalla el aviso de cliente desconectado
            System.out.println("Cliente desconectado...");
        }
    }
}
