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
import com.example.chris.ddcomercial.Clases.Snacks;
import com.example.chris.ddcomercial.R;

import java.util.List;


public class AdapterSnacks extends RecyclerView.Adapter<AdapterSnacks.ViewHolder> {

    private ImageLoader imageLoader;
    private Context context;
    List<Snacks> superSnacks;
    private EscuchaEventosClick escucha;

    public interface EscuchaEventosClick {
        void onItemClick(ViewHolder holder, int posicion);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Views
        public NetworkImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageView_Snacks);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            escucha.onItemClick(this, getAdapterPosition());
        }
    }

    public AdapterSnacks(List<Snacks> superSnacks, Context context, EscuchaEventosClick escucha) {
        this.superSnacks = superSnacks;
        this.context = context;
        this.escucha = escucha;
    }

    @Override
    public int getItemCount() {
        return superSnacks.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lista_snacks, parent, false);
        return new ViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Snacks snack = superSnacks.get(position);
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        holder.imageView.setImageUrl(snack.getImg_marc(), imageLoader);
    }

}
