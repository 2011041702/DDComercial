package com.example.chris.ddcomercial.Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.chris.ddcomercial.Clases.Busqueda;
import com.example.chris.ddcomercial.Clases.Cervezas;
import com.example.chris.ddcomercial.Clases.CustomVolleyRequest;
import com.example.chris.ddcomercial.Clases.ProductoCervezas;
import com.example.chris.ddcomercial.R;

import java.util.List;

/**
 * Created by Christian on 01/07/2016.
 */
public class AdapterBusqueda extends RecyclerView.Adapter<AdapterBusqueda.ViewHolder> {

    private ImageLoader imageLoader;
    private Context context;
    List<Busqueda> superBusqueda;
    private EscuchaEventosClick escucha;

    public interface EscuchaEventosClick {
        void onItemClick(ViewHolder holder, int posicion);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Views
        public NetworkImageView imageView;
        public TextView textNombreProducto;
        public TextView textPrecioProducto;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageView_Busqueda);
            textNombreProducto = (TextView) itemView.findViewById(R.id.TxtNombreProducto);
            textPrecioProducto = (TextView) itemView.findViewById(R.id.TxtPrecioProducto);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            escucha.onItemClick(this, getAdapterPosition());
        }
    }

    public AdapterBusqueda(List<Busqueda> superBusqueda, Context context, EscuchaEventosClick escucha) {
        this.superBusqueda = superBusqueda;
        this.context = context;
        this.escucha = escucha;
    }

    @Override
    public int getItemCount() {
        return superBusqueda.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_busqueda, parent, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Busqueda busqueda = superBusqueda.get(position);
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        holder.imageView.setImageUrl(busqueda.getImg_producto(), imageLoader);
        holder.textNombreProducto.setText(busqueda.getNombre_producto());
        holder.textPrecioProducto.setText(busqueda.getPrecio_producto());
    }
}
