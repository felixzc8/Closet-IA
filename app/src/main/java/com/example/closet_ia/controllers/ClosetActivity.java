package com.example.closet_ia.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.closet_ia.R;
import com.example.closet_ia.objects.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ClosetActivity extends AppCompatActivity
{
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    FirebaseUser mUser;
    User user;
    String type;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_closet);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        getUser();
    }

    public void getUser()
    {
        try
        {
            firestore.collection("/users").document(mUser.getUid()).get()
                    .addOnCompleteListener(task ->
                    {
                        DocumentSnapshot ds = task.getResult();
                        if (task.isSuccessful())
                        {
                            user = ds.toObject(User.class);
                            Log.d("USER OBJECT", "user name: " + user.getName());
                        }
                    });
        } catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, "error getting user", Toast.LENGTH_SHORT).show();
        }
    }

    public void goClothingTypeActivity(View v)
    {
        Intent intent = new Intent(this, ClothingTypeActivity.class);
        switch(v.getId())
        {
            case R.id.shirtsCL:
                type = "shirts";
                break;
            case R.id.pantsCL:
                type = "pants";
                break;
            case R.id.shoesCL:
                type = "shoes";
                break;
            case R.id.outerwearCL:
                type = "outerwear";
                break;
            case R.id.underwearAndSocksCL:
                type = "underwear & socks";
                break;
            case R.id.dressesCL:
                type = "dresses";
                break;
            case R.id.accessoriesCL:
                type = "accessories";
                break;
        }
        intent.putExtra("type", type);
        intent.putExtra("user", user);
        startActivity(intent);
    }
    public void goWashingMachineActivity(View v)
    {
        Intent intent = new Intent(this, WashingActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }
    public void signOut(View v)
    {
        mAuth.signOut();
        startActivity(new Intent(this, SignInActivity.class));
        this.finish();
    }
}