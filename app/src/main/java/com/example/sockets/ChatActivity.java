package com.example.sockets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        recyclerView = findViewById(R.id.recyclerView);

        Intent i = getIntent();
        if (i != null){
            txtNombreChat = i.getExtras().getString("Nombre");
            txtIpChat = i.getExtras().getString("Ip");
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerAdapter = new RecyclerAdapter(listaPaqueteEnvio);
        recyclerView.setAdapter(recyclerAdapter);

        hiloServidor = new Thread(new Runnable() {
            @Override
            public void run() {
                if (servidor == null){
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
                while(!servidor.online){
                    servidor.RecibirMensaje();
                    paqueteEnvio = servidor.getPaqueteEnvio();
                    if (paqueteEnvio != null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listaPaqueteEnvio.add(paqueteEnvio);
                                Log.d("INSERTADO", paqueteEnvio.getMensaje() + " " + listaPaqueteEnvio.size());
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
                if(!mensaje.equals("")){
                    txtMensaje.setText("");

                    hiloCliente = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            cliente = new Cliente(txtIpChat);
                            cliente.EnviaTexto(new PaqueteEnvio(txtNombreChat, txtIpChat, mensaje));
                        }
                    });
                    hiloCliente.start();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        servidor.online = true;    //  Este parámetro actúa de semáforo para detener el hilo encargado de escuchar los mensajes.
        //  Por si acaso, tratamos de forzar la interrupción del hilo manualmente.
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