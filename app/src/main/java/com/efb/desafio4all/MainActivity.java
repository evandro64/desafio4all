package com.efb.desafio4all;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.efb.desafio4all.adapters.MyAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> myDataSet;
    private ProgressBar progress;
    private Context context;
    private FrameLayout fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(R.string.lista);

        context = this;

        fm = (FrameLayout)findViewById(R.id.framelayoutMain);
        fm.setVisibility(View.VISIBLE);

        progress = (ProgressBar)findViewById(R.id.progressBarMain);

        myDataSet= new ArrayList<>();
        populaLista();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    public void populaLista(){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = getString(R.string.urlListaLocal);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Lista lista = gson.fromJson(response, Lista.class);
                        myDataSet = lista.lista;
                        mAdapter = new MyAdapter(context, myDataSet);
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                        fm.setVisibility(View.GONE);
                        progress.setVisibility(View.GONE);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("teste", "Erro na conexão! " + error);
                Toast.makeText(context, "Erro! Verifique sua conexão", Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    private class Lista{
        private ArrayList<String> lista;
        public Lista(ArrayList<String> lista) {
            this.lista = lista;
        }
    }
}
