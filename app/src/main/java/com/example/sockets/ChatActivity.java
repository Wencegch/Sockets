package com.example.sockets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sockets.IO.Cliente;
import com.example.sockets.IO.Servidor;
import com.example.sockets.adapter.RecyclerAdapter;
import com.example.sockets.model.PaqueteEnvio;

import java.io.IOException;
import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private ArrayList<PaqueteEnvio> listaPaqueteEnvio = new ArrayList<>();
    private Servidor servidor;
    private Cliente cliente;
    private Thread hiloServidor, hiloMensaje, hiloCliente;

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private TextView txtMensaje;
    private PaqueteEnvio paqueteEnvio;

    private String txtNombreChat;
    private String mensaje;
    private String txtIpChat;

    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        btnEnviar = findViewById(R.id.btnEnviar);
        txtMensaje = findViewById(R.id.txtMensaje);

        /*Se crean la instancia del recyclerAdapter, y se le pasa a este último la lista como parámetro de inicialización.*/
        recyclerView = findViewById(R.id.recyclerView);
        recyclerAdapter = new RecyclerAdapter(listaPaqueteEnvio);

        Intent i = getIntent();
        if (i != null) {
            txtNombreChat = i.getExtras().getString("Nombre");
            txtIpChat = i.getExtras().getString("Ip");
        }
        /*La clase LayoutManager tiene la responsabilidad de manejar la disposición de los elementos
         * en una lista dentro de un RecyclerView. Hay varias opciones disponibles, como GridLayoutManager
         * que los coloca en una vista de rejilla. En este caso se ha utilizado el modo Linear para una
         * disposición básica, con un elemento debajo de otro.*/
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        /*Finalmente, solo necesitamos agregar los elementos creados previamente a la vista principal (RecyclerView)
         * utilizando sus respectivos métodos.*/
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        hiloServidor = new Thread(new Runnable() {
            @Override
            public void run() {
                if (servidor == null) {
                    servidor = new Servidor();
                }
            }
        });
        hiloServidor.start();

        try {
            hiloServidor.join();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

        hiloMensaje = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!servidor.online) {
                    servidor.RecibirMensaje();
                    paqueteEnvio = servidor.getPaqueteEnvio();
                    if (paqueteEnvio != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listaPaqueteEnvio.add(paqueteEnvio);
                                recyclerAdapter.notifyItemInserted(listaPaqueteEnvio.size() - 1);
                                recyclerAdapter.notifyItemRangeChanged(listaPaqueteEnvio.size() - 1, listaPaqueteEnvio.size());
                                recyclerView.scrollToPosition(listaPaqueteEnvio.size() - 1);
                            }
                        });
                    }
                }
            }
        });
        hiloMensaje.start();

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mensaje = txtMensaje.getText().toString().trim();
                if (!mensaje.equals("")) {
                    txtMensaje.setText("");

                    hiloCliente = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            cliente = new Cliente(txtIpChat);
                            cliente.EnviaTexto(new PaqueteEnvio(txtNombreChat, txtIpChat, mensaje));
                        }
                    });
                    hiloCliente.start();

                    // Agregar el mensaje enviado por ti mismo a la lista y actualizar el adaptador
                    PaqueteEnvio paqueteEnvioPropio = new PaqueteEnvio(txtNombreChat, txtIpChat, mensaje);
                    listaPaqueteEnvio.add(paqueteEnvioPropio);
                    recyclerAdapter.notifyItemInserted(listaPaqueteEnvio.size() - 1);
                    recyclerAdapter.notifyItemRangeChanged(listaPaqueteEnvio.size() - 1, listaPaqueteEnvio.size());
                    recyclerView.scrollToPosition(listaPaqueteEnvio.size() - 1);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        servidor.online = true;

        if (hiloMensaje.isAlive()) {
            try {
                hiloMensaje.interrupt();
                servidor.serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
