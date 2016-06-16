package com.myapp.linykerramos.omdbapp.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Linyker Ramos on 14/06/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String BANCO_DADOS = "OMDB_db";
    private static int VERSAO = 1;

    //nome da tabela no banco
    static final String TABLE_FILME = "filme";

    //colunas da tabela filmes
    public static final String FILME_ID = "_id";
    public static final String FILME_TITLE = "title";
    public static final String FILME_YEAR = "year";
    public static final String FILME_RATED = "rated";
    public static final String FILME_RELEASED = "released";
    public static final String FILME_RUNTIME = "runtime";
    public static final String FILME_GENRE = "genre";
    public static final String FILME_DIRECTOR = "director";
    public static final String FILME_WRITER = "writer";
    public static final String FILME_ACTORS = "actors";
    public static final String FILME_PLOT = "plot";
    public static final String FILME_AWARDS = "awards";
    public static final String FILME_POSTER = "poster";
    public static final String FILME_METASCORE = "metascore";
    public static final String FILME_IMDBRATING = "imdb_rating";

    //script criação tabela filmes
    private static final String CREATE_TABLE_FILMES = "CREATE TABLE " +TABLE_FILME +"("+
            FILME_ID + " TEXT PRIMARY KEY,"+
            FILME_TITLE + " TEXT," +
            FILME_YEAR + " TEXT," +
            FILME_RATED + " TEXT," +
            FILME_RELEASED + " TEXT," +
            FILME_RUNTIME + " TEXT," +
            FILME_GENRE + " TEXT," +
            FILME_DIRECTOR + " TEXT," +
            FILME_WRITER + " TEXT," +
            FILME_ACTORS + " TEXT," +
            FILME_PLOT + " TEXT," +
            FILME_AWARDS + " TEXT," +
            FILME_POSTER + " TEXT," +
            FILME_METASCORE + " DOUBLE," +
            FILME_IMDBRATING +" DOUBLE" +
            ");";

    public DatabaseHelper(Context context){
        super(context,BANCO_DADOS,null,VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FILMES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
