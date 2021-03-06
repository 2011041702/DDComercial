package com.example.chris.ddcomercial.Adaptadores;




import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.chris.ddcomercial.Clases.Combos;
import com.example.chris.ddcomercial.Clases.CustomVolleyRequest;

import com.example.chris.ddcomercial.R;


import java.util.List;


public class AdaptadorInicio extends RecyclerView.Adapter<AdaptadorInicio.ViewHolder>{


    private ImageLoader imageLoader;
    private Context context;
    List<Combos> superCombos;
    private EscuchaEventosClick escucha;

    public interface EscuchaEventosClick {
        void onItemClick(ViewHolder holder, int posicion);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Views
        public NetworkImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageViewHero);
            //Nombre = (TextView) itemView.findViewById(R.id.TextNombreCombo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            escucha.onItemClick(this, getAdapterPosition());
        }
    }


    public AdaptadorInicio(List<Combos> superCombos, Context context, EscuchaEventosClick escucha) {
        this.superCombos = superCombos;
        this.context = context;
        this.escucha = escucha;
    }

    @Override
    public int getItemCount() {
        return superCombos.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_combos, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Combos combo = superCombos.get(position);
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        holder.imageView.setImageUrl(combo.getImg_comb(), imageLoader);
        //holder.Nombre.setText(combo.getNom_comb());
    }

}

