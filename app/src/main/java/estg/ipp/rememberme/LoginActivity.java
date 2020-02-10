package estg.ipp.rememberme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.squareup.picasso.Picasso;

public class LoginActivity extends AppCompatActivity {

    EditText emailId, password;
    Button btnSignIn;
    private SignInButton btnSignInGoogle;
    private LoginButton loginButtonFb;
    TextView tvSignUp, textViewUser;
    ImageView mLogo;
    private AccessTokenTracker accessTokenTracker;

    //authentication
    FirebaseAuth mFirebaseAuth;
    GoogleSignInClient mGoogleSignInClient;

    //facebook
    private CallbackManager mCallbackManager;



    private static final String TAG = "LoginActivity";

    private FirebaseAuth.AuthStateListener mAuthStateListener, mAuthStateListenerFacebook;

    private int RC_SIGN_IN =1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnSignInGoogle = findViewById(R.id.signInButtonGoogle);

        mFirebaseAuth = FirebaseAuth.getInstance();

        emailId = findViewById(R.id.editTextLogin);
        password = findViewById(R.id.editText2Login);
        btnSignIn = findViewById(R.id.buttonLogin);
        tvSignUp= findViewById(R.id.textViewLogin);

        FacebookSdk.sdkInitialize(getApplicationContext());
        textViewUser = findViewById(R.id.textViewNameFB);
        mLogo = findViewById(R.id.imageViewFb);
        loginButtonFb= findViewById(R.id.login_button_fb);
        loginButtonFb.setReadPermissions("email", "public_profile");

        mCallbackManager = CallbackManager.Factory.create();
        loginButtonFb.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "onSuccess: Sucess logged in!" + loginResult);
                handleFacebookToken(loginResult.getAccessToken());
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onSuccess: onCancel!");

            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "onSuccess: onError!");
            }
        });

        mAuthStateListenerFacebook = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user!=null){
                    update(user);
                }else{
                    update(null);
                }
            }
        };

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if(currentAccessToken == null){
                    mFirebaseAuth.signOut();
                }
            }
        };



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);


        btnSignInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });




        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null){
                    Toast.makeText(LoginActivity.this, "You are logged in!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(LoginActivity.this, "Please login!!", Toast.LENGTH_SHORT).show();

                }
            }
        };

        //login normal
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email = emailId.getText().toString();
                String pwd = password.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("Please enter the email");
                    emailId.requestFocus();
                }else if(pwd.isEmpty()){
                    password.setError("Please enter your password");
                    password.requestFocus();
                }else if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Fiels are empty!", Toast.LENGTH_SHORT).show();
                }else if(!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this,"Login Error! Please try again", Toast.LENGTH_SHORT).show();
                            }else{
                                Intent intentHome = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intentHome);
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(LoginActivity.this,"Error Ocurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSignUp = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intentSignUp);
            }
        });

    }

    private void signIn(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mCallbackManager.onActivityResult(requestCode,resultCode,data);
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }


    //google
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try {

            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(LoginActivity.this,"Signed in Sucessfull!", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);

            //Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            //startActivity(intent);

        }catch (ApiException e){
            Toast.makeText(LoginActivity.this,"Signed in Unsucessfull!", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);

        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acct){
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"Authentication Sucessfull!", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mFirebaseAuth.getCurrentUser();
                    updateUI(user);

                }else{
                    Toast.makeText(LoginActivity.this,"Authentication Failed!", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }
        });
    }

    private void updateUI(FirebaseUser fUser){


        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(account != null){

            String personEmail = account.getEmail();
            String personName = account.getDisplayName();
            Uri personFoto = account.getPhotoUrl();

            Toast.makeText(LoginActivity.this, personName + personEmail , Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListenerFacebook);
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthStateListenerFacebook != null){
            mFirebaseAuth.removeAuthStateListener(mAuthStateListenerFacebook);
        }
    }

    //facebook

    private void handleFacebookToken(AccessToken token){
        Log.d(TAG, "handleFacebookToken: " + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "sign in with credential: sucessful ");
                    FirebaseUser user = mFirebaseAuth.getCurrentUser();
                    update(user);
                }else{
                    Log.d(TAG, "sign in with credential: Erro ", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    update(null);
                }
            }
        });
    }

    private void update(FirebaseUser user){
        if(user != null){
            textViewUser.setText(user.getDisplayName());
            if(user.getPhotoUrl() != null){
                String photoUrl = user.getPhotoUrl().toString();
                photoUrl = photoUrl + "?type=small";
                Picasso.get().load(photoUrl).into(mLogo);
            }
        }
        else{
            textViewUser.setText("");
            mLogo.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
        }
    }


}
