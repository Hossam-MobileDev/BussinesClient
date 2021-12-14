package com.hashtagco.bussinesclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
//import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hashtagco.bussinesclient.databinding.ActivityOptBinding;

public class OptActivity extends AppCompatActivity {
    ActivityOptBinding binding;
    FirebaseAuth auth;
    String verificationId;
    ProgressDialog dialog;
    DatabaseReference table_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOptBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
         FirebaseDatabase database = FirebaseDatabase.getInstance();
         table_user = database.getReference("User");
        dialog = new ProgressDialog(this);
        dialog.setMessage("Sending Otp...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

//        getSupportActionBar().hide();

        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        String name = getIntent().getStringExtra("Name");
        binding.phoneLbl.setText("Verify " + phoneNumber);
      /*  PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(OptActivity.this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential
                                                                phoneAuthCredential) {
                        signInWithPhoneAuthCredential(phoneAuthCredential);

                    }

                    @Override
                    public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {

                    }

                    @Override
                    public void onCodeSent(@NonNull @NotNull String s, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        dialog.dismiss();
                        verificationId = s;
                        super.onCodeSent(s, forceResendingToken);
                    }
                }).build();

        PhoneAuthProvider.verifyPhoneNumber(options);

        binding.otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId,otp);
                auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user = task.getResult().getUser();

                            startActivity(new Intent(getApplicationContext()
                                    ,Home.class));
                            finishAffinity();
                        }else{
                            Toast.makeText(OptActivity.this, "Wrong Otp", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });*/
    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                         //   Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            // Update UI
                        } else {
                            // Sign in failed, display a message and update the UI
                         //   Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
}