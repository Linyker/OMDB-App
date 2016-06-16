package com.myapp.linykerramos.omdbapp;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.myapp.linykerramos.omdbapp.Adapter.FilmeAdapter;
import com.myapp.linykerramos.omdbapp.Model.Filme;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchMovies extends Fragment {

    private String URL = "http://www.omdbapi.com/?s=";
    View viewSearchMovies = null;
    ProgressDialog progressDialog = null;

    public SearchMovies() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewSearchMovies = inflater.inflate(R.layout.fragment_search_movies, container, false);


        final EditText busca = (EditText) viewSearchMovies.findViewById(R.id.edtBusca);
        final Button botaoBusca = (Button) viewSearchMovies.findViewById(R.id.botaoBusca);
        final ListView lista = (ListView) viewSearchMovies.findViewById(R.id.listaFilme);


        botaoBusca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtbusca = busca.getText().toString();
                final String[] responseReturn = {""};
                Log.e("URL",URL + txtbusca.replace(" ","+"));
                JsonObjectRequest requisicao =
                        new JsonObjectRequest(
                                Request.Method.GET,
                                URL + txtbusca.replace(" ","+"),
                                null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            responseReturn[0] = response.get("Response").toString();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        if(responseReturn[0].equals("True") ){

                                            try {
                                                //Log.e("RETORNO", response.getString("Search"));
                                                JSONObject jsonObject = new JSONObject(String.valueOf(response));
                                                JSONArray resultados = jsonObject.getJSONArray("Search");

                                                /*
                                                for (int i = 0; i < resultados.length();i++){
                                                    JSONObject filme = resultados.getJSONObject(i);
                                                    String titulo = filme.getString("Title");
                                                    String poster = filme.getString("Poster");

                                                    filmes[i] = titulo;
                                                }*/
                                                List<Filme> filmes = new ArrayList<Filme>();
                                                for (int i = 0; i < resultados.length();i++){
                                                    JSONObject filmeObject = resultados.getJSONObject(i);
                                                    Filme filme = new Filme();

                                                    filme.setTitle(filmeObject.getString("Title"));
                                                    filme.setYear(filmeObject.getString("Year"));
                                                    filme.setPoster(filmeObject.getString("Poster"));
                                                    filme.set_id(filmeObject.getString("imdbID"));
                                                    filmes.add(filme);
                                                }

                                                FilmeAdapter adapterfilme = new FilmeAdapter(getContext(), filmes);
                                                lista.setAdapter(adapterfilme);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                           // Log.e("FUNCIONOU",response.toString());
                                        }else{
                                            Toast.makeText(getContext(),"Não foi encontrado o filme!",Toast.LENGTH_LONG).show();
                                            //Log.e("ERRO RESPONSE",responseReturn[0]);
                                        }
                                        progressDialog.dismiss();
                                    }

                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.e("ERRO RESPONSE",error.toString());
                                        progressDialog.dismiss();
                                    }
                                }

                        );
                RequestQueue fila = Volley.newRequestQueue(getContext());
                fila.add(requisicao);
                //initialize the progress dialog and show it
                progressDialog = new ProgressDialog(getActivity());
                progressDialog.setMessage("Buscando....");
                progressDialog.show();
            }

        });

        return viewSearchMovies;
    }


}
