package com.efb.desafio4all.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.util.LruCache;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.efb.desafio4all.R;
import com.efb.desafio4all.model.Comentarios;
import com.mikhaellopez.circularimageview.CircularImageView;

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
        holder.nome.setText(mItens.get(position).getNome());
        holder.titulo.setText(mItens.get(position).getTitulo());
        holder.comentario.setText(mItens.get(position).getComentario());
        int nota = mItens.get(position).getNota();
        holder.ratingBar.setRating(nota);

        // Insere requisição de acesso na fila
        RequestQueue queue = Volley.newRequestQueue(mContext);

        // Busca Imagem
        ImageLoader imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(10);
            @Override
            public Bitmap getBitmap(String url) {
                //int teste = cache.maxSize();
                cache.get(url);
                return null;
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });

        // Seta foto da URL no ImageView
        imageLoader.get(mItens.get(position).getUrlFoto(),imageLoader.getImageListener(holder.circularImageView,0,0));
    }

    @Override
    public int getItemCount() {
        return mItens.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Context contextt;
        private TextView nome;
        private RatingBar ratingBar;
        private TextView titulo;
        private TextView comentario;
        private CircularImageView circularImageView;

        public MyViewHolder(Context contextt, View itemView) {
            super(itemView);
            this.contextt = contextt;
            itemView.setOnClickListener(this);

            circularImageView = (CircularImageView)itemView.findViewById(R.id.circularImage);
            circularImageView.setBorderColor(mContext.getResources().getColor(R.color.colorAccent));
            circularImageView.setBorderWidth(2);
            circularImageView.addShadow();
            circularImageView.setShadowRadius(15);
            circularImageView.setShadowColor(Color.rgb(224,139,0));

            nome = (TextView)itemView.findViewById(R.id.nome);
            ratingBar = (RatingBar)itemView.findViewById(R.id.ratingBar);

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
