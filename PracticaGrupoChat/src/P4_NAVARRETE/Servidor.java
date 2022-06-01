package P4_NAVARRETE;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

//JOSE MIGUEL NAVARRETE VERA

public class Servidor {

    public static void main(String[] args) {

        //ArrayList para almacenar los sockets de los clientes que se van conectando al servidor
        ArrayList<Socket> listaClientes = new ArrayList<>();
        int contador = 0;

        try {
            //Se realiza la conexión con el cliente
            System.out.println("SERVIDOR");
            System.out.println("Esperando conexión de un cliente...");
            ServerSocket servidor = new ServerSocket(12345);
            while (true) {
                //Se realiza una conexión por cada socket recibido desde el cliente y se añade a la lista de sockets 'listaClientes'
                Socket cliente = servidor.accept();
                listaClientes.add(cliente);

                //Se muestra en pantalla la cantidad de clientes que se van conectando utilizando un contador para contabilizarlos
                contador++;
                System.out.println("Cliente Conectado! Participantes totales en la Sala: " + contador);

                //Se cre un objeto de tipo 'hiloServidor' donde se pasa por parámetro el socket del cliente, la listaClientes y el servidor
                HiloServidor hiloServidor = new HiloServidor(cliente, listaClientes, servidor);
                Thread socket = new Thread(hiloServidor);
                socket.start();
            }
        } catch (IOException e) {
            //Utilizamos un mensaje de aviso personalizado el lugar de obtener la excepción en un string
            System.out.println("Cerrando el Servidor...");
        }
    }
}

