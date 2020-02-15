package estg.ipp.rememberme.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import estg.ipp.rememberme.R;
import estg.ipp.rememberme.models.Question;
import estg.ipp.rememberme.sql.GameDbHelper;

public class AritmeticGameActivity extends AppCompatActivity {

    public static final String EXTRA_SCORE = "extraScore";
    private static final long COUNTDOWN_IN_MILLIS = 30000;

    private static final String KEY_SCORE = "keyscore";
    private static final String KEY_QUESTION_COUNT = "keyQuestionCount";
    private static final String KEY_MILLIS_LEFT = "keyMillisLeft";
    private static final String KEY_ANSWERED = "keyAnswered";
    private static final String KEY_QUESTION_LIST = "keyQuestionList";


    private TextView textViewQuestion, textViewScore, textViewQuestionCount, textViewCountDown, textViewDificulty;
    private RadioGroup rbGroup;
    private RadioButton rb1, rb2, rb3;
    private Button buttonConfirmNext;

    private ColorStateList textColorDefaultRb;
    private ColorStateList textColorDefaultCountDown;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private ArrayList<Question> questionList;
    private int questionCounter, questionCountTotal, score;
    private Question currentQuestion;
    private boolean answered;

    private long backPressedTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aritmetic_game);

        Toolbar toolbar = findViewById(R.id.toolbar_aritmetic_game);
        toolbar.setTitle("Jogo aritmético");

        textViewQuestion = findViewById(R.id.text_view_question);
        textViewScore = findViewById(R.id.text_view_score);
        textViewQuestionCount = findViewById(R.id.text_view_question_count);
        textViewDificulty = findViewById(R.id.text_view_dificulty);
        textViewCountDown = findViewById(R.id.text_view_countdown);
        rbGroup = findViewById(R.id.radio_group);
        rb1 = findViewById(R.id.radio_button1);
        rb2 = findViewById(R.id.radio_button2);
        rb3 = findViewById(R.id.radio_button3);
        buttonConfirmNext = findViewById(R.id.btn_confirmar);

        textColorDefaultRb = rb1.getTextColors();
        textColorDefaultCountDown = textViewCountDown.getTextColors();

        Intent intent = getIntent();
        String dificulty = intent.getStringExtra(AritmeticGameMenuActivity.EXTRA_DIFICULTY);

        textViewDificulty.setText("Nível: " +dificulty);

        if(savedInstanceState == null) {
            GameDbHelper dbHelper = new GameDbHelper(this);
            questionList = dbHelper.getQuestions(dificulty);
            questionCountTotal = questionList.size();
            Collections.shuffle(questionList);

            showNextQuestion();
        }else {
            questionList = savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST);
            questionCountTotal = questionList.size();
            questionCounter = savedInstanceState.getInt(KEY_QUESTION_COUNT);
            currentQuestion=questionList.get(questionCounter-1);
            score = savedInstanceState.getInt(KEY_SCORE);
            timeLeftInMillis = savedInstanceState.getLong(KEY_MILLIS_LEFT);
            answered = savedInstanceState.getBoolean(KEY_ANSWERED);

            if(!answered){
                startCountDown();

            }else{
                updateCountDownText();
                showSolution();
            }
        }

        buttonConfirmNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!answered){
                    //confirmar que houve uma resposta selecionada
                    if(rb1.isChecked() || rb2.isChecked() || rb3.isChecked()) {
                        checkAnswer();
                    } else {
                        Toast.makeText(AritmeticGameActivity.this, "Por favor selecione uma resposta", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextQuestion();
                }
            }
        });
    }

    private void showNextQuestion(){
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);

        rbGroup.clearCheck();

        //só podemos mostrar a proxima pergunta se realmente tivermos mais perguntas
        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());

            questionCounter++;
            textViewQuestionCount.setText("Pergunta: "+ questionCounter + "/"+ questionCountTotal);
            answered = false;//para bloquear a resposta antes de seguir para a proxima
            buttonConfirmNext.setText("Confirmar resposta");

            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();
        }else{
            finishGame();
        }
    }

    private void startCountDown(){
        countDownTimer = new CountDownTimer(timeLeftInMillis,1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMillis = l;
                updateCountDownText();
            }

            @Override
            public void onFinish() {

                timeLeftInMillis =0;
                updateCountDownText();
                checkAnswer();

            }
        }.start();
    }

    private void updateCountDownText(){
        int minutos = (int) (timeLeftInMillis/1000)/60;//obter minutos
        int segundos = (int) (timeLeftInMillis/1000)%60;
        //só obtemos o que sobra depois de dividir por 60, porque só queremos os segundos que sobram

        String tempoFormatado = String.format(Locale.getDefault(), "%02d:%02d", minutos,segundos);

        textViewCountDown.setText(tempoFormatado);

        if(timeLeftInMillis < 10000){
            textViewCountDown.setTextColor(Color.RED);
        }else {
            textViewCountDown.setTextColor(textColorDefaultCountDown);
        }
    }

    private void checkAnswer(){
        answered = true;

        countDownTimer.cancel();//se escolhermos uma resposta , queremos que o cronometro pare

        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbSelected) + 1;

        if(answerNr == currentQuestion.getAnswerNr()){
            score++;
            textViewScore.setText("Pontuação: " + score);
        }

        showSolution();
    }

    private void showSolution(){
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);

        switch (currentQuestion.getAnswerNr()){
            case 1:
                rb1.setTextColor(Color.GREEN);
                textViewQuestion.setText("A alínea A é a correta!");
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                textViewQuestion.setText("A alínea B é a correta!");
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                textViewQuestion.setText("A alínea C é a correta!");
                break;
        }

        if(questionCounter < questionCountTotal){
            buttonConfirmNext.setText("Próxima");
        }else{
            buttonConfirmNext.setText("Finalizar");
        }

    }

    private void finishGame(){
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    //só sai da atividade se pressionar 2x o botão voltar,para caso o utilizador se engane e carregue 1x
    //no intervalo de 2 segundos
    @Override
    public void onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            finishGame();
        } else {
            Toast.makeText(this, "Pressione outra vez para sair do jogo", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SCORE, score);
        outState.putInt(KEY_QUESTION_COUNT, questionCounter);
        outState.putLong(KEY_MILLIS_LEFT, timeLeftInMillis);
        outState.putBoolean(KEY_ANSWERED, answered);
        outState.putParcelableArrayList(KEY_QUESTION_LIST, questionList);

    }
}
