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

import java.util.concurrent.CountDownLatch;

/**
 * This class is the main screen of the app
 * The activity has a number of buttons that represent different tyeps of clothing
 */
public class ClosetActivity extends AppCompatActivity
{
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    FirebaseUser mUser;
    User user;
    String sortType;

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

    /**
     * Information about the current user and the user's clothes are collected from Firebase
     *
     */
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
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(this, "error getting user", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * This function determines which of the buttons were clicked, then sends the user to
     * ClothingTypeActivity with the correct information
     *
     * @param v on user clicking one of the clothing type icons
     */
    public void goClothingTypeActivity(View v)
    {
        Intent intent = new Intent(this, ClothingTypeActivity.class);
        switch (v.getId())
        {
            case R.id.shirtsCL:
                sortType = "shirts";
                break;
            case R.id.pantsCL:
                sortType = "pants";
                break;
            case R.id.shoesCL:
                sortType = "shoes";
                break;
            case R.id.outerwearCL:
                sortType = "outerwear";
                break;
            case R.id.underwearAndSocksCL:
                sortType = "underwear & socks";
                break;
            case R.id.dressesCL:
                sortType = "dresses";
                break;
            case R.id.accessoriesCL:
                sortType = "accessories";
                break;
            case R.id.seeAllCL:
                sortType = "all";
                break;
            case R.id.washingMachineImageView:
                sortType = "Wash";
                break;
        }
        intent.putExtra("user", user);
        intent.putExtra("sortType", sortType);
        startActivity(intent);
    }

    /**
     * Signs the user out of Firebase and returns the user to the sign in page
     * @param v button click
     */
    public void signOut(View v)
    {
        mAuth.signOut();
        startActivity(new Intent(this, SignInActivity.class));
        this.finish();
    }
}