package com.myapp.linykerramos.omdbapp.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.myapp.linykerramos.omdbapp.Model.Filme;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linyker Ramos on 15/06/2016.
 */
public class FilmesDAO {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public FilmesDAO(Context context){
        databaseHelper = new DatabaseHelper(context);
    }


    public long insert(Filme filme){
        database = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.FILME_ID,filme.get_id());
        values.put(DatabaseHelper.FILME_TITLE,filme.getTitle());
        values.put(DatabaseHelper.FILME_YEAR,filme.getYear());
        values.put(DatabaseHelper.FILME_RATED,filme.getRated());
        values.put(DatabaseHelper.FILME_RELEASED,filme.getReleased());
        values.put(DatabaseHelper.FILME_RUNTIME,filme.getRuntime());
        values.put(DatabaseHelper.FILME_GENRE,filme.getGenre());
        values.put(DatabaseHelper.FILME_DIRECTOR,filme.getDirector());
        values.put(DatabaseHelper.FILME_WRITER,filme.getWriter());
        values.put(DatabaseHelper.FILME_ACTORS,filme.getActors());
        values.put(DatabaseHelper.FILME_PLOT,filme.getPlot());
        values.put(DatabaseHelper.FILME_AWARDS,filme.getAwards());
        values.put(DatabaseHelper.FILME_POSTER,filme.getPoster());
        values.put(DatabaseHelper.FILME_METASCORE,filme.getMetascore());
        values.put(DatabaseHelper.FILME_IMDBRATING,filme.getImdb_rating());

        long resultado = database.insert(DatabaseHelper.TABLE_FILME,null,values);

        return resultado;
    }

    public boolean checkFilme(String id){
        database = databaseHelper.getReadableDatabase();
        Cursor cursor = null;
        int numRow = 0;

        cursor = database.rawQuery("SELECT * FROM "+ DatabaseHelper.TABLE_FILME +" WHERE "+DatabaseHelper.FILME_ID +" LIKE '%"+id+"%'",null);

        numRow = cursor.getCount();

        if(numRow == 0){
            return false;
        }else{
            return true;
        }
    }

    public List<Filme> getAllFilmes() {

        database = databaseHelper.getReadableDatabase();

        Cursor cursor = null;
        List<Filme> filmes = new ArrayList<Filme>();
        int numRows = 0;

        cursor = database.rawQuery("SELECT "+
                DatabaseHelper.FILME_ID +","+
                DatabaseHelper.FILME_TITLE +","+
                DatabaseHelper.FILME_YEAR +","+
                DatabaseHelper.FILME_RATED +","+
                DatabaseHelper.FILME_RELEASED +","+
                DatabaseHelper.FILME_RUNTIME +","+
                DatabaseHelper.FILME_GENRE +","+
                DatabaseHelper.FILME_DIRECTOR +","+
                DatabaseHelper.FILME_WRITER +","+
                DatabaseHelper.FILME_ACTORS +","+
                DatabaseHelper.FILME_PLOT +","+
                DatabaseHelper.FILME_AWARDS +","+
                DatabaseHelper.FILME_POSTER +","+
                DatabaseHelper.FILME_METASCORE +","+
                DatabaseHelper.FILME_IMDBRATING +
                " FROM " + DatabaseHelper.TABLE_FILME,null);

        numRows = cursor.getCount();
        cursor.moveToFirst();

        for (int i=0;i<numRows;i++){
            Filme filme = new Filme();

            filme.set_id(cursor.getString(0));
            filme.setTitle(cursor.getString(1));
            filme.setYear(cursor.getString(2));
            filme.setRated(cursor.getString(3));
            filme.setReleased(cursor.getString(4));
            filme.setRuntime(cursor.getString(5));
            filme.setGenre(cursor.getString(6));
            filme.setDirector(cursor.getString(7));
            filme.setWriter(cursor.getString(8));
            filme.setActors(cursor.getString(9));
            filme.setPlot(cursor.getString(10));
            filme.setAwards(cursor.getString(11));
            filme.setPoster(cursor.getString(12));
            filme.setMetascore(cursor.getDouble(13));
            filme.setImdb_rating(cursor.getDouble(14));

            filmes.add(filme);
            cursor.moveToNext();

        }

        cursor.close();
        return filmes;
    }

}
