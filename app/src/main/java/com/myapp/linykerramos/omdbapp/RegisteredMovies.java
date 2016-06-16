package com.myapp.linykerramos.omdbapp;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myapp.linykerramos.omdbapp.DAO.FilmesDAO;
import com.myapp.linykerramos.omdbapp.Model.Filme;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RegisteredMovies extends Fragment {


    View viewRegisteredMovies = null;

    public RegisteredMovies() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewRegisteredMovies = inflater.inflate(R.layout.fragment_registered_movies, container, false);
        FilmesDAO filmesDAO = new FilmesDAO(getContext());
        List<Filme> filmes;

        filmes = filmesDAO.getAllFilmes();

        TextView texto = (TextView) viewRegisteredMovies.findViewById(R.id.qtdFilmesCadastrados);

        texto.setText(""+filmes.size());
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

}
