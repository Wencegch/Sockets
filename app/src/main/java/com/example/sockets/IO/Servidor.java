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

    public void MensajeEntrante(){
        try{
            if(serverSocket != null) {
                System.out.println("Esperando conexión");
                miSocket = serverSocket.accept();
                System.out.println("Cliente en línea");

                flujo_entrada = new ObjectInputStream(miSocket.getInputStream());
                paqueteEnvio = (PaqueteEnvio) flujo_entrada.readObject();

                System.out.println("Fin de la conexión");
                flujo_entrada.close();
                serverSocket.close();
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.printf(e.getMessage());
        }
    }

    public PaqueteEnvio getMensaje(){
        return paqueteEnvio;
    }
    public void RecibirMensaje(){
        try {
            if (serverSocket!=null) {
                miSocket = serverSocket.accept();
                flujo_entrada = new ObjectInputStream(miSocket.getInputStream());
                paqueteEnvio = (PaqueteEnvio) flujo_entrada.readObject();
                Log.d("SERVIDOR", "Mensaje obtenido: " + paqueteEnvio.getMensaje());
                flujo_entrada.close();
                miSocket.close();
            }
        } catch (IOException e) {
            Log.d("SERVIDOR", "Fin de la conexión.");
        } catch (ClassNotFoundException e) {
            Log.d("SERVIDOR", "No se pudo realizar el casting en la lectura de Mensaje.");
        }
    }
}
