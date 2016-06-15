package com.example.chris.ddcomercial.Adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.chris.ddcomercial.Clases.Combos;
import com.example.chris.ddcomercial.Clases.CustomVolleyRequest;
import com.example.chris.ddcomercial.R;

import java.util.List;

/**
 * Created by Chris on 15/06/2016.
 */
public class AdaptadorDetalleCombo extends RecyclerView.Adapter<AdaptadorDetalleCombo.ViewHolder> implements ItemClickListenerDetalle {

    private ImageLoader imageLoader;
    private Context context;
    List<Combos> superCombos;

    public AdaptadorDetalleCombo(List<Combos> superCombos, Context context) {
        this.superCombos = superCombos;
        this.context = context;
    }
    @Override
    public int getItemCount() {
        return superCombos.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_combo_detalle, parent, false);
        return new ViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Combos combo = superCombos.get(position);
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        holder.imageView.setImageUrl(combo.getImg_comb(), imageLoader);
        holder.textViewDescripcion.setText(combo.getDesc_comb());
        holder.textViewPrecio.setText(combo.getPrecio_comb());
    }

    @Override
    public void onItemClick(View view, int position) {

    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Views
        public NetworkImageView imageView;
        public TextView textViewDescripcion;
        public TextView textViewPrecio;

        public ItemClickListenerDetalle listener;

        public ViewHolder(View itemView, ItemClickListenerDetalle listener) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageViewDetalleCombo);
            textViewDescripcion = (TextView) itemView.findViewById(R.id.TextDescripcion);
            textViewPrecio = (TextView) itemView.findViewById(R.id.TextPrecio);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }
}
interface ItemClickListenerDetalle {
    void onItemClick(View view, int position);
}
