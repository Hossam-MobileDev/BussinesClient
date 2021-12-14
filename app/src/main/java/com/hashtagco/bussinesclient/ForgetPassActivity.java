package com.hashtagco.bussinesclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class ForgetPassActivity extends AppCompatActivity {

    EditText editpass;
    Button buttonpass;
    FirebaseAuth firebaseAuth;
    DatabaseReference table_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        editpass = findViewById(R.id.email);
        buttonpass = findViewById(R.id.btnemail);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        table_user = database.getReference("User");
        buttonpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetpassword();
            }
        });
    }

    private void resetpassword() {
        String email = editpass.getText().toString().trim();
        if (email.isEmpty()) {
            editpass.setError("برجاء كتابة ايميلك ");
            editpass.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editpass.setError("ايميلك غير صحيح");
            editpass.requestFocus();
            return;
        }
        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener
                (new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgetPassActivity.this, "قم بفحص ايميلك", Toast.LENGTH_SHORT).show();
/*
                            table_user.addValueEventListener(new ValueEventListener() {

                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    //Check if user not exist in Database
                                    if (dataSnapshot.child(firebaseAuth.getCurrentUser().getUid()).exists()) {
                                        //Get user information
                                        // mDialog.dismiss();
                                        User user = dataSnapshot.child(firebaseAuth.getCurrentUser().getUid())
                                                .getValue(User.class);
//              //  user.setEmail(editemail.getText().toString());//set phone
//                if (user.getPassword().equals(editPassword.getText()
//                        .toString())
//                        &&user.getEmail().equals(editemail.getText().toString())) {
//
//                    //Toast.makeText(SignIn.this, "تم تسجيل!!!", Toast.LENGTH_SHORT).show();
//                    Intent homeIntent = new Intent(SignIn.this, Home.class);
                                        Common.currentUser = user;
                                        finish();
                                    }


                                }


                                @Override
                                public void onCancelled(@NonNull @NotNull DatabaseError error) {


                                }

                            });
*/
                        }
                    }
                });
    }

}