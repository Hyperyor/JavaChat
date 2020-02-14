/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.util.Observable;

/**
 *
 * @author alumno
 */
public class LlegadaMensaje extends Observable {

    //metodo que recibe un mensaje
    public void nuevoMensaje(String mensaje) {
        this.setChanged();
        //y se lo rebota a todos los observadores (todos los usuarios conectados)
        notifyObservers(mensaje);

    }

}
