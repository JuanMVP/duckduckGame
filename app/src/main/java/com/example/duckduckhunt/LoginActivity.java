package com.example.duckduckhunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.duckduckhunt.common.Constantes;
import com.example.duckduckhunt.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
            EditText etUsername;
            Button btnStart;
            String name;
            FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //conexion db
        db = FirebaseFirestore.getInstance();

        etUsername = findViewById(R.id.etUsername);
        btnStart = findViewById(R.id.btnStart);


        Typeface typeFace = Typeface.createFromAsset(getAssets(),"pixel.ttf");
        etUsername.setTypeface(typeFace);
        btnStart.setTypeface(typeFace);


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etUsername.getText().toString();

                if(name.isEmpty()){
                    etUsername.setError("El nombre de usuario es obligatorio");
                }else if(name.length() < 4) {
                    etUsername.setError("El nombre debe de tener al menos 4 caracteres");
                }else{

                    addNickAndStart();
                }
            }
        });

    }

    private void addNickAndStart() {
        db.collection("users").whereEqualTo("name", name)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if( queryDocumentSnapshots.size() > 0){
                    etUsername.setError("El nombre ya existe");
                }else{
                    addNameToFireStore();
                }
            }
        });


    }

    private void addNameToFireStore() {
        db.collection("users").add(new User(name,0)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                etUsername.setText("");
                Intent i = new Intent(LoginActivity.this, GameActivity.class);
                i.putExtra(Constantes.CONST_NAME, name);
                i.putExtra(Constantes.USER_ID,documentReference.getId());
                startActivity(i);
            }
        });
    }
}
