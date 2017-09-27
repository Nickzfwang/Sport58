package com.example.user.sport58;

import android.app.Activity;

import android.content.Intent;


import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import cn.gavinliu.android.lib.scale.config.ScaleConfig;


public class login extends AppCompatActivity implements Runnable {
    ///變數宣告------------------------------------------------------------------------//
    private CallbackManager callbackManager;
    private  final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth Google_mAuth;
    private FirebaseAuth Facebook_mAuth;

    DatabaseReference databaseReference;
    private EditText account_numberString;
    private EditText passwordString;
    private Toast toast;
    private Thread threads;
    private  Button btn7, btn8, btn9;
    private  LoginButton loginButton;
    private int j = 0;
    private ProgressBar pb4;
    private  int count;
    //------------------------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //自適屏布局始量化
        ScaleConfig.create(this,
                1080, // Design Width
                1920, // Design Height
                3,    // Design Density
                3,    // Design FontScale
                ScaleConfig.DIMENS_UNIT_DP);
        setContentView(R.layout.activity_login);
        setTitle("登入");

        Google_mAuth = FirebaseAuth.getInstance();
        Facebook_mAuth = FirebaseAuth.getInstance();
        FacebookSdk.sdkInitialize(getApplicationContext());
        Activity loginclose = this;
        //元件宣告--------------------------------------------------------------------//
        btn7 = (Button) findViewById(R.id.button7);
        btn8 = (Button) findViewById(R.id.button8);
        btn9 = (Button) findViewById(R.id.button9);
        pb4 = (ProgressBar) findViewById(R.id.progressBar4);
        account_numberString = (EditText) findViewById(R.id.editText9);
        passwordString = (EditText) findViewById(R.id.editText10);
        btn7.setOnClickListener(dobtn7);
        btn8.setOnClickListener(dobtn8);
        btn9.setOnClickListener(dobtn9);

        loginButton = (LoginButton) findViewById(R.id.fb_login_bn);
        Toolbar mytoobar = (Toolbar) findViewById(R.id.toolbar2);
        //--------------------------------------------------------------------------------//
        setSupportActionBar(mytoobar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        pb4.setVisibility(View.GONE);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("member");
        btn7.setEnabled(true);
        btn8.setEnabled(true);
        btn9.setEnabled(true);
        loginButton.setEnabled(true);


        // fb登入-------------------------------------------------------------------------------------------------------------------------------//
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback < LoginResult > () {
            @Override
            public void onSuccess(LoginResult loginResult) {
                pb4.setVisibility(View.VISIBLE);
                btn7.setEnabled(false);
                btn8.setEnabled(false);
                btn9.setEnabled(false);
                loginButton.setEnabled(false);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                if (toast != null) {
                    toast.cancel();
                } //讓訊息不要延遲
                Toast.makeText(login.this, "取消", Toast.LENGTH_SHORT).show();
                btn7.setEnabled(true);
                btn8.setEnabled(true);
                btn9.setEnabled(true);
                loginButton.setEnabled(true);
            }

            @Override
            public void onError(FacebookException error) {
                if (toast != null) {
                    toast.cancel();
                } //讓訊息不要延遲
                Toast.makeText(login.this, "請刪除FB應用程式再試試!", Toast.LENGTH_SHORT).show();
                btn7.setEnabled(true);
                btn8.setEnabled(true);
                btn9.setEnabled(true);
                loginButton.setEnabled(true);
                // ...
            }
        });
        // ...
        ////---------------------------------------------------------------------------------------------------------------------------------------------//



        // google 登入--------------------------------------------------------------------------------------------------------------------------------///
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        pb4.setVisibility(View.GONE);
                        if (toast != null) {
                            toast.cancel();
                        } //讓訊息不要延遲
                        Toast.makeText(login.this, "You Get an  Error", Toast.LENGTH_SHORT).show();
                        btn7.setEnabled(true);
                        btn8.setEnabled(true);
                        btn9.setEnabled(true);
                        loginButton.setEnabled(true);
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        ////
        ////-----------------------------------------------------------------------------------------------------------------------------------------------------------//
    }


    ////google登入functuon----------------------------------------------//
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        btn7.setEnabled(false);
        btn8.setEnabled(false);
        btn9.setEnabled(false);
        loginButton.setEnabled(false);
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        Google_mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener < AuthResult > () {
                    @Override
                    public void onComplete(@NonNull Task < AuthResult > task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            pb4.setVisibility(View.GONE);
                            if (toast != null) {
                                toast.cancel();
                            } //讓訊息不要延遲
                            Toast.makeText(login.this, "登入成功!歡迎" + account.getDisplayName(), Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(login.this, menu.class));
                            FirebaseUser user = Google_mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a RegisteredLimit to the user.
                            pb4.setVisibility(View.GONE);
                            Toast.makeText(login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            btn7.setEnabled(true);
                            btn8.setEnabled(true);
                            btn9.setEnabled(true);
                            loginButton.setEnabled(true);
                        }

                        // ...
                    }
                });
    }
    //
    ///----------------------------------------------------------------------//

    //FB 登入function-------------------------------------------------------------------------------------------///
    private void handleFacebookAccessToken(final AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        Facebook_mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener < AuthResult > () {
                    @Override
                    public void onComplete(@NonNull Task < AuthResult > task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            pb4.setVisibility(View.GONE);
                            if (toast != null) {
                                toast.cancel();
                            } //讓訊息不要延遲
                            Toast.makeText(login.this, "登入成功!", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(login.this, menu.class));
                            FirebaseUser user = Facebook_mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a RegisteredLimit to the user.
                            pb4.setVisibility(View.GONE);
                            if (toast != null) {
                                toast.cancel();
                            } //讓訊息不要延遲
                            Toast.makeText(login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            btn7.setEnabled(true);
                            btn8.setEnabled(true);
                            btn9.setEnabled(true);
                            loginButton.setEnabled(true);
                        }

                        // ...
                    }
                });
    }

    //----------------------------------------------------------------------------------------------------------------------------//



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                pb4.setVisibility(View.GONE);
                btn7.setEnabled(true);
                btn8.setEnabled(true);
                btn9.setEnabled(true);
                loginButton.setEnabled(true);
            }
        }

    }


    private final Button.OnClickListener dobtn9 = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            j = 2;

            Thread threads = new Thread(login.this);
            threads.start();
        }
    };

    private final Button.OnClickListener dobtn7 = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();

            //初始化Intent物件
            Intent intent = new Intent();
            //從MainActivity 到registered
            intent.setClass(login.this, Registered.class);
            //開啟Activity
            startActivity(intent);
        }
    };

    private final Button.OnClickListener dobtn8 = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            j = 1;
            count = 0;
            Thread threads = new Thread(login.this);
            threads.start();

        }
    };

    private final Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == 1) {
                if (j == 1) {
                    pb4.setVisibility(View.VISIBLE);
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            // do some stuff once
                            for (DataSnapshot ds: snapshot.getChildren()) {
                                String account = account_numberString.getText().toString().trim();//使用者輸入的帳號
                                String password = passwordString.getText().toString().trim();//使用者輸入的密碼
                                String Account = ds.child("account").getValue().toString();//資料庫的帳號
                                String Password = ds.child("password").getValue().toString();//資料庫的密碼

                                if (account.matches("") || password.matches("")) {
                                    if (toast != null) {
                                        toast.cancel();
                                    } //讓訊息不要延遲
                                    toast = Toast.makeText(login.this, "帳號或密碼未填!", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.CENTER, 0, 50);
                                    toast.show();
                                    pb4.setVisibility(View.GONE);
                                    break;
                                } else {
                                    //密碼加密
                                    String passmd5 = MD5.MD5(password);
                                    String encryptmd5 = MD5.encryptmd5(passmd5);
                                    //
                                    if (account.equals(Account) && encryptmd5.equals(Password)) {
                                        pb4.setVisibility(View.GONE);
                                        if (toast != null) {
                                            toast.cancel();
                                        } //讓訊息不要延遲
                                        toast = Toast.makeText(login.this, "歡迎," + Account, Toast.LENGTH_SHORT);
                                        toast.setGravity(Gravity.CENTER, 0, 50);
                                        toast.show();
                                        String user = account_numberString.getText().toString();
                                        String pass = passwordString.getText().toString();
                                        SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
                                        pref.edit()
                                                .clear()
                                                .putString("user", user)
                                                .putString("pass", pass)
                                                .apply();
                                        finish();
                                        account_numberString.setText("");
                                        passwordString.setText("");
                                        startActivity(new Intent(login.this, menu.class));
                                        break;
                                    } else {
                                        count++;

                                        if (count == snapshot.getChildrenCount()) {
                                            pb4.setVisibility(View.GONE);
                                            if (toast != null) {
                                                toast.cancel();
                                            } //讓訊息不要延遲
                                            toast = Toast.makeText(login.this, "帳號或密碼有誤!", Toast.LENGTH_SHORT);
                                            toast.setGravity(Gravity.CENTER, 0, 50);
                                            toast.show();
                                            break;
                                        }

                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                } else {
                    if (j == 2) {
                        pb4.setVisibility(View.VISIBLE);
                        signIn();
                    }
                }




            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void run() {
        Message m = mHandler.obtainMessage();
        m.what = 1;
        m.sendToTarget();
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(login.this);
        super.onDestroy();
    }




}