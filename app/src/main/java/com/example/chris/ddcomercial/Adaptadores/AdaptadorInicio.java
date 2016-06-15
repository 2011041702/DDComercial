package com.example.chris.ddcomercial.Adaptadores;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.chris.ddcomercial.Activity.ActividadDetalleCombo;
import com.example.chris.ddcomercial.Clases.Combos;
import com.example.chris.ddcomercial.Clases.CustomVolleyRequest;
import com.example.chris.ddcomercial.R;


import java.util.List;

/**
 * Created by Chris on 29/05/2016.
 */
public class AdaptadorInicio extends RecyclerView.Adapter<AdaptadorInicio.ViewHolder> implements ItemClickListenerInicio {


    private ImageLoader imageLoader;
    private Context context;
    List<Combos> superCombos;


    public AdaptadorInicio(List<Combos> superCombos, Context context) {
        this.superCombos = superCombos;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return superCombos.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_combos, parent, false);
        return new ViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Combos combo = superCombos.get(position);
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        holder.imageView.setImageUrl(combo.getImg_comb(), imageLoader);

    }

    @Override
    public void onItemClick(View view, int position) {
        ActividadDetalleCombo.launch((Activity) context, superCombos.get(position).getId_combo());
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Views
        public NetworkImageView imageView;
        public ItemClickListenerInicio listener;

        public ViewHolder(View itemView, ItemClickListenerInicio listener) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageViewHero);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }
}

interface ItemClickListenerInicio {
    void onItemClick(View view, int position);
}
