package com.efb.desafio4all;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.LruCache;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.efb.desafio4all.adapters.ComentsAdapter;
import com.efb.desafio4all.adapters.MyAdapter;
import com.efb.desafio4all.fragments.Map_Fragment;
import com.efb.desafio4all.model.Comentarios;
import com.efb.desafio4all.model.Local;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainScreen extends AppCompatActivity {

    private Local selectedLocal;
    private ProgressBar progress;
    private String urlImage, telefone;
    private ImageLoader imageLoader;
    private NetworkImageView nwImg;
    private TextView title, text, endereco;
    private ImageView buttonLigar;
    private ImageView buttonServicos;
    private ImageView buttonEnderecos;
    private ImageView buttonComentarios;
    private ImageView buttonFavoritos;
    private LinearLayout linearMap;
    private LinearLayout linearEndereco;
    private LatLng mLocal;
    private AlertDialog alerta;
    private ArrayList<Comentarios> mComents;
    private Context mContext;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Tela Principal");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mContext = this;

        title = (TextView)findViewById(R.id.title);
        text = (TextView)findViewById(R.id.textViewText);
        endereco = (TextView)findViewById(R.id.endereco);

        buttonLigar = (ImageView)findViewById(R.id.imageButton);
        buttonServicos = (ImageView)findViewById(R.id.imageButton2);
        buttonEnderecos = (ImageView)findViewById(R.id.imageButton3);
        buttonComentarios = (ImageView)findViewById(R.id.imageButton4);
        buttonFavoritos = (ImageView)findViewById(R.id.imageButton5);
        buttonFavoritos = (ImageView)findViewById(R.id.imageButton5);

        linearMap = (LinearLayout)findViewById(R.id.linearMap);
        linearEndereco = (LinearLayout)findViewById(R.id.enderecoMap);

        // NetWorkImageView
        nwImg = (NetworkImageView) findViewById(R.id.netWorkImageView);
        nwImg.setDefaultImageResId(R.drawable.load);
        nwImg.setErrorImageResId(R.drawable.error);

        // Adapter dos comentários
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_coments);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new com.efb.desafio4all.LinearLayoutManager(mRecyclerView.getContext()));

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
                        text.setText(selectedLocal.getTexto());
                        showMap(mLocal);
                        // Preenche os comentários
                        mComents = selectedLocal.getComentarios() ;
                        mAdapter = new ComentsAdapter(mContext, mComents);
                        mRecyclerView.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
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
                alertMessage();
                break;
            case R.id.imageButton4:
                // ToDo
                break;
            default:
                // ToDo;
        }
    }

    private void showMap(LatLng local) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_map, Map_Fragment.newInstance(local)).commitAllowingStateLoss();
        endereco.setText(selectedLocal.getEndereco());
    }

    public void alertMessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Endereço");
        builder.setMessage(selectedLocal.getEndereco());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                return;
            }
        });
        alerta = builder.create();
        alerta.show();
    }
}
