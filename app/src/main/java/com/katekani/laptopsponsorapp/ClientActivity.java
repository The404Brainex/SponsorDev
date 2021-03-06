package com.katekani.laptopsponsorapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ClientActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtAnswer1;
    private EditText edtAnswer2;
    private EditText edtAnswer3;
    private EditText edtAnswer4;
    private EditText edtAnswer5;
    private Button btnSubmitForm;
    private String userID;
    private EditText Skills;
    private EditText Qualification;
    private ProgressDialog progressDialog;
    private FirebaseUser firebaseUser;
    Validation validation = new Validation();
    DatabaseReference mCurrentUserRef;


    String editAnswer1, editAnswer2,editAnswer3,editAnswer4,editAnswer5,skils,qual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Toast.makeText(this,"client",Toast.LENGTH_SHORT).show();

        edtAnswer1 = findViewById(R.id.answer1);
        edtAnswer2 = findViewById(R.id.answer2);
        edtAnswer3 = findViewById(R.id.answer3);
        edtAnswer4 = findViewById(R.id.answer4);
        edtAnswer5 = findViewById(R.id.answer5);
        Skills = findViewById(R.id.editSkills);
        Qualification = findViewById(R.id.editQualification);

        btnSubmitForm = findViewById(R.id.submitForm);

        progressDialog = new ProgressDialog(this);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if ( firebaseUser !=null) {
            userID = firebaseUser.getUid();
        }
        mCurrentUserRef = FirebaseDatabase.getInstance().getReference();;
        btnSubmitForm.setOnClickListener(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
        }

        if(R.id.signout == item.getItemId()){
         FirebaseAuth.getInstance().signOut();
         startActivity(new Intent(ClientActivity.this, ClientAndSponsorActivity.class));
         return true;

        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClick(View view) {
        editAnswer1 = edtAnswer1.getText().toString().trim();
        validation.isNameSurnameValid(editAnswer1);
        editAnswer2 = edtAnswer2.getText().toString().trim();
        validation.isNameSurnameValid(editAnswer2);
        editAnswer3 = edtAnswer3.getText().toString().trim();
        validation.isNameSurnameValid(editAnswer3);
        editAnswer4 = edtAnswer4.getText().toString().trim();
        validation.isNameSurnameValid(editAnswer4);
        editAnswer5 = edtAnswer5.getText().toString().trim();
        validation.isNameSurnameValid(editAnswer5);
        skils =Skills.getText().toString().trim();
        validation.isNameSurnameValid(skils);
        qual = Qualification.getText().toString().trim();
        validation.isNameSurnameValid(qual);

        //validation
        if (TextUtils.isEmpty(editAnswer1)) {
            Toast.makeText(getApplicationContext(), "provide answer for question 1", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(editAnswer2)) {
            Toast.makeText(getApplicationContext(), "provide answer for question 2", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(editAnswer3)) {
            Toast.makeText(getApplicationContext(), "provide answer for question 3", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(editAnswer4)) {
            Toast.makeText(getApplicationContext(), "provide answer for question 4", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(editAnswer5)) {
            Toast.makeText(getApplicationContext(), "provide answer for question 5", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(skils)) {
            Toast.makeText(getApplicationContext(), "provide answer for question 6", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(qual)) {
            Toast.makeText(getApplicationContext(), "provide answer for question 7", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering user please wait....");
        progressDialog.show();

        if (!"".equals(editAnswer1) && !"".equals(editAnswer2) && !"".equals(editAnswer3) && !"".equals(editAnswer4) && !"".equals(editAnswer5)&& !"".equals(skils) && !"".equals(qual)) {
            //mCurrentUserRef.child("Users").child(userID);
            startActivity(new Intent(ClientActivity.this, ClientAndSponsorActivity.class));

            //Toast.makeText(getApplicationContext(), "UUID : "+userID, Toast.LENGTH_SHORT).show();
            if (!"".equals(editAnswer1) && !"".equals(editAnswer2) && !"".equals(editAnswer3)  ) {
                //mCurrentUserRef.child("Users").child(userID);
                startActivity(new Intent(ClientActivity.this, ClientAndSponsorActivity.class));
                Toast.makeText(getApplicationContext(), "UUID : "+userID, Toast.LENGTH_SHORT).show();
                DeveloperAnswers developerAnswers = new DeveloperAnswers(editAnswer1,editAnswer2,editAnswer3,editAnswer4,editAnswer5,skils,qual);
                mCurrentUserRef.child("Developer_answers").child(userID).setValue(developerAnswers);
                progressDialog.dismiss();
            }
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    }
