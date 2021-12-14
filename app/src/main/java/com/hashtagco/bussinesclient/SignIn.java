package com.hashtagco.bussinesclient;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.hashtagco.bussinesclient.Common.Common;
import com.hashtagco.bussinesclient.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class SignIn extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    TextView forgetpass;
    EditText editemail, editPassword;
    Button btnSignIn,btnSignUp;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
String mail;
String pass;
     DatabaseReference table_user;
     ProgressDialog mDialog;
     CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
Paper.init(this);
        editemail = findViewById(R.id.edit_email);
        editPassword = findViewById(R.id.edit_Password);
        btnSignIn = findViewById(R.id.btn_signIn);
        database = FirebaseDatabase.getInstance();
        table_user = database.getReference("User");
      // String mailtext=getIntent().getStringExtra("email");
       // String passtext = getIntent().getStringExtra("pass");
btnSignUp = findViewById(R.id.btn_siginup);
btnSignUp.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(SignIn.this,SignUp.class);
        Paper.book().destroy();
        startActivity(intent);
    }
});
        mail = Paper.book().read(Common.USER_KEY);
        pass=Paper.book().read(Common.PWD_KEY);

editemail.setText(mail);
editPassword.setText(pass);
        forgetpass = findViewById(R.id.forgetpass);
        checkBox=findViewById(R.id.checkboxremeber);
        firebaseAuth = FirebaseAuth.getInstance();


       btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

if(mail!=null&&pass!=null){
    Intent homeIntent =
            new Intent(SignIn.this, Home.class);
    //Common.currentUser = user;

    startActivity(homeIntent);
}
        else       login();


            }
        });
    }


    private void login() {
    String  email  = editemail.getText().toString();
    String passs= editPassword.getText().toString();

        if (email.isEmpty()) {
            editemail.setError("برجاء كتابة ايميلك ");
            editemail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editemail.setError("ايميلك غير صحيح");
            editemail.requestFocus();
            return;
        }
        if (passs.isEmpty()) {
            editPassword.setError("الباسورد فارغ");
            editPassword.requestFocus();
            return;
        }
        if (checkBox.isChecked()){
            Paper.book().write(Common.USER_KEY,email);
            Paper.book().write(Common.PWD_KEY,passs);

/*
            mail = Paper.book().read(Common.USER_KEY);
            pass=Paper.book().read(Common.PWD_KEY);*/
           //*//* mail = Paper.book().read(Common.USER_KEY);
         //  pass=Paper.book().read(Common.PWD_KEY);*//*
            // editemail.setText(mail);
            // editPassword.setText(pass);
            // checkBox.setChecked(true);
//email=mail;
//passs=pass;
        }
        firebaseAuth.signInWithEmailAndPassword(email, passs).addOnSuccessListener(aVoid ->
                {
                    mDialog = new ProgressDialog(SignIn.this);
                    mDialog.setMessage("انتظر من فضلك ...");
                    mDialog.show();
                    table_user.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            //Check if user not exist in Database
                            if(dataSnapshot.child(firebaseAuth.getCurrentUser().getUid()).exists()) {
                                //Get user information
                                mDialog.dismiss();
                                User user = dataSnapshot.child(firebaseAuth.getCurrentUser().getUid())
                                        .getValue(User.class);
                                user.setEmail(email);//set phone
                                if (user.getEmail().equals(editemail.getText().toString())) {

                                    //Toast.makeText(SignIn.this, "تم تسجيل!!!", Toast.LENGTH_SHORT).show();
                                    Intent homeIntent =
                                            new Intent(SignIn.this, Home.class);
                                    Common.currentUser = user;
                                    startActivity(homeIntent);
                                    finish();
                                } else {

                                    Toast.makeText(SignIn.this, "Wrong password!!!", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                mDialog.dismiss();
                                Toast.makeText(SignIn.this, "User not exist !", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
        ).addOnFailureListener(e -> Toast.makeText(SignIn.this, "تاكد من كتابة الباسورد Registration Failed", Toast.LENGTH_LONG)
                .show());

        //mDialog.dismiss();
        // loadingProgressBar.setVisibility(View.INVISIBLE);

        // startActivity(new Intent(Sign_up.this, Book.class));
        // finish();
    }
}


