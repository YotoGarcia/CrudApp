package com.example.crudapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crudapp.db.DbContactos;

public class NuevoActivity extends AppCompatActivity {

    EditText txtNombre, txtCorreoElectronico, txtTelefono, txtPassword;
    Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);

        txtNombre = findViewById(R.id.txtNombre);
        txtCorreoElectronico = findViewById(R.id.txtCorreoElectronico);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtPassword = findViewById(R.id.txtPassword);

        btnGuardar = findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbContactos dbContactos = new DbContactos(NuevoActivity.this);
                long id = dbContactos.insertarContacto(
                        txtNombre.getText().toString(),
                        txtCorreoElectronico.getText().toString(),
                        txtTelefono.getText().toString(),
                        txtPassword.getText().toString()
                );

                if (id > 0) {
                    Toast.makeText(NuevoActivity.this, "REGISTRO GUARDADO", Toast.LENGTH_LONG).show();
                    limpiar();
                } else {
                    Toast.makeText(NuevoActivity.this, "ERROR AL GUARDAR REGISTRO", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void limpiar() {
        txtNombre.setText("");
        txtCorreoElectronico.setText("");
        txtTelefono.setText("");
        txtPassword.setText("");
    }
}
