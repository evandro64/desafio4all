package com.efb.desafio4all;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Evandro on 17/03/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<String> mItens;

    public MyAdapter(Context context, ArrayList<String> itens) {
        this.mContext = context;
        this.mItens = itens;
    }

    public int teste(){
        return 9;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tela_inicial, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(mContext, view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String id = mItens.get(position);
        holder.textView.setText(id);
    }

    @Override
    public int getItemCount() {
        return mItens.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder{

        private Context contextt;
        private TextView textView;

        public MyViewHolder(Context contextt, View itemView) {
            super(itemView);
            this.contextt = contextt;
            textView = (TextView)itemView.findViewById(R.id.localId);
        }
    }

    public void setData(ArrayList<String> data) {
        mItens = data;
        //Fechar o load.
    }
}
