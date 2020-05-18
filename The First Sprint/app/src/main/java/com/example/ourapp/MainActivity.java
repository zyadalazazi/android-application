package com.example.ourapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText Emailaddr;
    private EditText PassWrd;

    private RadioGroup rg;
    private RadioButton stu;
    private RadioButton tch;

    private CheckBox rememberMe;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefn";
    private static final String KEY_REMEBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";

    FirebaseAuth mFirebaseauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        //Adding the remember me feature
        Emailaddr = (EditText) findViewById(R.id.etEmail);
        PassWrd = (EditText)findViewById(R.id.etPass);
        rememberMe = (CheckBox) findViewById(R.id.rememberme_cb);
        rg = (RadioGroup)findViewById(R.id.TrueFalseRB);

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");
        if(checkbox.equals("true")){
            Toast.makeText(this, "The box is checked", Toast.LENGTH_LONG).show();
        }else if (checkbox.equals("false")){
            Toast.makeText(this, "The box is unchecked", Toast.LENGTH_LONG).show();
        }

        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if(compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remeber", "true");
                    editor.apply();
                    Toast.makeText(MainActivity.this, "Box is Checked", Toast.LENGTH_SHORT).show();
                }else if (!compoundButton.isChecked()){
                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remeber", "false");
                    editor.apply();
                    Toast.makeText(MainActivity.this, "Box is Unchecked", Toast.LENGTH_SHORT).show();
                }

            }
        });
// End of the remember me feature
        stu = findViewById(R.id.Student);
        mFirebaseauth = FirebaseAuth.getInstance();
        if (mFirebaseauth.getCurrentUser()!=null){
            FirebaseAuth.getInstance().signOut();
        }

    }

    public void Login(View v){

        final String loginEmail = Emailaddr.getText().toString();
        String pwd = PassWrd.getText().toString();
        final int radiobuttonid = rg.getCheckedRadioButtonId();

        if (loginEmail.isEmpty()){
            Emailaddr.setError("PLease enter an email");
            Emailaddr.requestFocus();
        }else if (pwd.isEmpty()){
            PassWrd.setError("please en a password");
            PassWrd.requestFocus();
        } else if (loginEmail.isEmpty() && pwd.isEmpty()){
            Toast.makeText(MainActivity.this,"fields are empty",Toast.LENGTH_SHORT).show();

        }else if(radiobuttonid==-1){
            Toast.makeText(getBaseContext(), "You have to login as a user type", Toast.LENGTH_SHORT).show();

        }

        else if ((!loginEmail.isEmpty())&&(!pwd.isEmpty())){



            mFirebaseauth.signInWithEmailAndPassword(loginEmail,pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                  if (task.isSuccessful()){
                      if (radiobuttonid == R.id.Student){
                        Intent i =new Intent(getApplicationContext(),homepage.class);
                        i.putExtra("email", loginEmail);
                        i.putExtra("isTeacher",false);
                        startActivity(i);
                      } else {
                          Intent i =new Intent(getApplicationContext(),homepage.class);
                          i.putExtra("email", loginEmail);
                          i.putExtra("isTeacher",true);
                          startActivity(i);

                      }


                  }
                  else {
//                      Log.e("MyActivity", "Sign-in Failed: " + task.getException().getMessage());
                      Toast.makeText(MainActivity.this,"Login Error, please try again!",Toast.LENGTH_SHORT).show();

                  }
                }
            });

        } else {
            Toast.makeText(MainActivity.this,"Error Occured",Toast.LENGTH_SHORT).show();
        }



    }


    public void createAccount(View v){
        startActivity(new Intent(MainActivity.this,createAccount.class));
    }


    public void forgotpass(View v){
        startActivity(new Intent(getApplicationContext(),forgetpassword.class));
    }


}
