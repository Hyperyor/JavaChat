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
public class LlegadaMensaje extends Observable{
    
    public void nuevoMensaje(String mensaje)
    {
        this.setChanged();
        notifyObservers(mensaje);
    }
    
}
