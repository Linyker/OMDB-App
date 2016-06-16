package com.myapp.linykerramos.omdbapp.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.myapp.linykerramos.omdbapp.Model.Filme;
import com.myapp.linykerramos.omdbapp.R;
import com.myapp.linykerramos.omdbapp.Volley.CustomVolleyRequestImage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Linyker Ramos on 15/06/2016.
 */
public class FilmeAdapter extends BaseAdapter {

    private Context context;
    private List<Filme> filmes;

    public FilmeAdapter(Context context, List<Filme> filmes){
        this.context = context;
        this.filmes = filmes;
    }

    @Override
    public int getCount() {
        return filmes.size();
    }

    @Override
    public Object getItem(int position) {
        return filmes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Filme filme = filmes.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout,null);

        TextView title = (TextView) view.findViewById(R.id.textTitle);
        title.setText(filme.getTitle());

        TextView year = (TextView) view.findViewById(R.id.textYear);
        year.setText(filme.getYear());

        NetworkImageView img = (NetworkImageView) view.findViewById(R.id.imageFilme);

        //ImageView img = (ImageView) view.findViewById(R.id.imageFilme);
        ImageLoader mImageLoader = CustomVolleyRequestImage.getInstance(context).getImageLoader();
        mImageLoader.get(filme.getPoster(),ImageLoader.getImageListener(img,R.drawable.ic_menu_camera,android.R.drawable.ic_dialog_alert));
        img.setImageUrl(filme.getPoster(),mImageLoader);

        return view;

    }
}
