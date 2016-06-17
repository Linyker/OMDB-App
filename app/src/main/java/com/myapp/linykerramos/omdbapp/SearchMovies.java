package com.myapp.linykerramos.omdbapp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    final List<Filme> filmes = new ArrayList<Filme>();

    public SearchMovies() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewSearchMovies = inflater.inflate(R.layout.fragment_search_movies, container, false);


        final EditText busca = (EditText) viewSearchMovies.findViewById(R.id.edtBusca);
        final Button botaoBuscaFilme = (Button) viewSearchMovies.findViewById(R.id.botaoBusca);
        final ListView listView = (ListView) viewSearchMovies.findViewById(R.id.listaFilmeBusca);


        botaoBuscaFilme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filmes.clear();//limpa o arrayList para uma nova consulta
                String txtbusca = busca.getText().toString();
                final String[] responseReturn = {""};//resposta do retorno do json - retorna true ou false

                JsonObjectRequest requisicao =
                        new JsonObjectRequest(
                                Request.Method.GET,
                                URL + txtbusca.replace(" ","+"),
                                null,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            responseReturn[0] = response.get("Response").toString();//Response indica true ou false da requisiçao
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        if(responseReturn[0].equals("True") ){

                                            try {
                                                //Log.e("RETORNO", response.getString("Search"));
                                                JSONObject jsonObject = new JSONObject(String.valueOf(response));
                                                JSONArray resultados = jsonObject.getJSONArray("Search");

                                                for (int i = 0; i < resultados.length();i++){
                                                    JSONObject filmeObject = resultados.getJSONObject(i);
                                                    Filme filme = new Filme();

                                                    filme.set_id(filmeObject.getString("imdbID"));
                                                    filme.setTitle(filmeObject.getString("Title"));
                                                    filme.setYear(filmeObject.getString("Year"));
                                                    filme.setPoster(filmeObject.getString("Poster"));

                                                    filmes.add(filme);
                                                }

                                                FilmeAdapter adapterfilme = new FilmeAdapter(getContext(), filmes);
                                                listView.setAdapter(adapterfilme);

                                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                     //Log.e("ID",filmes.get(position).get_id());
                                                        Intent i = new Intent(getContext(),MovieJson.class);
                                                        i.putExtra("idFilme",filmes.get(position).get_id());
                                                        i.putExtra("nomeFilme",filmes.get(position).getTitle());
                                                        startActivity(i);
                                                    }
                                                });


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
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
