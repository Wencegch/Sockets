package com.example.sockets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.InetAddresses;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class MainActivity extends AppCompatActivity {

    private Button btnConectar;
    private TextView txtNombreUsuario;
    private TextView txtIp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombreUsuario = findViewById(R.id.txtNombreUsuario);
        txtIp = findViewById(R.id.txtDireccionIp);
        btnConectar = findViewById(R.id.btnConectar);

        btnConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, ChatActivity.class);
                i.putExtra("Nombre", txtNombreUsuario.getText().toString().trim());
                i.putExtra("Ip", txtIp.getText().toString().trim());
                startActivity(i);
            }
        });
    }
}