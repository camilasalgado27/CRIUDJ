package com.example.criudj;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText editTextNombre, editTextTelefono;
    private Button buttonAgregar;
    private ListView listView;
    private ArrayList<String> contactos;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //

        editTextNombre = findViewById(R.id.editTextName);
        editTextTelefono = findViewById(R.id.editTextPhone);
        buttonAgregar = findViewById(R.id.buttonAdd);
        listView = findViewById(R.id.listView);

        contactos = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactos);
        listView.setAdapter(adapter);

        buttonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarContacto();
            }
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            editarContacto(position);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            eliminarContacto(position);
            return true;
        });
    }

    private void agregarContacto() {
        String nombre = editTextNombre.getText().toString().trim();
        String telefono = editTextTelefono.getText().toString().trim();

        if (!nombre.isEmpty() && !telefono.isEmpty()) {
            contactos.add(nombre + " - " + telefono);
            adapter.notifyDataSetChanged();
            editTextNombre.setText("");
            editTextTelefono.setText("");
        } else {
            Toast.makeText(this, "Por favor, llena todos los campos.", Toast.LENGTH_SHORT).show();
        }
    }

    private void editarContacto(int position) {
        String contacto = contactos.get(position);
        if (contacto.contains(" - ")) {  //
            String[] partes = contacto.split(" - ");
            editTextNombre.setText(partes[0]);
            editTextTelefono.setText(partes[1]);
            contactos.remove(position);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Formato de contacto incorrecto.", Toast.LENGTH_SHORT).show();
        }
    }

    private void eliminarContacto(int position) {
        contactos.remove(position);
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Contacto eliminado.", Toast.LENGTH_SHORT).show();
    }
}