package com.efb.desafio4all;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.efb.desafio4all.model.Local;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> myDataSet;
    private ProgressBar progress;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        progress = (ProgressBar)findViewById(R.id.progressBar);

        myDataSet= new ArrayList<>();
        populaLista();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

       /* // specify an adapter (see also next example)
        mAdapter = new MyAdapter(this, myDataSet);

        mRecyclerView.setAdapter(mAdapter);*/

    }

    public void populaLista(){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://dev.4all.com:3003/tarefa";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        Lista lista = gson.fromJson(response, Lista.class);
                        myDataSet = lista.lista;
                        // specify an adapter (see also next example)
                        mAdapter = new MyAdapter(context, myDataSet);
                        mRecyclerView.setAdapter(mAdapter);

                        mAdapter.notifyDataSetChanged();
                        progress.setVisibility(View.INVISIBLE);
                        Log.v("teste", "Acertou! " + lista.lista.get(0));
                        Log.v("teste", "Acertou! " + response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
                Log.v("teste", "Deu Erro! " + error);
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    public void wrapper(){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://dev.4all.com:3003/tarefa/1";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //ArrayList<Local> retorno = new ArrayList<>();
                        Local local;
                        Gson gson = new Gson();
                        local = gson.fromJson(response, Local.class);
                        Log.v("teste", "Cidade: " + local.getCidade());
                        Log.v("teste", "Bairro: " + local.getBairro());
                        Log.v("teste", "Telefone: " + local.getTelefone());
                        Log.v("teste", "Texto: " + local.getTexto());
                        Log.v("teste", "Latitude: " + local.getLatitude());
                        Log.v("teste", "Longitude: " + local.getLongitude());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
                Log.v("teste", "Deu Erro! " + error);
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
