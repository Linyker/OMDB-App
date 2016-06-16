package com.myapp.linykerramos.omdbapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.myapp.linykerramos.omdbapp.Model.Filme;
import com.myapp.linykerramos.omdbapp.Volley.CustomVolleyRequestImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Movie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Filme filme = new Filme();

        final NetworkImageView imageMovie = (NetworkImageView) findViewById(R.id.imageMovie);
        final TextView txtResumo = (TextView) findViewById(R.id.txtResumo);
        final TextView txtAnoLancamento = (TextView) findViewById(R.id.txtAnoLancamento);
        final TextView txtGenero = (TextView) findViewById(R.id.txtGenero);
        final TextView txtAutor = (TextView) findViewById(R.id.txtAutor);
        final TextView txtPremios = (TextView) findViewById(R.id.txtPremios);
        final TextView txtNotas = (TextView) findViewById(R.id.txtNotas);
        final TextView txtAtores = (TextView) findViewById(R.id.txtAtores);


        Intent i = getIntent();
        final String idFilme = i.getStringExtra("idFilme");
        final String nomeFilme = i.getStringExtra("nomeFilme");

        String URL = "http://www.omdbapi.com/?plot=short&r=json&i=";
        final String[] responseReturn = new String[1];

        //seta o titulo com o nome do filme
        this.setTitle(nomeFilme);

        JsonObjectRequest requisicao =
                new JsonObjectRequest(
                        Request.Method.GET,
                        URL + idFilme,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    responseReturn[0] = response.getString("Response");
                                    //Log.e("RETORNO",responseReturn[0]);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                if(responseReturn[0].equals("True") ){

                                    try {
                                        JSONObject jsonObject = new JSONObject(String.valueOf(response));

                                        filme.set_id(idFilme);
                                        filme.setTitle(jsonObject.getString("Title"));
                                        filme.setYear(jsonObject.getString("Year"));
                                        filme.setRated(jsonObject.getString("Rated"));
                                        filme.setReleased(jsonObject.getString("Released"));
                                        filme.setRuntime(jsonObject.getString("Runtime"));
                                        filme.setGenre(jsonObject.getString("Genre"));
                                        filme.setDirector(jsonObject.getString("Director"));
                                        filme.setWriter(jsonObject.getString("Writer"));
                                        filme.setActors(jsonObject.getString("Actors"));
                                        filme.setPlot(jsonObject.getString("Plot"));
                                        filme.setAwards(jsonObject.getString("Awards"));
                                        filme.setPoster(jsonObject.getString("Poster"));
                                        if(!jsonObject.getString("Metascore").equals("N/A")){
                                            filme.setMetascore(Double.valueOf(jsonObject.getString("Metascore")));
                                        }
                                        if(!jsonObject.getString("imdbRating").equals("N/A")){
                                            filme.setImdb_rating(Double.valueOf(jsonObject.getString("imdbRating")));
                                        }
                                        //Preenche o network image view com a imagem do filme
                                        ImageLoader imageLoader = CustomVolleyRequestImage.getInstance(getBaseContext()).getImageLoader();
                                        imageLoader.get(filme.getPoster(),ImageLoader.getImageListener(imageMovie,R.drawable.ic_menu_camera,android.R.drawable.ic_dialog_alert));
                                        imageMovie.setImageUrl(filme.getPoster(),imageLoader);

                                        txtResumo.setText(filme.getPlot());
                                        txtAnoLancamento.setText("Ano: "+filme.getYear()+" | Lançamento: "+filme.getReleased());
                                        txtGenero.setText("Genero: "+ filme.getGenre());
                                        txtAutor.setText("Autor(es): "+filme.getWriter());
                                        txtAtores.setText("Atores: " +filme.getActors());
                                        txtPremios.setText("Prêmios: "+filme.getAwards());
                                        txtNotas.setText("Nota no IMDB: "+filme.getImdb_rating()+" | MetaScore: "+ filme.getMetascore());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }else{
                                    Log.e("Filme Não encontrado",response.toString());
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                );


        RequestQueue fila = Volley.newRequestQueue(this);
        fila.add(requisicao);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
