package com.example.crudapp;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.crudapp.adaptadores.ListaContactosAdapter;
import com.example.crudapp.db.DbContactos;
import com.example.crudapp.entidades.Contactos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView txtBuscar;
    RecyclerView listaContactos;
    ArrayList<Contactos> listaArrayContactos;
    FloatingActionButton fabNuevo;
    ListaContactosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtBuscar = findViewById(R.id.txtBuscar);
        listaContactos = findViewById(R.id.listaContactos);
        listaContactos.setLayoutManager(new LinearLayoutManager(this));

        DbContactos dbContactos = new DbContactos(MainActivity.this);

        listaArrayContactos = new ArrayList<>();

        adapter = new ListaContactosAdapter(dbContactos.mostrarContactos());
        listaContactos.setAdapter(adapter);

        txtBuscar.setOnQueryTextListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_principal, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int[] menuItems = {R.id.menuNuevo};

        for (int menuItem : menuItems) {
            if (item.getItemId() == menuItem) {
                if (menuItem == R.id.menuNuevo) {
                    nuevoRegistro();
                    return true;
                }
            }
        }

        return super.onOptionsItemSelected(item);
    }


    private void nuevoRegistro(){
        Intent intent = new Intent(this, NuevoActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

}