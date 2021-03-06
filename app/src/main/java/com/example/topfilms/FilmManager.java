package com.example.topfilms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class FilmManager {
    private static final String TABLE_NAME = "film";
    public static final String KEY_ID_FILM="id_film";
    public static final String KEY_TITRE_FILM="titre_film";
    public static final String KEY_RESUME_FILM="synopsys_film";
    public static final String KEY_NOTE_FILM="note_film";
    public static final String KEY_DATE_FILM="date_film";
    public static final String KEY_IMAGE_FILM="image_film";
    public static final String KEY_TOSEE_FILM="tosee";
    public static final String KEY_SEEN_FILM="seen";
    public static final String KEY_TRAILER_FILM="trailer";
    public static final String CREATE_TABLE_FILM = "CREATE TABLE "+TABLE_NAME+
            " (" +
            " "+KEY_ID_FILM+" INTEGER primary key," +
            " "+KEY_TITRE_FILM+" TEXT," +
            " "+KEY_RESUME_FILM+" TEXT," +
            " "+KEY_NOTE_FILM+" REAL," +
            " "+KEY_DATE_FILM+" TEXT," +
            " "+KEY_IMAGE_FILM+" TEXT," +
            " "+KEY_TOSEE_FILM+" INTEGER," +
            " "+KEY_SEEN_FILM+" INTEGER,"+
            " "+KEY_TRAILER_FILM+" TEXT"+
            ");";
    private MySQLite maBaseSQLite;
    private SQLiteDatabase db;

    public FilmManager(Context context)
    {
        maBaseSQLite = MySQLite.getInstance(context);
    }
    public void open()
    {
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close()
    {
        db.close();
    }
    public long addFilm(Film film) {
        //ajout d'un enregistrement
        ContentValues values = new ContentValues();
        values.put(KEY_ID_FILM,film.getId());
        values.put(KEY_TITRE_FILM, film.getTitre());
        values.put(KEY_RESUME_FILM,film.getSynopsys());
        values.put(KEY_NOTE_FILM,film.getNote());
        values.put(KEY_DATE_FILM,film.getDate_sortie());
        values.put(KEY_IMAGE_FILM,film.getImage());
        values.put(KEY_TOSEE_FILM,film.getTosee());
        values.put(KEY_SEEN_FILM,film.getSeen());
        values.put(KEY_TRAILER_FILM,film.getTrailer());
        return db.insert(TABLE_NAME,null,values);
    }
    public int modFilm(Film film) {
        // modification d'un enregistrement
        ContentValues values = new ContentValues();
        values.put(KEY_TITRE_FILM, film.getTitre());
        values.put(KEY_RESUME_FILM,film.getSynopsys());
        values.put(KEY_NOTE_FILM,film.getNote());
        values.put(KEY_DATE_FILM,film.getDate_sortie());
        values.put(KEY_IMAGE_FILM,film.getImage());
        values.put(KEY_TOSEE_FILM,film.getTosee());
        values.put(KEY_SEEN_FILM,film.getSeen());
        values.put(KEY_TRAILER_FILM,film.getTrailer());
        String where = KEY_ID_FILM+" = ?";
        String[] whereArgs = {film.getId()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }
    public int supFilm(Film film) {
        // suppression d'un enregistrement
        String where = KEY_ID_FILM+" = ?";
        String[] whereArgs = {film.getId()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }
    public Film getFilm(int id) {
        // Retourne le film dont l'id est passé en paramètre

        Film a=new Film(0,"","",0,"","",0,0,"");

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_FILM+"="+id, null);
        if (c.moveToFirst()) {
            a.setId(c.getInt(c.getColumnIndex(KEY_ID_FILM)));
            a.setTitre(c.getString(c.getColumnIndex(KEY_TITRE_FILM)));
            a.setSynopsys(c.getString(c.getColumnIndex(KEY_RESUME_FILM)));
            a.setNote(c.getFloat(c.getColumnIndex(KEY_NOTE_FILM)));
            a.setDate_sortie(c.getString(c.getColumnIndex(KEY_DATE_FILM)));
            a.setImage(c.getString(c.getColumnIndex(KEY_IMAGE_FILM)));
            a.setTosee(c.getInt(c.getColumnIndex(KEY_TOSEE_FILM)));
            a.setSeen(c.getInt(c.getColumnIndex(KEY_SEEN_FILM)));
            a.setTrailer(c.getString(c.getColumnIndex(KEY_TRAILER_FILM)));
            c.close();
        }

        return a;
    }

    public Boolean Checkfilm(int id){
        //Verification de l'éxistence d'un film dont l'id est passé en paramètre
        Film a=new Film(0,"","",0,"","",0,0,"");

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_FILM+"="+id, null);

        if (c.moveToFirst()) {
            return true;
        }else{
            return false;
        }

    }

    public Cursor getLiked(){
        //récuperation des films coups de coeur
        return db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_SEEN_FILM+"=1",null);
    }

    public Cursor getSee(){
        //récuperation des films Vus
        return db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_TOSEE_FILM+"=1",null);
    }
    public Cursor getFilms() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

}

