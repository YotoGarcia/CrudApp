package com.example.crudapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.crudapp.entidades.Contactos;

import java.util.ArrayList;

public class DbContactos extends DbHelper {

    Context context;
    public DbContactos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarContacto(String nombre, String correo_electronico, String telefono, String password ) {

        long id = 0;

        try {
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("correo_electronico", correo_electronico);
            values.put("telefono", telefono);
            values.put("password", password);

            id = db.insert(TABLE_CONTACTOS, null, values);

            return id;
        } catch(Exception ex){
            ex.toString();
        }

        return id;
    }

    public ArrayList<Contactos> mostrarContactos(){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<Contactos> listaContactos = new ArrayList<>();
        Contactos contacto = null;
        Cursor cursorContactos = null;

        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS, null );

        if(cursorContactos.moveToFirst()){
            do{
                contacto = new Contactos();
                contacto.setId(cursorContactos.getInt(0));
                contacto.setNombre(cursorContactos.getString(1));
                contacto.setCorreo_electronico(cursorContactos.getString(2));
                contacto.setTelefono(cursorContactos.getString(3));
                contacto.setPassword(cursorContactos.getString(4));
                listaContactos.add(contacto);
            } while (cursorContactos.moveToNext());
        }

        cursorContactos.close();

        return listaContactos;
    }

    public Contactos verContacto(int id){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Contactos contacto = null;
        Cursor cursorContactos;

        cursorContactos = db.rawQuery("SELECT * FROM " + TABLE_CONTACTOS + " WHERE id = " + id + " LIMIT 1", null );

        if(cursorContactos.moveToFirst()){

            contacto = new Contactos();
            contacto.setId(cursorContactos.getInt(0));
            contacto.setNombre(cursorContactos.getString(1));
            contacto.setCorreo_electronico(cursorContactos.getString(2));
            contacto.setTelefono(cursorContactos.getString(3));
            contacto.setPassword(cursorContactos.getString(4));
        }

        cursorContactos.close();

        return contacto;
    }

    public boolean editarContacto(int id, String nombre, String correo_electronico, String telefono) {
        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            String sql = "UPDATE " + TABLE_CONTACTOS +
                    " SET nombre = ?, telefono = ?, correo_electronico = ?" +
                    "WHERE id = ?";

            SQLiteStatement statement = db.compileStatement(sql);
            statement.bindString(1, nombre);
            statement.bindString(2, telefono);
            statement.bindString(3, correo_electronico);
            statement.bindLong(4, id);

            statement.executeUpdateDelete();

            correcto = true;
        } catch (Exception ex) {
            Log.e("DB_ERROR", ex.toString());
            correcto = false;
        } finally {
            db.close();
        }
        return correcto;
    }


    public boolean eliminarContacto(int id ) {

        boolean correcto = false;

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {
            db.execSQL("DELETE FROM " + TABLE_CONTACTOS + " WHERE id = '" + id + "'");
            correcto = true;

        } catch(Exception ex) {
            ex.toString();
            correcto = false;
        } finally {
            db.close();
        }
        return correcto;
    }
}
