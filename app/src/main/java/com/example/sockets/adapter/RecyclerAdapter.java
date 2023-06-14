package com.example.sockets.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sockets.R;
import com.example.sockets.model.PaqueteEnvio;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {
    private List<PaqueteEnvio> listaMensajes;

    public RecyclerAdapter(List<PaqueteEnvio> listMensajes){ this.listaMensajes = listMensajes;}

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plantilla_mensajes, parent, false);
        RecyclerHolder recyclerHolder = new RecyclerHolder(view);

        return recyclerHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        holder.txtNombre.setText(listaMensajes.get(position).getNombre());
        holder.txtMensaje.setText(listaMensajes.get(position).getMensaje());
    }

    @Override
    public int getItemCount() {
        return listaMensajes.size();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView txtNombre;
        TextView txtMensaje;
        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = (TextView)itemView.findViewById(R.id.txtNombreMensaje);
            txtMensaje = (TextView) itemView.findViewById(R.id.txtConversacionMensaje);
        }
    }
}
