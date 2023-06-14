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

/**
 * Creamos la clase herendando de Adapter que admite un tipo viewHolder necesario para contener los elementos de la vista
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerHolder> {
    private List<PaqueteEnvio> listaMensajes;

    /**
     * Constructor
     * @param listMensajes lista de donde se saca la información
     */
    public RecyclerAdapter(List<PaqueteEnvio> listMensajes){ this.listaMensajes = listMensajes;}

    /**
     * Esto llena cada celda del recyclerView con nuestro diseño
     * @param parent representa el contenedor padre donde se agregará la vista de la celda
     * @param viewType representa el tipo de vista que se está creando
     * @return devuelve un objeto de tipo RecyclerHolder
     */
    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plantilla_mensajes, parent, false);
        RecyclerHolder recyclerHolder = new RecyclerHolder(view);

        return recyclerHolder;
    }

    /**
     * Esta vista se encarga de enlazar la información con cada celda, el método se llama cuando se ha
     * creado la vista de cada celda de la lista y solo se tendría que recoger la información de cada elemendo de la lista
     * y asignarlo a cada elemento gráfico de la celda
     * @param holder representa el ViewHolder asociado a la vista del elemento de la lista
     * @param position posición del elemento actual en la lista
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        holder.txtNombre.setText(listaMensajes.get(position).getNombre());
        holder.txtMensaje.setText(listaMensajes.get(position).getMensaje());
    }

    @Override
    public int getItemCount() {
        return listaMensajes.size();
    }

    /**
     * Se crea la clase heredando de ViewHolder, que permitirá recrear los elementos de la vista del layaout de cada elemento
     * de la lista según el modelo de datos que hemos creado
     */
    public class RecyclerHolder extends RecyclerView.ViewHolder {
        TextView txtNombre;
        TextView txtMensaje;

        /**
         * El constructor recibe como parámetro un tipo vista(itemView) que contiene la celda ya creada
         * a partir del layout correspondiente
         * @param itemView representa la vista del elemento de la lista (celda del RecyclerView) que se
         *          *                 va a enlazar con los datos correspondientes.
         */
        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);
            txtNombre = (TextView)itemView.findViewById(R.id.txtNombreMensaje);
            txtMensaje = (TextView) itemView.findViewById(R.id.txtConversacionMensaje);
        }
    }
}
