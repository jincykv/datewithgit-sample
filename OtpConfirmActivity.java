package com.business.supermarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class OtpConfirmActivity extends AppCompatActivity implements OtpVerificationWorker.Otpeventlistner {
    EditText txtotpcode;
    Button btnconfirm;
    OtpVerificationWorker otpworker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_confirm);
        txtotpcode=findViewById(R.id.etxtOtp_no);
        btnconfirm=findViewById(R.id.confirmOpt);

        if(getIntent()!=null){

            otpworker=new OtpVerificationWorker(getIntent().getStringExtra("number"),this,this);


        }



        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!txtotpcode.getText().toString().trim().isEmpty() || txtotpcode.getText().toString().trim().length() == 6){
                    otpworker.verifycode(txtotpcode.getText().toString());
              }else {

                    txtotpcode.setError("Enter valid Otp pin");
                }

            }
        });
    }

    @NonNull
    @Override
    public void OnverificationSuccesCallBack() {

     new AppSharedpreference(this).saveinprefernceString(AppSharedpreference.shopkey,getIntent().getStringExtra("number"));
     Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
     startActivity(new Intent(OtpConfirmActivity.this,MainActivity.class));
     finish();


    }

    @Override
    public void OnverficationFailedCallBack(String Cause) {
        Toast.makeText(this, Cause, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void OnCodeRecievedCallBack(int Status, String Code) {

    }
}