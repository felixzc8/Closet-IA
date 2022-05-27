package com.example.closet_ia.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.closet_ia.R;
import com.example.closet_ia.objects.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends AppCompatActivity
{

    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    EditText nameInput, emailInput, passwordInput;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        nameInput = findViewById(R.id.datePurchasedEditText);
        emailInput = findViewById(R.id.emailInputEditTextSU);
        passwordInput = findViewById(R.id.passwordInputEditTextSU);
    }

    public void signUp(View v)
    {
        String name = nameInput.getText().toString();
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();
        System.out.println(String.format("Sign Up - Email: %s, Password: %s", email, password));

        if (!name.equals("") && !email.equals("") && !password.equals(""))
        {
            try
            {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task ->
                        {
                            if (task.isSuccessful())
                            {
                                Log.d("SIGN UP", "createUserWithEmail:success");
                                FirebaseUser currUser = mAuth.getCurrentUser();
                                String uid = currUser.getUid();

                                user = new User(uid, name, email);
                                firestore.collection("/users").document(uid).set(user);
                                goClosetActivity();
                            } else
                            {
                                Log.w("SIGN UP", "createUserWithEmail:failure",
                                        task.getException());
                                Toast.makeText(SignUpActivity.this,
                                        "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        } else
        {
            Toast.makeText(this, "fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void goClosetActivity()
    {
        startActivity(new Intent(this, ClosetActivity.class));
    }
    public void goSignInActivity(View v)
    {
        startActivity(new Intent(this, SignInActivity.class));
    }
}