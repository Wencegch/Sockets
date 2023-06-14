package com.example.sockets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ConstraintLayout constraintLayout;
    private Button btnConectar;
    private TextView txtNombreUsuario;
    private TextView txtIp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        constraintLayout = findViewById(R.id.constraintLayout);
        txtNombreUsuario = findViewById(R.id.txtNombreUsuario);
        txtIp = findViewById(R.id.txtDireccionIp);
        btnConectar = findViewById(R.id.btnConectar);

        btnConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = txtNombreUsuario.getText().toString().trim();
                String ip = txtIp.getText().toString().trim();

                if (nombre.equals("") || ip.equals("")){
                    Toast.makeText(MainActivity.this,"Debes introducir nombre y ip",Toast.LENGTH_SHORT).show();
                }else{
                    Intent i = new Intent(MainActivity.this, ChatActivity.class);
                    i.putExtra("Nombre", txtNombreUsuario.getText().toString().trim());
                    i.putExtra("Ip", txtIp.getText().toString().trim());
                    startActivity(i);
                }
            }
        });
    }
}