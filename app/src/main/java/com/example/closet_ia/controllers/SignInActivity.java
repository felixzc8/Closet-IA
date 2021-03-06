package com.example.closet_ia.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.closet_ia.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignInActivity extends AppCompatActivity
{
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    EditText emailInput, passwordInput;

    @Override
    public void onStart()
    {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null)
        {
            String email = currentUser.getEmail();
            System.out.println(String.format("Current User - email: %s", email));
            goClosetActivity();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        emailInput = findViewById(R.id.nameInputEditText);
        passwordInput = findViewById(R.id.passwordInputEditTextSI);
    }

    /**
     * Checks that all input fields are valid, then signs the user in with their email and password,
     * if successful, brings user to ClosetActivity
     * @param v button click
     */
    public void signIn(View v)
    {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (!email.equals("") && !password.equals(""))
        {
            try
            {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task ->
                        {
                            if (task.isSuccessful())
                            {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("SIGN IN", "signInWithEmail:success");
                                goClosetActivity();
                            }
                            else
                            {
                                // If sign in fails, display a message to the user.
                                Log.w("SIGN IN", "signInWithEmail:failure",
                                        task.getException());
                                Toast.makeText(SignInActivity.this,
                                        "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(this, "fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Brings user to ClosetActivity
     */
    public void goClosetActivity()
    {
        startActivity(new Intent(this, ClosetActivity.class));
    }

    /**
     * Goes to Sign Up Activity
     * @param v button click
     */
    public void goSignUpActivity(View v)
    {
        startActivity(new Intent(this, SignUpActivity.class));
    }
}