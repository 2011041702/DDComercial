package com.example.chris.ddcomercial.Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.chris.ddcomercial.Clases.CustomVolleyRequest;
import com.example.chris.ddcomercial.Clases.Energizantes;
import com.example.chris.ddcomercial.Clases.Pisco;
import com.example.chris.ddcomercial.R;


import java.util.List;


public class AdapterEnergizante extends RecyclerView.Adapter<AdapterEnergizante.ViewHolder> {

    private ImageLoader imageLoader;
    private Context context;
    List<Energizantes> superEnergizante;
    private EscuchaEventosClick escucha;

    public interface EscuchaEventosClick {
        void onItemClick(ViewHolder holder, int posicion);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Views
        public NetworkImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageView_Energizante);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            escucha.onItemClick(this, getAdapterPosition());
        }
    }

    public AdapterEnergizante(List<Energizantes> superEnergizante, Context context, EscuchaEventosClick escucha) {
        this.superEnergizante = superEnergizante;
        this.context = context;
        this.escucha = escucha;
    }

    @Override
    public int getItemCount() {
        return superEnergizante.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_energizante, parent, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Energizantes energizante = superEnergizante.get(position);
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        holder.imageView.setImageUrl(energizante.getImg_marc(), imageLoader);
    }

}
