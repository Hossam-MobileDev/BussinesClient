package com.hashtagco.bussinesclient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hashtagco.bussinesclient.Common.Common;
import com.hashtagco.bussinesclient.Model.User;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import dmax.dialog.SpotsDialog;
import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;


import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.hashtagco.bussinesclient.databinding.ActivityMainBinding;
import com.rengwuxian.materialedittext.MaterialEditText;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE =2021 ;
    FirebaseAuth firebaseAuth;
   // ActivityMainBinding activityMainBinding;
    // User user;
    DatabaseReference table_user;
    Button btnSignIn, btnSignUp;
    ProgressDialog mDialog;
    // TextView textSlogan;
    FirebaseAuth.AuthStateListener listener;
    Button btn_verify ,btn_codeVerify;
    TextInputEditText editTextphon ,et_code;
TextInputLayout inputLayout;
    TextView resend_code  ;
   //List<AuthUI.IdpConfig> providers;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callback ;
    PhoneAuthProvider.ForceResendingToken resendToken ;
    String storedVerificationId;
    FirebaseUser user;
    AlertDialog waitingDialog;
    @Override
    protected void onStop() {

        super.onStop();

    }

    @Override
    protected void onStart() {
        super.onStart();
       // firebaseAuth.addAuthStateListener(listener);
        if(user!=null){
           // editTextphon.setText(user.getPhoneNumber());

            checkuserlogin();
        }    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
*/
        setContentView(R.layout.activity_main);
btn_codeVerify = findViewById(R.id.btn_codeVerify);
btn_verify = findViewById(R.id.btn_verify);
editTextphon = findViewById(R.id.editTextphon);
et_code = findViewById(R.id.et_code);
inputLayout = findViewById(R.id.textinputlayout);
resend_code = findViewById(R.id.resend_code);
//textView = findViewById(R.id.textView);
btn_verify.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

       String phoneNumber = editTextphon.getText().toString();
if(TextUtils.isEmpty(phoneNumber)
||phoneNumber==null){
    editTextphon.setError("من فضلك ادخل الرقم");
    return;

}

         startPhoneNumberVerification("+2"+phoneNumber);
    }
});
btn_codeVerify.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
String code = et_code.getText().toString();
if(TextUtils.isEmpty(code)||code==null){
    et_code.setError("من فضلك ادخل الكود");
    return;
}
        verifyPhoneNumberWithCode(storedVerificationId, code);
    }
});
resend_code.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        resendVerificationCode(editTextphon.getText().toString(), resendToken);
    }
});
callback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
    @Override
    public void onCodeSent(@NonNull @NotNull String s,
                           @NonNull @NotNull
                                   PhoneAuthProvider.ForceResendingToken
                                   forceResendingToken) {
        super.onCodeSent(s, forceResendingToken);
        //textView.setText(s);
        storedVerificationId = s;
        resendToken = forceResendingToken;
        btn_verify.setVisibility(View.INVISIBLE);
        editTextphon.setVisibility(View.INVISIBLE);
        inputLayout.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {
signInWithPhoneAuthCredential(phoneAuthCredential);
    }

    @Override
    public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {

    }
};
//        getSupportActionBar().hide();

       // activityMainBinding.phoneLbl.requestFocus();

       /* activityMainBinding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,OptActivity.class);
                intent.putExtra("phoneNumber",activityMainBinding.phoneBox.getText().toString());
                intent.putExtra("Name",activityMainBinding.name.getText().toString());
                startActivity(intent);
            }
        });*/
//Paper.init(this);
       /* btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);*/
        firebaseAuth = FirebaseAuth.getInstance();
       FirebaseDatabase database = FirebaseDatabase.getInstance();
       table_user = database.getReference("User");
         user = firebaseAuth.getCurrentUser();
if(user!=null){
    editTextphon.setText(user.getPhoneNumber());

    checkuserlogin();
}
     /*  providers = Arrays.asList(new AuthUI.IdpConfig.PhoneBuilder().build());
        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    checkUserFromFirebase(user);
                   // Toast.makeText(MainActivity.this, "USER ID\n"+mUserId,Toast.LENGTH_SHORT).show();
                }
                else {
                    StartLoginSystem();
                   // Toast.makeText(ListActivity.this, "no id got", Toast.LENGTH_SHORT).show();
                }

            }
        };*/
     /* listener = firebaseAuth1 ->{
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if(user!=null){
                checkUserFromFirebase(user);
            }
            else StartLoginSystem();
        };*/
       // String email = Paper.book().read(Common.USER_KEY);
/*String pass=Paper.book().read(Common.PWD_KEY);
if(email!=null&&pass!=null){
    if(!email.isEmpty()&&!pass.isEmpty()){
        login(email,pass);
    }
}*/
       // textSlogan = findViewById(R.id.textSlogan);
        //Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/NABILA.TTF");
        //textSlogan.setTypeface(typeface);
/*
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent =
                        new Intent(MainActivity.this, SignUp.class);
                Paper.book().destroy();
                startActivity(signUpIntent);
                finish();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIntent = new Intent(MainActivity.this, SignIn.class);
               // signIntent.putExtra("email",email);
               // signIntent.putExtra("pass",pass);
                startActivity(signIntent);
                finish();
            }
        });*/
    }

    private void resendVerificationCode(String toString,
                                        PhoneAuthProvider.ForceResendingToken resendToken)
    {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(toString)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(callback)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    private void verifyPhoneNumberWithCode(String storedVerificationId, String code) {

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(storedVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this,
                        new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       // LogManager.printLog(LOGTYPE_DEBUG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()) {

                            FirebaseUser user = task.getResult().getUser();

                                AlertDialog waitingDialog = new SpotsDialog.Builder().setContext(MainActivity.this).build();
                                waitingDialog.show();
                                waitingDialog.setMessage("انتظر من فضلك");
                                waitingDialog.setCancelable(false);
                                table_user.orderByKey().equalTo(user.getPhoneNumber()).
                                        addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                                if(!snapshot.child(user.getPhoneNumber()).exists()){
                                                    User newUser = new User();
                                                    newUser.setPhone(user.getPhoneNumber());
                                                    //  newUser.setName(user.getDisplayName());
                                                    table_user.child(user.getPhoneNumber()).setValue(newUser)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                                    if(task.isSuccessful())
                                                                        Toast.makeText(MainActivity.this,"تم تسجيلك بنجاح",Toast.LENGTH_SHORT).show();

                                                                    table_user.child(user.getPhoneNumber()).addListenerForSingleValueEvent
                                                                            (new ValueEventListener() {
                                                                                @Override
                                                                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                                                                    User localUser = snapshot.getValue(User.class);
                                                                                    Intent intent = new Intent(MainActivity.this,Home.class);
                                                                                    Common.currentUser = localUser;
                                                                                    startActivity(intent);
                                                                                    waitingDialog.dismiss();

                                                                                }

                                                                                @Override
                                                                                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                                                                    waitingDialog.dismiss();
                                                                                    Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT);
                                                                                }
                                                                            });
                                                                }
                                                            });
                                                }
                                                else table_user.child(user.getPhoneNumber()).
                                                        addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                                                User localUser= snapshot.getValue(User.class);
                                                                Intent intent = new Intent(MainActivity.this,Home.class);
                                                                Common.currentUser = localUser;
                                                                startActivity(intent);
                                                                waitingDialog.dismiss();
                                                                finish();
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                                                waitingDialog.dismiss();
                                                                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT);
                                                            }
                                                        });
                                            }

                                            @Override
                                            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                                waitingDialog.dismiss();
                                                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT);
                                            }
                                        });
                            }
                         //   mPrgrsbrMain.setVisibility(View.GONE);
                         //   Log.w(LOG_TAG, "signInWithCredential", task.getException());
                          //  Snackbar.make(mCrdntrlyot, "Authentication failed.\n" + task.getException().getMessage(),
                                  //  Snackbar.LENGTH_LONG).show();
                        }
                           // successLoginGetData(task);

                });
    }

    private void startPhoneNumberVerification(String phoneNumber) {

       /* PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber, // Phone number to verify
                60, // Timeout duration
                TimeUnit.SECONDS, // Unit of timeout
                this, // Activity (for callback binding)
                callback);*/
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(callback)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
private void checkuserlogin(){
    table_user.child(user.getPhoneNumber()).
            addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    User localUser= snapshot.getValue(User.class);
                    Intent intent = new Intent(MainActivity.this,Home.class);
                    Common.currentUser = localUser;
                    startActivity(intent);
//                    waitingDialog.dismiss();
                    finish();
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    waitingDialog.dismiss();
                    Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT);
                }
            });
}
    private void checkUserFromFirebase(FirebaseUser user) {
        AlertDialog waitingDialog = new SpotsDialog.Builder().setContext(this).build();
        waitingDialog.show();
        waitingDialog.setMessage("انتظر من فضلك");
        waitingDialog.setCancelable(false);
        table_user.orderByKey().equalTo(user.getPhoneNumber()).
                addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(!snapshot.child(user.getPhoneNumber()).exists()){
                    User newUser = new User();
                    newUser.setPhone(user.getPhoneNumber());
                  //  newUser.setName(user.getDisplayName());
                    table_user.child(user.getPhoneNumber()).setValue(newUser)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
if(task.isSuccessful())
    Toast.makeText(MainActivity.this,"تم تسجيلك بنجاح",Toast.LENGTH_SHORT).show();

table_user.child(user.getPhoneNumber()).addListenerForSingleValueEvent
        (new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
        User localUser = snapshot.getValue(User.class);
        Intent intent = new Intent(MainActivity.this,Home.class);
        Common.currentUser = localUser;
        startActivity(intent);
        waitingDialog.dismiss();

    }

    @Override
    public void onCancelled(@NonNull @NotNull DatabaseError error) {
waitingDialog.dismiss();
Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT);
    }
});
                        }
                    });
                }
                else {}
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                waitingDialog.dismiss();
                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT);
            }
        });
    }

   /* private void StartLoginSystem(){
  startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
          .setAvailableProviders(providers).build(),REQUEST_CODE);
    }*/
/*
    private void login(String email, String pass) {
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(aVoid ->
                {
                    mDialog = new ProgressDialog(MainActivity.this);
                    mDialog.setMessage("انتظر من فضلك ...");
                    mDialog.show();
                    table_user.addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            //Check if user not exist in Database
                            if(dataSnapshot.child(firebaseAuth.getCurrentUser().getUid()).
                                    exists()) {
                                //Get user information
                                mDialog.dismiss();
                                User user = dataSnapshot.child(firebaseAuth.
                                getCurrentUser().
                                        getUid())
                                        .getValue(User.class);
                                user.setEmail(email);//set phone
                                if (user.getEmail().equals(email)) {

                                    //Toast.makeText(SignIn.this, "تم تسجيل!!!", Toast.LENGTH_SHORT).show();
                                    Intent homeIntent =
                                            new Intent(MainActivity.this, Home.class);
                                    Common.currentUser = user;
                                    startActivity(homeIntent);
                                    finish();
                                } else {

                                    Toast.makeText(MainActivity.this, "Wrong password!!!", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                mDialog.dismiss();
                                Toast.makeText(MainActivity.this, "User not exist !", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
        ).addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_LONG)
                .show());
    }*/

  /*  @Override

    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IdpResponse response = IdpResponse.fromResultIntent(data);
        if(resultCode==RESULT_OK){
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
         */
/*   checkUserFromFirebase(firebaseUser);
Intent intent = new Intent(MainActivity.this,Home.class);
startActivity(intent);*//*

        }
        else {
           // Toast.makeText(MainActivity.this,"تاكد من بياناتك",Toast.LENGTH_SHORT).show();

        }
    }
*/

}
