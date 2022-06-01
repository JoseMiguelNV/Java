package P4_NAVARRETE;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

//JOSE MIGUEL NAVARRETE VERA

public class HiloEnviar implements Runnable {

    Socket hiloSalida;
    String usuario;
    Scanner sc = new Scanner(System.in);

    //Constructor de la clase que recibe por parámetro desde el cliente, el socket y el nombre del cliente
    public HiloEnviar(Socket hiloSalida, String usuario) {
        this.hiloSalida = hiloSalida;
        this.usuario = usuario;
    }

    @Override
    public void run() {
        String linea;
        try {
            //El cliente envía mensajes hasta que escribe la palabra "fin"
            do {
                //Flujo de salida del mensaje enviado
                OutputStream salida = hiloSalida.getOutputStream();
                DataOutputStream flujoSalida = new DataOutputStream(salida);
                linea = sc.nextLine();
                flujoSalida.writeUTF(usuario + ": " + linea);
            } while (!linea.equals("fin"));

            //Se cierra el socket de enviar mensajes después de finalizar el cliente con la palabra "fin"
            hiloSalida.close();

        } catch (IOException e) {
            //Obtenemos la excepción en un string
            System.out.println(e.getMessage());
        }
    }
}
