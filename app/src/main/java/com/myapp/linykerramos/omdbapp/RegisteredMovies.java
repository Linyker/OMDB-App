package com.myapp.linykerramos.omdbapp;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



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

        FloatingActionButton fab = (FloatingActionButton) viewRegisteredMovies.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });






        // Inflate the layout for this fragment
        return viewRegisteredMovies;
    }

}
