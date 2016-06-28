package com.example.chris.ddcomercial.Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.chris.ddcomercial.Clases.CustomVolleyRequest;
import com.example.chris.ddcomercial.Clases.ProductoCervezas;
import com.example.chris.ddcomercial.Clases.ProductoRon;
import com.example.chris.ddcomercial.R;

import java.util.List;

/**
 * Created by Chris on 28/06/2016.
 */
public class AdapterListaRon extends RecyclerView.Adapter<AdapterListaRon.ViewHolder> {

    private ImageLoader imageLoader;
    private Context context;
    List<ProductoRon> superProductoRon;
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
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageView_ProductoRon);
            textNombreProducto = (TextView) itemView.findViewById(R.id.TxtNombreProducto);
            textPrecioProducto = (TextView) itemView.findViewById(R.id.TxtPrecioProducto);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            escucha.onItemClick(this, getAdapterPosition());
        }
    }

    public AdapterListaRon(List<ProductoRon> superProductoRon, Context context, EscuchaEventosClick escucha) {
        this.superProductoRon = superProductoRon;
        this.context = context;
        this.escucha = escucha;
    }

    @Override
    public int getItemCount() {
        return superProductoRon.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_producto_ron, parent, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProductoRon productoron = superProductoRon.get(position);
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        holder.imageView.setImageUrl(productoron.getImg_producto(), imageLoader);
        holder.textNombreProducto.setText(productoron.getNombre_producto());
        //holder.textPrecioProducto.setText(productocervezas.getPrecio_producto());
        holder.textPrecioProducto.setText(productoron.getPrecio_producto());
    }
}
