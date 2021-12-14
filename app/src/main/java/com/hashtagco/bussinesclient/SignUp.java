package com.hashtagco.bussinesclient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.hashtagco.bussinesclient.Common.Common;
import com.hashtagco.bussinesclient.Model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.paperdb.Paper;

public class SignUp extends AppCompatActivity {

    EditText editPhone, editName, editPassword , editEmail;
    Button btnSignUp;
    FirebaseAuth auth;
    DatabaseReference table_user;
    FirebaseDatabase database;
    User user;
     //FirebaseAuth.AuthStateListener mAuthListener;

   /* @Override
    protected void onStart() {
        super.onStart();
        if(auth.getCurrentUser()!=null){
            Intent intent = new Intent(SignUp.this,SignIn.class);
            startActivity(intent);
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        editEmail = findViewById(R.id.ediEmail);
        editPhone = findViewById(R.id.edit_PhoneUp);
        editName = findViewById(R.id.edit_NameUp);
        editPassword = findViewById(R.id.edit_PasswordUp);
        btnSignUp = findViewById(R.id.btn_signUp);
       auth = FirebaseAuth.getInstance();
/*
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {


                } else {

                    // startActivity(new Intent(MainActivity.this, WelcomeActivity.class));


                }


            }
        };
*/
        //Init Firebase
        database = FirebaseDatabase.getInstance();
        table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("انتظر من فضلك ...");
                mDialog.show();
               table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if user not exist in database
                        if (dataSnapshot.child(editPhone.getText().toString()).exists()) {
                            //get user information
                            mDialog.dismiss();
                            User user = dataSnapshot.child(editPhone.getText().toString()).
                                    getValue(User.class);
                           // Toast.makeText(SignUp.this, "Phone number already registered !", Toast.LENGTH_SHORT).show();

                        } else {
                            mDialog.dismiss();
                            User user = new User(editName.getText().toString(),
                                    editPassword.getText().toString(),editPhone.getText().toString());
                            table_user.child(editPhone.getText().toString()).setValue(user);

                            Toast.makeText(SignUp.this, "تم تسجيلك بنجاح !", Toast.LENGTH_SHORT).show();
Intent intent = new Intent(SignUp.this,Home.class);
Common.currentUser=user;
startActivity(intent);

                            finish();
                        }
                    }
                    @Override
                    public void onCancelled (DatabaseError databaseError){

                    }
                });*/
                registerUser();
            }
        });

    }
    private void registerUser() {
        String email = editEmail.getText().toString().trim();
        String name = editName.getText().toString();
        String password = editPassword.getText().toString().trim();
        String phone = editPhone.getText().toString();
        if(phone.isEmpty()){
            editPhone.setError("برجاء كتابة رقم الجوال");
        }
        if (email.isEmpty()) {
            editEmail.setError("برجاء كتابة ايميلك");
            editEmail.requestFocus();

        }
        if(email.isEmpty()){
            editEmail.setError("برجاء كتابة ايميلك ");
            editEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editEmail.setError("ايميلك غير صحيح");
            editEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editPassword.setError("الباسورد فارغ");
            editPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            editPassword.setError("الباسورد لا يقل عن 6");
            editPassword.requestFocus();
            return;
        }
        final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
        mDialog.setMessage("انتظر من فضلك ...");
        mDialog.show();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    //save user to the database
                    user = new User();
                    user.setName(name);
                    user.setEmail(email);
                    user.setPhone(phone);
                 // user.setPassword(password);
                    user.setIsStaff("false");
user.setUid(auth.getCurrentUser().getUid());
//Common.currentUser=user;
                //    loadingProgressBar.setVisibility(View.VISIBLE);

                    //use phone as key

                    table_user.child(auth.getCurrentUser().getUid())
                            .setValue(user).addOnSuccessListener(aVoid ->{
                                Intent intent = new Intent(SignUp.this,SignIn.class);
                              //  intent.putExtra("bundle",user);
                               Common.currentUser = user;

                                Paper.book().write(Common.USER_KEY,email);
                                Paper.book().write(Common.PWD_KEY,password);
                              //  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

                                startActivity(intent);
                                finish();
                            }
                                  /*  Intent intent = new Intent(SignIn.this,Home.class)
                            startActivity(intent)*/
                    )
                            .addOnFailureListener(e -> Toast.makeText(SignUp.this, "Registration Failed", Toast.LENGTH_LONG)
                                    .show());
                    mDialog.dismiss();
                    // loadingProgressBar.setVisibility(View.INVISIBLE);

                    // startActivity(new Intent(Sign_up.this, Book.class));
                    // finish();
                })
                .addOnFailureListener(e -> Toast.makeText
                        (SignUp.this, "Authentication Failed: " + e.getMessage(),
                        Toast.LENGTH_LONG).show());;


    }
}




