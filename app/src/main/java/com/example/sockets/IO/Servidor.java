package com.example.sockets.IO;

import android.util.Log;

import com.example.sockets.model.PaqueteEnvio;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor{
    public ServerSocket serverSocket;
    private Socket miSocket;
    private PaqueteEnvio paqueteEnvio;
    private ObjectInputStream flujo_entrada;
    public boolean online = false;

    public Servidor(){
        try {
            serverSocket = new ServerSocket(9999);
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public PaqueteEnvio getPaqueteEnvio() {
        return paqueteEnvio;
    }

    public PaqueteEnvio getMensaje(){
        return paqueteEnvio;
    }

    /**
     * Método encargado de recibir un mensaje enviado a través de un socket en un servidor
     */
    public void RecibirMensaje(){
        try {
            if (serverSocket!=null) {
                //aceptamos todas las conexiones
                miSocket = serverSocket.accept();
                //Creamos un flujo de entrada de objetos
                flujo_entrada = new ObjectInputStream(miSocket.getInputStream());
                //Se lee un objeto del flujo de entrada y se hace casting
                paqueteEnvio = (PaqueteEnvio) flujo_entrada.readObject();
                //Se cierra el flujo de entrada y el socket
                flujo_entrada.close();
                miSocket.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
