package com.example.chris.ddcomercial.Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.chris.ddcomercial.Clases.CustomVolleyRequest;
import com.example.chris.ddcomercial.Clases.Ron;
import com.example.chris.ddcomercial.Clases.Vodka;
import com.example.chris.ddcomercial.R;

import java.util.List;

/**
 * Created by Chris on 26/06/2016.
 */
public class AdapterVodka extends RecyclerView.Adapter<AdapterVodka.ViewHolder> {

    private ImageLoader imageLoader;
    private Context context;
    List<Vodka> superVodka;
    private EscuchaEventosClick escucha;

    public interface EscuchaEventosClick {
        void onItemClick(ViewHolder holder, int posicion);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Views
        public NetworkImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageView_Vodka);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            escucha.onItemClick(this, getAdapterPosition());
        }
    }

    public AdapterVodka(List<Vodka> superVodka, Context context, EscuchaEventosClick escucha) {
        this.superVodka = superVodka;
        this.context = context;
        this.escucha = escucha;
    }

    @Override
    public int getItemCount() {
        return superVodka.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_vodka, parent, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Vodka ron = superVodka.get(position);
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        holder.imageView.setImageUrl(ron.getImg_marc(), imageLoader);
    }

}
