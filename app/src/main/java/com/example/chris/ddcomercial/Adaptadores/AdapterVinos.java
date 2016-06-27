package com.example.chris.ddcomercial.Adaptadores;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.chris.ddcomercial.Clases.CustomVolleyRequest;
import com.example.chris.ddcomercial.Clases.Vinos;
import com.example.chris.ddcomercial.R;

import java.util.List;

/**
 * Created by Chris on 26/06/2016.
 */
public class AdapterVinos extends RecyclerView.Adapter<AdapterVinos.ViewHolder>{

    private ImageLoader imageLoader;
    private Context context;
    List<Vinos> superVinos;
    private EscuchaEventosClick escucha;

    public interface EscuchaEventosClick {
        void onItemClick(ViewHolder holder, int posicion);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Views
        public NetworkImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageView_Vinos);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            escucha.onItemClick(this, getAdapterPosition());
        }
    }

    public AdapterVinos(List<Vinos> superVinos, Context context, EscuchaEventosClick escucha) {
        this.superVinos = superVinos;
        this.context = context;
        this.escucha = escucha;
    }

    @Override
    public int getItemCount() {
        return superVinos.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_vinos, parent, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Vinos vinos = superVinos.get(position);
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        holder.imageView.setImageUrl(vinos.getImg_marc(), imageLoader);
    }
}
