package com.example.chris.ddcomercial.Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.chris.ddcomercial.Clases.Cervezas;
import com.example.chris.ddcomercial.Clases.CustomVolleyRequest;
import com.example.chris.ddcomercial.R;

import java.util.List;

/**
 * Created by Chris on 15/06/2016.
 */
public class AdaptadorCervezas extends RecyclerView.Adapter<AdaptadorCervezas.ViewHolder> implements ItemClickListenerCervezas {

    private ImageLoader imageLoader;
    private Context context;
    List<Cervezas> superCervezas;

    public AdaptadorCervezas(List<Cervezas> superCervezas, Context context) {
        this.superCervezas = superCervezas;
        this.context = context;
    }
    @Override
    public int getItemCount() {
        return superCervezas.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_cervezas, parent, false);
        return new ViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Cervezas cerveza = superCervezas.get(position);
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        holder.imageView.setImageUrl(cerveza.getImg_marc(), imageLoader);

    }

    @Override
    public void onItemClick(View view, int position) {
        //ActividadDetalleCombo.launch((Activity) context, superCombos.get(position).getId_combo());
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Views
        public NetworkImageView imageView;
        public ItemClickListenerCervezas listener;

        public ViewHolder(View itemView, ItemClickListenerCervezas listener) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageView_Cervezas);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }
}
interface ItemClickListenerCervezas {
    void onItemClick(View view, int position);
}