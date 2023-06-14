package com.example.sockets.IO;

import com.example.sockets.model.PaqueteEnvio;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente {
    private Socket miSocket;
    private String ip;
    private ObjectOutputStream flujo_salida;

    public Cliente(String ip){
        this.ip = ip;
        try {
            miSocket = new Socket(this.ip, 9999);
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void EnviaTexto(PaqueteEnvio paqueteEnvio){
        try {
            if(miSocket != null) {
                flujo_salida = new ObjectOutputStream(miSocket.getOutputStream());

                flujo_salida.writeObject(paqueteEnvio);

                flujo_salida.close();
                miSocket.close();

            }
        }catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}