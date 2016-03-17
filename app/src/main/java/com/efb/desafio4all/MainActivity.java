package com.efb.desafio4all;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.efb.desafio4all.model.Local;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
}
