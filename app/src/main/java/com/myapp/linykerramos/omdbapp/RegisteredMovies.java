package com.myapp.linykerramos.omdbapp;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.myapp.linykerramos.omdbapp.Adapter.FilmeAdapter;
import com.myapp.linykerramos.omdbapp.DAO.FilmesDAO;
import com.myapp.linykerramos.omdbapp.Model.Filme;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisteredMovies extends Fragment {


    View viewRegisteredMovies = null;
    ListView listView = null;
    List<Filme> filmes = new ArrayList<>();
    FilmeAdapter filmeAdapter = null;
    FilmesDAO filmesDAO = null;

    TextView qtdFilmesCadastrados = null;
    public RegisteredMovies() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewRegisteredMovies = inflater.inflate(R.layout.fragment_registered_movies, container, false);

        filmesDAO = new FilmesDAO(getContext());



        filmes = filmesDAO.getAllFilmes();

        qtdFilmesCadastrados = (TextView) viewRegisteredMovies.findViewById(R.id.qtdFilmesCadastrados);
        qtdFilmesCadastrados.setText(""+filmes.size());

        listView = (ListView) viewRegisteredMovies.findViewById(R.id.listaFilmeCadastrados);
        filmeAdapter = new FilmeAdapter(getContext(),filmes);
        listView.setAdapter(filmeAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Filme filme = filmes.get(position);
                Intent i = new Intent(getContext(),MovieSql.class);
                i.putExtra("filme", filme);
                startActivity(i);

            }
        });


        FloatingActionButton fab = (FloatingActionButton) viewRegisteredMovies.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                SearchMovies searchMovies = new SearchMovies();
                fragmentTransaction.replace(R.id.fragment_container,searchMovies).commit();
            }
        });

        // Inflate the layout for this fragment

        return viewRegisteredMovies;
    }

    @Override
    public void onResume() {

        filmes = filmesDAO.getAllFilmes();

        listView = (ListView) viewRegisteredMovies.findViewById(R.id.listaFilmeCadastrados);
        filmeAdapter = new FilmeAdapter(getContext(),filmes);
        listView.setAdapter(filmeAdapter);
        qtdFilmesCadastrados.setText(""+filmes.size());
        super.onResume();
    }
}
