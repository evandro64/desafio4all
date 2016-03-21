package com.efb.desafio4all;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.efb.desafio4all.model.Local;
import com.google.gson.Gson;

public class MainScreen extends AppCompatActivity {

    private Local selectedLocal;
    private ProgressBar progress;
    private String urlImage;
    private ImageLoader imageLoader;
    private NetworkImageView nwImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tela Principal");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //imageView = (ImageView)findViewById(R.id.imageView);

        // NetWorkImageView
        nwImg = (NetworkImageView) findViewById(R.id.netWorkImageView);
        nwImg.setDefaultImageResId(R.drawable.load);
        nwImg.setErrorImageResId(R.drawable.error);

        RequestQueue queue = Volley.newRequestQueue(this);

        imageLoader = new ImageLoader(queue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(10);
            @Override
            public Bitmap getBitmap(String url) {
                cache.get(url);
                return null;
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });

        progress = (ProgressBar)findViewById(R.id.progressBarLocal);

        Intent intent = getIntent();
        String value = intent.getStringExtra("id");
        getSelectLocal(value);
    }

    public void getSelectLocal(String id){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://dev.4all.com:3003/tarefa/" + id ;
        Log.v("teste", "URL " + url);
        //String url = "http://dev.4all.com:3003/tarefa/1" ;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //ArrayList<Local> retorno = new ArrayList<>();
                        //Local local;
                        Gson gson = new Gson();
                        selectedLocal = gson.fromJson(response, Local.class);
                        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                        toolbar.setSubtitle(selectedLocal.getCidade() + " - " + selectedLocal.getBairro());
                        Log.v("teste", "Response " + response);
                        urlImage = selectedLocal.getUrlFoto();
                        nwImg.setImageUrl(urlImage, imageLoader);
                        /*Log.v("teste", "Cidade: " + selectedLocal.getCidade());
                        Log.v("teste", "Bairro: " + selectedLocal.getBairro());
                        Log.v("teste", "Telefone: " + selectedLocal.getTelefone());
                        Log.v("teste", "Texto: " + selectedLocal.getTexto());
                        Log.v("teste", "Latitude: " + selectedLocal.getLatitude());
                        Log.v("teste", "Longitude: " + selectedLocal.getLongitude());*/
                        progress.setVisibility(View.INVISIBLE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
                Log.v("teste", "Deu Erro! " + error);
            }
        });

        // Add the request to the RequestQueue.
        Log.v("teste", "String request! " + stringRequest);
        queue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // Retorna para Activity anterior
        if (id == android.R.id.home){
            finish();
            return (true);
        }
        return super.onOptionsItemSelected(item);
    }
}
