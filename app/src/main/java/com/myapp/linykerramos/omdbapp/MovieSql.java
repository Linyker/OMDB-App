package com.myapp.linykerramos.omdbapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.myapp.linykerramos.omdbapp.DAO.FilmesDAO;
import com.myapp.linykerramos.omdbapp.Model.Filme;
import com.myapp.linykerramos.omdbapp.Volley.CustomVolleyRequestImage;

public class MovieSql extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_sql);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        setSupportActionBar(toolbar);

        final Filme filme = (Filme) getIntent().getSerializableExtra("filme");
        final FilmesDAO filmesDAO = new FilmesDAO(getBaseContext());

        this.setTitle(filme.getTitle());

        final NetworkImageView imageMovie = (NetworkImageView) findViewById(R.id.imageMovie);
        final TextView txtResumo = (TextView) findViewById(R.id.txtResumo);
        final TextView txtAnoLancamento = (TextView) findViewById(R.id.txtAnoLancamento);
        final TextView txtGenero = (TextView) findViewById(R.id.txtGenero);
        final TextView txtAutor = (TextView) findViewById(R.id.txtAutor);
        final TextView txtPremios = (TextView) findViewById(R.id.txtPremios);
        final TextView txtNotas = (TextView) findViewById(R.id.txtNotas);
        final TextView txtAtores = (TextView) findViewById(R.id.txtAtores);

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog alerta;

                builder.setMessage(R.string.deletaFilme).
                        setPositiveButton(R.string.confirmaDelete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int resultado = filmesDAO.delete(filme.get_id());
                                Snackbar.make(view, R.string.mensagemFilmeDeletado, Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        }).setNegativeButton(R.string.cancelaDelete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                });

                alerta = builder.create();
                alerta.show();
            }
        });
    }


}
