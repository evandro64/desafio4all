package com.efb.desafio4all.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.efb.desafio4all.R;
import com.efb.desafio4all.model.Comentarios;

import java.util.ArrayList;

/**
 * Created by Evandro on 22/03/2016.
 */
public class ComentsAdapter extends RecyclerView.Adapter<ComentsAdapter.MyViewHolder>{

    private Context mContext;
    private ArrayList<Comentarios> mItens;

    public ComentsAdapter(Context context, ArrayList<Comentarios> itens) {
        this.mContext = context;
        this.mItens = itens;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_comentarios, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(mContext, view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        /*id = mItens.get(position);*/
        holder.nome.setText(mItens.get(position).getNome());
        holder.titulo.setText(mItens.get(position).getTitulo());
        holder.comentario.setText(mItens.get(position).getComentario());
        int nota = mItens.get(position).getNota();
        holder.ratingBar.setRating(nota);

    }

    @Override
    public int getItemCount() {
        return mItens.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Context contextt;
        private ImageView foto;
        private TextView nome;
        private RatingBar ratingBar;
        private TextView titulo;
        private TextView comentario;

        public MyViewHolder(Context contextt, View itemView) {
            super(itemView);
            this.contextt = contextt;
            itemView.setOnClickListener(this);
            nome = (TextView)itemView.findViewById(R.id.nome);
            ratingBar = (RatingBar)itemView.findViewById(R.id.ratingBar);
            /*LayerDrawable layerDrawable = (LayerDrawable) ratingBar.getProgressDrawable();
            DrawableCompat.setTint(DrawableCompat.wrap(layerDrawable.getDrawable(0)), Color.WHITE);   // Empty star
            DrawableCompat.setTint(DrawableCompat.wrap(layerDrawable.getDrawable(1)), Color.YELLOW); // Partial star
            DrawableCompat.setTint(DrawableCompat.wrap(layerDrawable.getDrawable(2)), Color.YELLOW);  // Full star*/

            LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(contextt.getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(1).setColorFilter(contextt.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(0).setColorFilter(contextt.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);

            titulo = (TextView)itemView.findViewById(R.id.titulo);
            comentario = (TextView)itemView.findViewById(R.id.comentario);
        }

        @Override
        public void onClick(View v) {
            //ToDO
        }
    }

}
