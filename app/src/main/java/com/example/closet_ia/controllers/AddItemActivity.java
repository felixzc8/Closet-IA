package com.example.closet_ia.controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.closet_ia.R;
import com.example.closet_ia.objects.ClothingItem;
import com.example.closet_ia.objects.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.UUID;

import yuku.ambilwarna.AmbilWarnaDialog;

/**
 *This activity will allow the user to add items by inputting a name, a date purchased, and a color
 * The item will be added to Firestore under the user
 */
public class AddItemActivity extends AppCompatActivity
{
    FirebaseFirestore firestore;

    User user;

    Button colorButton;
    EditText nameInputEditText, datePurchasedEditText;

    String chosenDate = "";
    String type;
    String sortType;
    int chosenColor = 0;
    boolean choseColor = false;


    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        firestore = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        sortType = intent.getStringExtra("sortType");
        type = sortType;

        nameInputEditText = findViewById(R.id.nameInputEditText);
        datePurchasedEditText = findViewById(R.id.datePurchasedEditText);

//        datePurchasedTextView = findViewById(R.id.datePurchasedTextView);
        datePurchasedEditText = findViewById(R.id.datePurchasedEditText);
        choosePurchaseDate();

        colorButton = findViewById(R.id.colorButton);
        colorButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openColorPicker();
            }
        });
    }

    /**
     * Allows users to choose a purchase date for their clothing item from a calendar
     */
    public void choosePurchaseDate()
    {
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePurchasedEditText.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddItemActivity.this, new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2)
                    {
                        i1 += 1;
                        chosenDate = i2 + "/" + i1 + "/" + i;
                        String msg = "date purchased: " + chosenDate;
                        datePurchasedEditText.setText(msg);
                    }
                }, year, month, day);

                datePickerDialog.show();
            }
        });
    }

    /**
     * Allows the user to choose a color for their clothing item from a color wheel
     */
    public void openColorPicker()
    {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this,
                chosenColor, new AmbilWarnaDialog.OnAmbilWarnaListener()
        {
            @Override
            public void onCancel(AmbilWarnaDialog dialog)
            {
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color)
            {
                System.out.println(color);
                chosenColor = color;
                choseColor = true;
                colorButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.round_bg));
                colorButton.setBackgroundColor(color);
                String hex = Integer.toHexString(color);
                System.out.println(hex);
            }
        });
        colorPicker.show();
    }

    /**
     * After the user inputs information about their new clothing item, they can add the item to
     * their closet, which is stored on Firebase
     *
     * @param v button click
     */
    public void addItem(View v)
    {

        String ID = UUID.randomUUID().toString();
        String name = nameInputEditText.getText().toString();

        if (!ID.equals("") && !name.equals("") && choseColor)
        {
            ClothingItem item = new ClothingItem(ID, type, name, chosenColor, chosenDate);
            user.addItem(item);

            firestore.collection("users")
                    .document(user.getID())
                    .set(user).addOnCompleteListener(task ->
                    {
                        Toast.makeText(AddItemActivity.this, "item added", Toast.LENGTH_SHORT).show();
                    });
            goClothingTypeActivity(v);
        }
        else
        {
            Toast.makeText(this, "please fill in all fields", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Goes to ClothingTypeActivity and sends the user object and the type of the user
     * @param v button click
     */
    public void goClothingTypeActivity(View v)
    {
        Intent intent = new Intent(this, ClothingTypeActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("sortType", sortType);
        startActivity(intent);
    }
}