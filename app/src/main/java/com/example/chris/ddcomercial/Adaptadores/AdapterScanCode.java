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
import com.example.chris.ddcomercial.Clases.ScanCode;
import com.example.chris.ddcomercial.R;

import java.util.List;

/**
 * Created by Christian on 02/07/2016.
 */
public class AdapterScanCode extends RecyclerView.Adapter<AdapterScanCode.ViewHolder> {

    private ImageLoader imageLoader;
    private Context context;
    List<ScanCode> superScanCode;
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
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageView_ProductoScanCode);
            textNombreProducto = (TextView) itemView.findViewById(R.id.TxtNombreProducto);
            textPrecioProducto = (TextView) itemView.findViewById(R.id.TxtPrecioProducto);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            escucha.onItemClick(this, getAdapterPosition());
        }
    }

    public AdapterScanCode(List<ScanCode> superScanCode, Context context, EscuchaEventosClick escucha) {
        this.superScanCode = superScanCode;
        this.context = context;
        this.escucha = escucha;
    }

    @Override
    public int getItemCount() {
        return superScanCode.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_scancode, parent, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ScanCode scande = superScanCode.get(position);
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        holder.imageView.setImageUrl(scande.getImg_producto(), imageLoader);
        holder.textNombreProducto.setText(scande.getNombre_producto());
        //holder.textPrecioProducto.setText(productocervezas.getPrecio_producto());
        holder.textPrecioProducto.setText(scande.getPrecio_producto());
    }

}
