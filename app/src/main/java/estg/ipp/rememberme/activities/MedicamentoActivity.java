package estg.ipp.rememberme.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import estg.ipp.rememberme.R;
import estg.ipp.rememberme.database.Repository;
import estg.ipp.rememberme.models.Medicamento;
import estg.ipp.rememberme.util.Utility;

public class MedicamentoActivity extends AppCompatActivity implements View.OnTouchListener,
        GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener,View.OnClickListener, TextWatcher {

    private static final String TAG = "MedicamentoActivity";

    private static final int EDIT_MODE_ENABLED = 1;
    private static final int EDIT_MODE_DISABLED = 0;

    //ui components
    private EditText mDescricao, mEditNome, mStock, mLocal, mHora;
    private TextView mViewNome;
    private RelativeLayout mCheckContainer, mBackArrowContainer;
    private ImageButton mCheck, mBackArrow;

    //vars
    private boolean mIsNewMedicamento;
    private Medicamento mMedicamentoInicial;
    private GestureDetector mGestureDetector;
    private int mMode;
    private Repository mRepository;
    private Medicamento mMedicamentoFinal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamento);

        mDescricao = findViewById(R.id.descricao2_medicamento);
        mStock = findViewById(R.id.stock2_medicamento);
        mLocal = findViewById(R.id.local2_medicamento);
        mHora = findViewById(R.id.hora2_medicamento);

        mEditNome = findViewById(R.id.med_edit_title);
        mViewNome = findViewById(R.id.med_text_title);

        mCheck = findViewById(R.id.toolbar_check);
        mBackArrow = findViewById(R.id.toolbar_back_arrow);
        mCheckContainer = findViewById(R.id.check_container);
        mBackArrowContainer = findViewById(R.id.back_arrow_container);

        mRepository = new Repository(this);

        if(getIncomingIntent()){
            //this is a new medication (Edit mode)
            setPropriedadesNovoMedicamento();
            enableEditMode();
        }
        else{
            //this is NOT a new medication (VIEW MODE)
            setPropriedadesMedicamento();
            disableEditMode();
            disableContentInteraction();
        }

        setListeners();

    }

    //sempre que tocar na descrição , envia para OnTouchListener e dispara o onTouch
    //@SuppressLint("ClickableViewAccessibility")
    private void setListeners(){
        mDescricao.setOnTouchListener(this);
        mStock.setOnTouchListener(this);
        mLocal.setOnTouchListener(this);
        mHora.setOnTouchListener(this);
        mGestureDetector = new GestureDetector(this,this);
        mViewNome.setOnClickListener(this);
        mCheck.setOnClickListener(this);
        mBackArrow.setOnClickListener(this);
        mEditNome.addTextChangedListener(this);

    }

    private boolean getIncomingIntent(){

        if(getIntent().hasExtra("selected_med")){
            mMedicamentoInicial = getIntent().getParcelableExtra("selected_med");
            //Log.d(TAG, "getIncomingIntent:  "+ mMedicamentoInicial.toString());

            //mMedicamentoFinal = getIntent().getParcelableExtra("selected_med");
            //Log.d(TAG, "getIncomingIntent:  "+ mMedicamentoFinal.toString());

            //no update
            //se mMedicamentoFinal for alterado , mMedicamentoInicial também é alterado porque partilham a mesma posição na memória
            mMedicamentoFinal = new Medicamento();
            mMedicamentoFinal.setNome_medicamento(mMedicamentoInicial.getNome_medicamento());
            mMedicamentoFinal.setDescricao_medicamento(mMedicamentoInicial.getDescricao_medicamento());
            mMedicamentoFinal.setData_registo_medicamento(mMedicamentoInicial.getData_registo_medicamento());
            mMedicamentoFinal.setStock(mMedicamentoInicial.getStock());
            mMedicamentoFinal.setLocal_da_toma(mMedicamentoInicial.getLocal_da_toma());
            mMedicamentoFinal.setHora_da_toma(mMedicamentoInicial.getHora_da_toma());
            mMedicamentoFinal.setId(mMedicamentoInicial.getId());

            mMode = EDIT_MODE_DISABLED;
            mIsNewMedicamento = false;
            return false;
        }
        mMode = EDIT_MODE_ENABLED;
        mIsNewMedicamento = true;
        return true;

    }

    private void saveChanges(){
        if(mIsNewMedicamento){
            saveNewMedicamento();
        }else{
            updateMedicamento();
        }
    }

    private void saveNewMedicamento(){
        mRepository.insertMedicamento(mMedicamentoFinal);
    }

    private void updateMedicamento(){
        mRepository.updateMedicamento(mMedicamentoFinal);
    }

    //desativa a interação com o edittext dos campos a preencher
    private void disableContentInteraction(){
        mDescricao.setKeyListener(null);
        mDescricao.setFocusable(false);
        mDescricao.setFocusableInTouchMode(false);
        mDescricao.setCursorVisible(false);
        mDescricao.clearFocus();
        mStock.setKeyListener(null);
        mStock.setFocusable(false);
        mStock.setFocusableInTouchMode(false);
        mStock.setCursorVisible(false);
        mStock.clearFocus();
        mLocal.setKeyListener(null);
        mLocal.setFocusable(false);
        mLocal.setFocusableInTouchMode(false);
        mLocal.setCursorVisible(false);
        mLocal.clearFocus();
        mHora.setKeyListener(null);
        mHora.setFocusable(false);
        mHora.setFocusableInTouchMode(false);
        mHora.setCursorVisible(false);
        mHora.clearFocus();

    }

    //ativa a interação com o edittext dos campos a preencher
    private void enableContentInteraction(){
        mDescricao.setKeyListener(new EditText(this).getKeyListener());
        mDescricao.setFocusable(true);
        mDescricao.setFocusableInTouchMode(true);
        mDescricao.setCursorVisible(true);
        mDescricao.requestFocus();
        mStock.setKeyListener(new EditText(this).getKeyListener());
        mStock.setFocusable(true);
        mStock.setFocusableInTouchMode(true);
        mStock.setCursorVisible(true);
        mStock.requestFocus();
        mLocal.setKeyListener(new EditText(this).getKeyListener());
        mLocal.setFocusable(true);
        mLocal.setFocusableInTouchMode(true);
        mLocal.setCursorVisible(true);
        mLocal.requestFocus();
        mHora.setKeyListener(new EditText(this).getKeyListener());
        mHora.setFocusable(true);
        mHora.setFocusableInTouchMode(true);
        mHora.setCursorVisible(true);
        mHora.requestFocus();

    }

    private void enableEditMode(){
        mBackArrowContainer.setVisibility(View.GONE);
        mCheckContainer.setVisibility(View.VISIBLE);

        mViewNome.setVisibility(View.GONE);
        mEditNome.setVisibility(View.VISIBLE);

        mMode = EDIT_MODE_ENABLED;

        enableContentInteraction();
    }

    private void disableEditMode(){
        mBackArrowContainer.setVisibility(View.VISIBLE);
        mCheckContainer.setVisibility(View.GONE);

        mViewNome.setVisibility(View.VISIBLE);
        mEditNome.setVisibility(View.GONE);

        mMode = EDIT_MODE_DISABLED;

        disableContentInteraction();

        hideSoftKeyboard();


        //verifica se alterou o medicamento , nao guarda se nao alterar
        //só altera se editar o titulo e a descrição para não ficar em branco
        String temp = mDescricao.getText().toString();
        //replace new lines with blankspace
        temp = temp.replace("\n", "");
        //replace blankspace for nothing
        temp = temp.replace(" ", "");

        String temp1 = mLocal.getText().toString();
        //replace new lines with blankspace
        temp1 = temp1.replace("\n", "");
        //replace blankspace for nothing
        temp1 = temp1.replace(" ", "");

        String temp2 = mStock.getText().toString();
        //replace new lines with blankspace
        temp2 = temp2.replace("\n", "");
        //replace blankspace for nothing
        temp2 = temp2.replace(" ", "");

        String temp3 = mHora.getText().toString();
        //replace new lines with blankspace
        temp3 = temp3.replace("\n", "");
        //replace blankspace for nothing
        temp3 = temp3.replace(" ", "");


        if((temp.length() > 0) && (temp1.length() > 0)&& (temp2.length() > 0)&& (temp3.length() > 0)) {
            mMedicamentoFinal.setNome_medicamento(mEditNome.getText().toString());
            mMedicamentoFinal.setDescricao_medicamento(mDescricao.getText().toString());
            mMedicamentoFinal.setStock(mStock.getText().toString());
            mMedicamentoFinal.setLocal_da_toma(mLocal.getText().toString());
            mMedicamentoFinal.setHora_da_toma(mHora.getText().toString());

            //define data de registo do medicamento
            String data = Utility.getCurrentData();
            //mMedicamentoFinal.setData(date);
            //String data = "Fevereiro 2020";
            mMedicamentoFinal.setData_registo_medicamento(data);

            //se o medicamento for alterado guarda-o
            if(!mMedicamentoFinal.getDescricao_medicamento().equals(mMedicamentoInicial.getDescricao_medicamento()) ||
                    !mMedicamentoFinal.getNome_medicamento().equals(mMedicamentoInicial.getNome_medicamento()) ||
                    !mMedicamentoFinal.getStock().equals(mMedicamentoInicial.getStock()) ||
                    !mMedicamentoFinal.getHora_da_toma().equals(mMedicamentoInicial.getHora_da_toma()) ||
                    !mMedicamentoFinal.getLocal_da_toma().equals(mMedicamentoInicial.getLocal_da_toma())){
                Log.d(TAG, "disableEditMode: called!!");
                saveChanges();
            }
        }

    }

    private void setPropriedadesNovoMedicamento(){
        mViewNome.setText("Nome do medicamento");
        mEditNome.setText("Nome do medicamento");

        mMedicamentoInicial = new Medicamento();
        mMedicamentoFinal = new Medicamento();
        mMedicamentoInicial.setNome_medicamento("Nome do medicamento");
        mMedicamentoFinal.setNome_medicamento("Nome do medicamento");

    }

    private void setPropriedadesMedicamento(){
        mViewNome.setText(mMedicamentoInicial.getNome_medicamento());
        mEditNome.setText(mMedicamentoInicial.getNome_medicamento());
        mDescricao.setText(mMedicamentoInicial.getDescricao_medicamento());
        mStock.setText(mMedicamentoInicial.getStock());
        mLocal.setText(mMedicamentoInicial.getLocal_da_toma());
        mHora.setText(mMedicamentoInicial.getHora_da_toma());

    }

    //the touch event vai ser interceptado pelo Ontouchlistener, quando se toca na descrição
    //o touch event is set to ontouchlistener, them the motion event is sent to gesture detector,
    //wich has all the methods..

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        return mGestureDetector.onTouchEvent(motionEvent);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        Log.d(TAG, "onDoubleTap: double tapped!");
        enableEditMode();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    //esconder o ecra de texto quando se desativa o editmode
    private void hideSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if(view == null){
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(),0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toolbar_check:{
                hideSoftKeyboard();
                disableEditMode();
                break;
            }
            case R.id.med_text_title:{
                enableEditMode();
                mEditNome.requestFocus();//foca no titulo
                mEditNome.setSelection(mEditNome.length());//cursor vai para o final da string
                break;
            }
            case R.id.toolbar_back_arrow:{
                finish();
                break;
            }
        }
    }

    //qd esta no modo editar se carregar na seta do tlm assume a do check da toolbar
    @Override
    public void onBackPressed() {
        if(mMode == EDIT_MODE_ENABLED){
            onClick(mCheck);
        }
        else{
            super.onBackPressed();
        }
    }

    //é chamado qd a atividade fica no Onpause,guarda o estado da atividade
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mode", mMode);
    }

    //quando a actividade é recriada vai buscar o que guardou no onSaveInstanceState
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMode = savedInstanceState.getInt("mode");
        if(mMode == EDIT_MODE_ENABLED) {
            enableEditMode();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    //quando muda o titulo e carrega confirmar, o titulo do medicamento muda logo
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mViewNome.setText(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
