package com.efb.desafio4all.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.efb.desafio4all.MainScreen;
import com.efb.desafio4all.R;
import java.util.ArrayList;

/**
 * Created by Evandro on 17/03/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<String> mItens;
    private String id;
    private String selectId;

    public MyAdapter(Context context, ArrayList<String> itens) {
        this.mContext = context;
        this.mItens = itens;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tela_inicial, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(mContext, view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        id = mItens.get(position);
        holder.textView.setText(id);
    }

    @Override
    public int getItemCount() {
        return mItens.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Context contextt;
        private TextView textView;

        public MyViewHolder(Context contextt, View itemView) {
            super(itemView);
            this.contextt = contextt;
            itemView.setOnClickListener(this);
            textView = (TextView)itemView.findViewById(R.id.localId);
        }

        @Override
        public void onClick(View v) {
            Log.d("teste", "onClick " + getAdapterPosition());
            selectId = mItens.get(getAdapterPosition());
            Intent intent = new Intent(this.contextt, MainScreen.class);
            intent.putExtra("id",selectId);
            this.contextt.startActivity(intent);
        }
    }


}
