package com.efb.desafio4all;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.LruCache;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
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
    private FrameLayout fm;

    private float dpHeight;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ProgressBar progressImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle(R.string.tela_principal);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        fm = (FrameLayout)findViewById(R.id.framelayoutLocal);
        fm.setVisibility(View.VISIBLE);

        progressImg = (ProgressBar)findViewById(R.id.progressBar);
        progress = (ProgressBar)findViewById(R.id.progressBarLocal);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        dpHeight = outMetrics.heightPixels / density;
        float dpWidth  = outMetrics.widthPixels / density;

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
        nwImg.setDefaultImageResId(R.drawable.background);

        // Verifica resolução da Tela
        Configuration config = getResources().getConfiguration();
        if (config.smallestScreenWidthDp < 480)
        {
            ViewGroup.LayoutParams layoutParams = ((ImageView)nwImg).getLayoutParams();
            layoutParams.height = (int)Math.round((dpWidth)*1.2);
            ((ImageView)nwImg).setLayoutParams(layoutParams);
        }

        LinearLayout ll = (LinearLayout)findViewById(R.id.linearTeste);
        int t1 = ll.getHeight();
        Log.v("teste","Tamanho do linearLayout: "+t1);

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

        Intent intent = getIntent();
        String value = intent.getStringExtra("id");
        getSelectLocal(value);
    }

    public void getSelectLocal(String id){

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = getString(R.string.url_local) + id ;
        Log.v("teste", "URL " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        selectedLocal = gson.fromJson(response, Local.class);
                        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                        toolbar.setSubtitle(selectedLocal.getCidade() + " - " + selectedLocal.getBairro());
                        Log.v("teste", "Response " + response);
                        progressImg.setVisibility(View.GONE);
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
                        fm.setVisibility(View.GONE);
                        progress.setVisibility(View.GONE);

                        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(150, 150);
                        lp.setMargins(nwImg.getWidth() - 160, nwImg.getHeight() - 70, 0, 0);
                        ImageView iv = (ImageView)findViewById(R.id.estrela);
                        iv.setLayoutParams(lp);

                        FrameLayout container = (FrameLayout)findViewById(R.id.container_map);
                        FrameLayout.LayoutParams lp2 = new FrameLayout.LayoutParams(40, 40);
                        lp2.setMargins(container.getWidth()-40, container.getHeight()-20, 0, 0);
                        ImageView iv2 = (ImageView)findViewById(R.id.imageButton7);
                        iv2.setLayoutParams(lp2);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("teste", "Erro de conexão! " + error);
                Toast.makeText(mContext, "Erro! Verifique sua conexão", Toast.LENGTH_LONG).show();
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
                ScrollView scrollView = (ScrollView) findViewById(R.id.scrool);
                int scroll = (int)Math.round((dpHeight)*1.5);
                scrollView.scrollTo(0,scroll);
                if (selectedLocal.getComentarios().isEmpty()) Toast.makeText(mContext, "Nenhum comentário a ser exibido!", Toast.LENGTH_LONG).show();
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
        builder.setTitle(getString(R.string.endereco));
        builder.setMessage(selectedLocal.getEndereco());
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                return;
            }
        });
        alerta = builder.create();
        alerta.show();
    }
}
