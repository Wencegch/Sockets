package com.example.sockets.model;

import java.io.Serializable;

public class PaqueteEnvio implements Serializable {
    private String nombre;
    private String ip;
    private String mensaje;

    /**
     *
     * @param nombre Nombre de usuario
     * @param ip Ip de quien va a recibir el mensaje
     * @param mensaje Mensaje se manda al usuario de la Ip
     */
    public PaqueteEnvio(String nombre, String ip, String mensaje) {
        this.nombre = nombre;
        this.ip = ip;
        this.mensaje = mensaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
