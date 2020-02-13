package estg.ipp.rememberme.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Arrays;
import java.util.Collections;

import estg.ipp.rememberme.R;

public class Games_Activity extends AppCompatActivity {

    TextView textViewP1, textViewP2;

    ImageView iv_11, iv_12,iv_13,iv_14,iv_21,iv_22,iv_23,iv_24,iv_31,iv_32,iv_33,iv_34;

    //array de imagens
    Integer[] imagesArray = {101, 102, 103, 104, 105, 106, 201,202,203,204,205,206};

    int image101,image102,image103,image104,image105,image106,image201,image202,image203,image204,
    image205, image206;

    int firstImage, secondImage;
    int clickFirst, clickSecond;
    int cardNumber =1;

    int turn = 1;
    int playerPoints = 0, cpuPoints =0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_);

        Toolbar toolbar = findViewById(R.id.toolbar_game);
        toolbar.setTitle("Jogo da memória");

        textViewP1 = findViewById(R.id.textViewP1);
        textViewP2 = findViewById(R.id.textViewP2);

        iv_11 = findViewById(R.id.iv_11);
        iv_12 = findViewById(R.id.iv_12);
        iv_13 = findViewById(R.id.iv_13);
        iv_14 = findViewById(R.id.iv_14);
        iv_21 = findViewById(R.id.iv_21);
        iv_22 = findViewById(R.id.iv_22);
        iv_23 = findViewById(R.id.iv_23);
        iv_24 = findViewById(R.id.iv_24);
        iv_31 = findViewById(R.id.iv_31);
        iv_32 = findViewById(R.id.iv_32);
        iv_33 = findViewById(R.id.iv_33);
        iv_34 = findViewById(R.id.iv_34);

        iv_11.setTag("0");
        iv_12.setTag("1");
        iv_13.setTag("2");
        iv_14.setTag("3");
        iv_21.setTag("4");
        iv_22.setTag("5");
        iv_23.setTag("6");
        iv_24.setTag("7");
        iv_31.setTag("8");
        iv_32.setTag("9");
        iv_33.setTag("10");
        iv_34.setTag("11");

        //carrega cartas
        frontOfCardsResources();

        //embaralhar cartas
        Collections.shuffle(Arrays.asList(imagesArray));

        //muda cor do 2o jogador
        textViewP1.setTextColor(Color.GRAY);

        iv_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_11, theCard);
            }
         });

        iv_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_12, theCard);
            }
        });

        iv_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_13, theCard);

            }
        });

        iv_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_14, theCard);

            }
        });

        iv_21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_21, theCard);

            }
        });

        iv_22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_22, theCard);

            }
        });

        iv_23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_23, theCard);

            }
        });

        iv_24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_24, theCard);

            }
        });

        iv_31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_31, theCard);

            }
        });

        iv_32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_32, theCard);

            }
        });

        iv_33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_33, theCard);

            }
        });

        iv_34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int theCard = Integer.parseInt((String) v.getTag());
                doStuff(iv_34, theCard);

            }
        });


    }

    private void doStuff(ImageView iv, int card){

        //set da imagem correta para a imageview
        if(imagesArray[card] == 101){
            iv.setImageResource(image101);
        } else if(imagesArray[card] == 102){
            iv.setImageResource(image102);
        }else if(imagesArray[card] == 103){
            iv.setImageResource(image103);
        }else if(imagesArray[card] == 104){
            iv.setImageResource(image104);
        }else if(imagesArray[card] == 105){
            iv.setImageResource(image105);
        }else if(imagesArray[card] == 106){
            iv.setImageResource(image106);
        }else if(imagesArray[card] == 201){
            iv.setImageResource(image201);
        }else if(imagesArray[card] == 202){
            iv.setImageResource(image202);
        }else if(imagesArray[card] == 203){
            iv.setImageResource(image203);
        }else if(imagesArray[card] == 204){
            iv.setImageResource(image204);
        }else if(imagesArray[card] == 205){
            iv.setImageResource(image205);}
        else if(imagesArray[card] == 206) {
            iv.setImageResource(image206);
        }

        //checa qual carta é selecionada e guarda em var temporaria
        if(cardNumber ==1){
            firstImage = imagesArray[card];
            if(firstImage > 200){
                firstImage = firstImage -100;
            }
            cardNumber = 2;
            clickFirst = card;
            iv.setEnabled(false);
        }else if(cardNumber == 2){
            secondImage = imagesArray[card];
            if(secondImage > 200){
                secondImage = secondImage -100;
            }
            cardNumber =1;
            clickSecond = card;

            iv_11.setEnabled(false);
            iv_12.setEnabled(false);
            iv_13.setEnabled(false);
            iv_14.setEnabled(false);
            iv_21.setEnabled(false);
            iv_22.setEnabled(false);
            iv_23.setEnabled(false);
            iv_24.setEnabled(false);
            iv_31.setEnabled(false);
            iv_32.setEnabled(false);
            iv_33.setEnabled(false);
            iv_34.setEnabled(false);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //checa se cartas selecionadas sao iguais
                   calculate();
                }
            },1000);
        }
    }

    private void calculate(){
        //se as cartas sao iguais remove e adiciona ponto
        if(firstImage == secondImage) {
            if (clickFirst == 0) {
                iv_11.setVisibility(View.INVISIBLE);
            } else if (clickFirst == 1) {
                iv_12.setVisibility(View.INVISIBLE);
            } else if (clickFirst == 2) {
                iv_13.setVisibility(View.INVISIBLE);
            } else if (clickFirst == 3) {
                iv_14.setVisibility(View.INVISIBLE);
            } else if (clickFirst == 4) {
                iv_21.setVisibility(View.INVISIBLE);
            } else if (clickFirst == 5) {
                iv_22.setVisibility(View.INVISIBLE);
            } else if (clickFirst == 6) {
                iv_23.setVisibility(View.INVISIBLE);
            } else if (clickFirst == 7) {
                iv_24.setVisibility(View.INVISIBLE);
            } else if (clickFirst == 8) {
                iv_31.setVisibility(View.INVISIBLE);
            } else if (clickFirst == 9) {
                iv_32.setVisibility(View.INVISIBLE);
            } else if (clickFirst == 10) {
                iv_33.setVisibility(View.INVISIBLE);
            } else if (clickFirst == 11) {
                iv_34.setVisibility(View.INVISIBLE);
            }

            if (clickSecond == 0) {
                iv_11.setVisibility(View.INVISIBLE);
            } else if (clickSecond == 1) {
                iv_12.setVisibility(View.INVISIBLE);
            } else if (clickSecond == 2) {
                iv_13.setVisibility(View.INVISIBLE);
            } else if (clickSecond == 3) {
                iv_14.setVisibility(View.INVISIBLE);
            } else if (clickSecond == 4) {
                iv_21.setVisibility(View.INVISIBLE);
            } else if (clickSecond == 5) {
                iv_22.setVisibility(View.INVISIBLE);
            } else if (clickSecond == 6) {
                iv_23.setVisibility(View.INVISIBLE);
            } else if (clickSecond == 7) {
                iv_24.setVisibility(View.INVISIBLE);
            } else if (clickSecond == 8) {
                iv_31.setVisibility(View.INVISIBLE);
            } else if (clickSecond == 9) {
                iv_32.setVisibility(View.INVISIBLE);
            } else if (clickSecond == 10) {
                iv_33.setVisibility(View.INVISIBLE);
            } else if (clickSecond == 11) {
                iv_34.setVisibility(View.INVISIBLE);
            }

            //adiciona pontos ao jogador
            if (turn == 1) {
                playerPoints++;
                textViewP1.setText("P1: " + playerPoints);
            } else if (turn == 2) {
                cpuPoints++;
                textViewP2.setText("P2: " + cpuPoints);
            }
        }else {
            iv_11.setImageResource(R.drawable.ic0);
            iv_12.setImageResource(R.drawable.ic0);
            iv_13.setImageResource(R.drawable.ic0);
            iv_14.setImageResource(R.drawable.ic0);

            iv_21.setImageResource(R.drawable.ic0);
            iv_22.setImageResource(R.drawable.ic0);
            iv_23.setImageResource(R.drawable.ic0);
            iv_24.setImageResource(R.drawable.ic0);

            iv_31.setImageResource(R.drawable.ic0);
            iv_32.setImageResource(R.drawable.ic0);
            iv_33.setImageResource(R.drawable.ic0);
            iv_34.setImageResource(R.drawable.ic0);

            //muda vez do jogador
            if(turn ==1){
                turn =2 ;
                textViewP1.setTextColor(Color.GRAY);
                textViewP2.setTextColor(Color.BLACK);
            }else if(turn == 2){
                turn =1;
                textViewP2.setTextColor(Color.GRAY);
                textViewP1.setTextColor(Color.BLACK);
            }
        }

        iv_11.setEnabled(true);
        iv_12.setEnabled(true);
        iv_13.setEnabled(true);
        iv_14.setEnabled(true);
        iv_21.setEnabled(true);
        iv_22.setEnabled(true);
        iv_23.setEnabled(true);
        iv_24.setEnabled(true);
        iv_31.setEnabled(true);
        iv_32.setEnabled(true);
        iv_33.setEnabled(true);
        iv_34.setEnabled(true);

        //ve se é fim do jogo
        checkEnd();

    }

    private void checkEnd(){
        if(iv_11.getVisibility() == View.INVISIBLE &&
                iv_12.getVisibility() == View.INVISIBLE &&
                iv_13.getVisibility() == View.INVISIBLE &&
                iv_14.getVisibility() == View.INVISIBLE &&
                iv_21.getVisibility() == View.INVISIBLE &&
                iv_22.getVisibility() == View.INVISIBLE &&
                iv_23.getVisibility() == View.INVISIBLE &&
                iv_24.getVisibility() == View.INVISIBLE &&
                iv_31.getVisibility() == View.INVISIBLE &&
                iv_32.getVisibility() == View.INVISIBLE &&
                iv_33.getVisibility() == View.INVISIBLE &&
                iv_34.getVisibility() == View.INVISIBLE ) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Games_Activity.this);
            alertDialogBuilder.setMessage("GAME OVER\nP1: " + playerPoints + "\nP2: "+cpuPoints).
                    setCancelable(false)
                    .setPositiveButton("Novo Jogo", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getApplicationContext(), Games_Activity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("Sair do jogo", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }

    }

    private void frontOfCardsResources(){
        image101 = R.drawable.ic1;
        image102 = R.drawable.ic2;
        image103 = R.drawable.ic3;
        image104 = R.drawable.ic4;
        image105 = R.drawable.ic5;
        image106 = R.drawable.ic6;
        image201 = R.drawable.ic7;
        image202 = R.drawable.ic8;
        image203 = R.drawable.ic9;
        image204 = R.drawable.ic10;
        image205 = R.drawable.ic11;
        image206 = R.drawable.ic12;

    }




}
