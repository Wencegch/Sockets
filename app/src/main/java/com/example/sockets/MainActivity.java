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
    private SharedPreferences sharedPreferences;
    private boolean modoOscuro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtenemos las preferencias encargadas de guardar el tema elegido
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        modoOscuro = sharedPreferences.getBoolean("modoOscuro", false);
        // Modificamos el tema
        if(modoOscuro){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

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

    /*Se sobreescribe el metodo onCreateOptionsMenu para indicar que nuestra app tendra
     un menu personalizado.*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /*Se usa un inflater para construir la vista y se pasa el menu por defecto para
         que Android se encargue de colocarlo en la vista*/
        getMenuInflater().inflate(R.menu.simple_menu,menu);

        return true;
    }

    /*Se sobreescribe el metodo onOptionsItemSelected para manejar las selecciones a través
     de los diferentes item del menu.*/
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            // Si queremos modificar las preferencias
            case R.id.item_preferencias:
                // Modificamos el tema
                if(modoOscuro){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    sharedPreferences.edit().putBoolean("modoOscuro", false).apply();
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    sharedPreferences.edit().putBoolean("modoOscuro", true).apply();
                }
                // Recreamos la vista
                recreate();
                break;
        }
        return true;
    }
}