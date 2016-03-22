package com.efb.desafio4all;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.efb.desafio4all.fragments.Map_Fragment;
import com.efb.desafio4all.model.Local;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

public class MainScreen extends AppCompatActivity {

    private Local selectedLocal;
    private ProgressBar progress;
    private String urlImage, telefone;
    private ImageLoader imageLoader;
    private NetworkImageView nwImg;
    private TextView title;
    private ImageView buttonLigar;
    private ImageView buttonServicos;
    private ImageView buttonEnderecos;
    private ImageView buttonComentarios;
    private ImageView buttonFavoritos;
    private LatLng mLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tela Principal");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = (TextView)findViewById(R.id.title);
        buttonLigar = (ImageView)findViewById(R.id.imageButton);
        buttonServicos = (ImageView)findViewById(R.id.imageButton2);
        buttonEnderecos = (ImageView)findViewById(R.id.imageButton3);
        buttonComentarios = (ImageView)findViewById(R.id.imageButton4);
        buttonFavoritos = (ImageView)findViewById(R.id.imageButton5);

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
                        title.setText(selectedLocal.getTitulo());
                        telefone = selectedLocal.getTelefone();
                        mLocal = new LatLng(selectedLocal.getLatitude(),selectedLocal.getLongitude());
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
    /*public void onClickLigar(View v){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+telefone));
        startActivity(intent);
    }

    public void onClickServicos(View v){
        Intent intent = new Intent(MainScreen.this, ServicesActivity.class);
        startActivity(intent);
    }*/

    public void onClick(View v){
        Intent intent;
        switch(v.getId()) {
            case R.id.imageButton:
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+telefone));
                startActivity(intent);
                break;
            case R.id.imageButton2:
                intent = new Intent(MainScreen.this, ServicesActivity.class);
                startActivity(intent);
                break;
            case R.id.imageButton3:
                showMap(mLocal);
                break;
            case R.id.imageButton4:
                // ToDo
                break;
            default:
                // ToDo;
        }
    }

    /**
     * Cria uma nova instância do fragment que possui o mapa de acordo com a opção escolhida
     * (botão clicado) e exibe na tela.
     */
    private void showMap(LatLng local) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_map, Map_Fragment.newInstance(local)).commitAllowingStateLoss();
    }
}
