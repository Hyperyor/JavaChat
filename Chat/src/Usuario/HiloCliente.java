package Usuario;

import Modelo.Mensaje;
import Modelo.User;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HiloCliente extends Thread {

    private Socket socket;
    private ObjectOutputStream flujoSalida;
    private ObjectInputStream flujoEntrada;
    private boolean escuchando;
    private Cliente padre;

    public HiloCliente(Cliente c, Socket s) throws IOException {
        this.socket = s;

        //guardamos una referencia a la ventana principal
        //para pasarle datos posteriormente
        padre = c;

        //creamos el flujo de salida
        flujoSalida = new ObjectOutputStream(socket.getOutputStream());
        //creamos flujo de entrada
        flujoEntrada = new ObjectInputStream(socket.getInputStream());

        //mandamos los datos del cliente al servidor
        flujoSalida.writeObject(padre.getUser());// enviando el objeto

        //levantamos la bandera del bucle infinito
        escuchando = true;
    }

    public void run() {

        try {

            //bucle infinito
            //esta escuchando constantemente al servidor
            while (escuchando) {
                //tomamos los datos de entrada
                Object obj = flujoEntrada.readObject();

                //si recibimos un String
                if (obj instanceof String) {
                    //hacemos conversion
                    String men = obj.toString();
                    //le pasamos a la ventana principal el mensaje
                    //que nos envia el servidor
                    padre.addMessage(men); //System.out.println(men);
                }

                //si recibimos un ArrayList (nombres de los usuarios conectados)
                if (obj instanceof ArrayList) {

                    //hacemos conversion
                    ArrayList n = (ArrayList) obj;

                    //se lo pasamos a la ventana principal
                    //para mostrarlos
                    padre.mostrarUsuarios(n);

                }

            }

            //si llegamos a este punto quiere decir que hemos
            //bajado la bandera "escuchando", es decir,
            //hemos recibido una señal para desconectarnos del servidor
            System.out.println("Desconexion del usuario");

            //cerramos los flujos
            flujoEntrada.close();
            flujoSalida.close();

            //cerramos la conexion
            socket.close();
        } catch (Exception e) {

        }
    }

    public synchronized void desconectar() {
        System.out.println("Desconectamos");
        //bajamos la bandera que del bucle infinito,
        //es decir, terminamos la ejecucion del hilo
        escuchando = false;

        try {
            //enviamos una señal al servidor indicando
            //que nos desconectamos enviandole un String
            flujoSalida.writeObject("salimos");
        } catch (Exception ex) {

        }

    }

    public synchronized void enviar(String user, String msg) {
        //creamos un objeto de la clase mensaje con los datos recibidos
        //de la ventana principal
        Mensaje j = new Mensaje(user, msg);
        try {
            //se lo enviamos al servidor
            flujoSalida.writeObject(j);
        } catch (IOException ex) {

        }

    }
}
