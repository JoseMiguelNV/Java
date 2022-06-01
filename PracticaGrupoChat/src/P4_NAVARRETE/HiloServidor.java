package P4_NAVARRETE;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

//JOSE MIGUEL NAVARRETE VERA

public class HiloServidor implements Runnable{

    Socket cliente;
    ArrayList<Socket> listaClientes;
    ServerSocket servidor;

    //Constructor de la clase que recibe por parámetro desde el servidor, el socket del cliente y la lista de los clientes almacenados
    public HiloServidor(Socket cliente, ArrayList<Socket> listaHilos, ServerSocket servidor) {
        this.cliente = cliente;
        this.listaClientes = listaHilos;
        this.servidor = servidor;
    }

    @Override
    public void run() {
        String mensaje;
        int clientesConectados = 0;
        try {
            //Flujo de entrada de los mensajes enviados por los clientes
            InputStream entrada = cliente.getInputStream();
            DataInputStream flujoEntrada = new DataInputStream(entrada);
            do {
                mensaje = flujoEntrada.readUTF();
                for(Socket socket : listaClientes) {
                    //Se enviarán los mensajes a todos los sockets de la listaCliente que no sean el socket del cliente que realiza el envío del mensaje
                    if (socket != cliente) {
                        //Flujo de salida del mensaje recibido en el flujo de entrada
                        OutputStream salida = socket.getOutputStream();
                        DataOutputStream flujoSalida = new DataOutputStream(salida);
                        flujoSalida.writeUTF(mensaje);
                    }
                }
                //Bucle para obtener el número de clientes almacenados en la listaClientes
                for(int i = 0; i < listaClientes.size(); i++) {
                    clientesConectados = i;
                }
            } while (!mensaje.equals(("fin")));
        } catch (IOException e) {
            //En lugar de obtener la excepción, mostramos el mensaje de desconexión del cliente en el servidor, y descontamos al número de clientes conectados
            System.out.println("Cliente Desconectado! Número de clientes en linea: " + clientesConectados);
            //EL SERVIDOR ELIMINA EL CLIENTE DE LA LISTA UNA VEZ SE HA PRODUCIDO LA EXCEPCIÓN Y POR TANTO SABEMOS QUE EL CLIENTE SE HA DESCONECTADO
            listaClientes.remove(cliente);
            //Muestra por pantalla cuando la sala está vacía y cierra el servidor
            if(clientesConectados == 0){
                System.out.println("La sala de chat está vacía!");
                try {
                    //Se cierra el servidor cuando ya no hay nadie en la sala
                    servidor.close();
                } catch (IOException ex) {
                    //Obtenemos el mensaje de la excepción para mostrarla por pantalla
                    System.out.println(ex.getMessage());
                }
            }
        }
    }
}
