package com.example.duckduckhunt;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duckduckhunt.common.Constantes;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
        TextView tvName, tvTime,tvDucksNumber;
        ImageView imvDuck;
        int contador = 0;
        int anchoPantalla;
        int altoPantalla;
        Random randomNumber;
        boolean gameOver = false;
        String  id, username;
        FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        db = FirebaseFirestore.getInstance();

        iniciarViewComponents();
        eventosGame();
        iniciarPantalla();
        iniciarCuentaAtras();
        randomDuckMovement();



    }

    private void iniciarCuentaAtras() {
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                long segundosRestantes = millisUntilFinished / 1000;
                tvTime.setText(segundosRestantes + "s" );
            }

            public void onFinish() {
                tvTime.setText("0s");
                gameOver = true;
                mostrarDialogoGameOver();
                saveUserResult();
            }
        }.start();
    }

    private void saveUserResult() {

        db.collection("users").document(id).update("ducks", contador);

    }

    private void mostrarDialogoGameOver() {
        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);

// 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage("Has cazado "+ contador + "patos")
                .setTitle("Game Over");

        builder.setPositiveButton("Reiniciar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                contador = 0;
                tvDucksNumber.setText("0");
                gameOver = false;
                iniciarCuentaAtras();
                randomDuckMovement();
            }
        });
        builder.setNegativeButton("Ver Ranking", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                Intent i = new Intent(GameActivity.this, RankingActivity.class);
                startActivity(i);
            }
        });

// 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void iniciarPantalla() {
        //obtener tamaño
        Display display = getWindowManager().getDefaultDisplay();
        Point sizePoint = new Point();
        display.getSize(sizePoint);
        anchoPantalla = sizePoint.x;
        altoPantalla = sizePoint.y;
        randomNumber = new Random();

    }


    private void iniciarViewComponents() {
        tvName = findViewById(R.id.tvDucksRanking);
        tvTime =  findViewById(R.id.tvTimer);
        tvDucksNumber = findViewById(R.id.tvCounter);
        imvDuck = findViewById(R.id.imvDuck);

        Typeface typeFace = Typeface.createFromAsset(getAssets(),"pixel.ttf");
        tvName.setTypeface(typeFace);
        tvTime.setTypeface(typeFace);
        tvDucksNumber.setTypeface(typeFace);

        Bundle extras = getIntent().getExtras();
        username = extras.getString(Constantes.CONST_NAME);
        tvName.setText(username);
        id = extras.getString(Constantes.USER_ID);


    }

    private void eventosGame() {
        imvDuck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!gameOver) {
                    contador++;
                    tvDucksNumber.setText(String.valueOf(contador));

                    imvDuck.setImageResource(R.drawable.duck_clicked);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imvDuck.setImageResource(R.drawable.duck);
                            randomDuckMovement();
                        }
                    }, 500);
                }
            }
        });
    }

    private void randomDuckMovement() {
        int min = 0;
        int maximoX = anchoPantalla - imvDuck.getWidth();
        int maximoY = altoPantalla - imvDuck.getHeight();

        int randomX = randomNumber.nextInt(((maximoX - min)+1)+min);
        int randomY = randomNumber.nextInt(((maximoY - min)+1)+min);

        imvDuck.setX(randomX);
        imvDuck.setY(randomY);
    }
}
