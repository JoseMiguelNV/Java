package P4_NAVARRETE;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

//JOSE MIGUEL NAVARRETE VERA

public class Cliente {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try{
            //Se realiza la conexión con el servidor
            Socket conexion = new Socket("127.0.0.1", 12345);
            System.out.print("Introduce un nombre de usuario: ");
            String usuario = sc.nextLine();
            System.out.println(usuario + " se ha conectado al CHAT! (escribe 'fin' para salir)");
            System.out.println("Usuario -> " + usuario);

            //Se envia el socket y el usuario al 'hiloEnviar' para mandar el mensaje junto al nombre de usuario
            Thread enviar = new Thread(new HiloEnviar(conexion, usuario));
            enviar.start();

            //Se ejecuta el hiloRecibir para recibir los mensajes que se van enviando por los distintos clientes
            Thread recibir = new Thread(new HiloRecibir(conexion));
            recibir.start();

        } catch (IOException e) {
            //Obtenemos la excepción en un string
            System.out.println(e.getMessage());
        }
    }
}
