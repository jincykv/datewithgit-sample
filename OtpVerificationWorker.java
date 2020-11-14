package com.business.supermarket;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpVerificationWorker {

    private FirebaseAuth firebaseAuth;
    private String Mobileno;
    private String Ccode="+91";
    private String Vcode;
    private final String TAG="otpverification.class";
    private String verificstionId;
    private String DashMode;
    Context context;
    Otpeventlistner otpeventlistner;
    Handler handler;



    public OtpVerificationWorker(String Cmobileno, Context mcontext, Otpeventlistner otpeventlistner) {
        this.Mobileno = Cmobileno;
        this.context=mcontext;
        this.otpeventlistner=otpeventlistner;
        handler=new Handler();

       init();
        sentverificationCode(Mobileno);
        Log.d(TAG,"PhoneNumber:"+Mobileno);



    }
    public void init(){
        firebaseAuth=FirebaseAuth.getInstance();
        Log.i(TAG,""+TAG+"::invoked");
    }

    public void getVerificationcode(String Vrcode) {
        Vcode = Vrcode;

        if (Vcode.length()>= 6) {

            verifycode(Vcode);
        }
        else{
            Toast.makeText(context.getApplicationContext(),"Invalid otp code...", Toast.LENGTH_LONG).show();
        }
    }

    public void verifycode(String code){
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificstionId,code);
        siginwithcredential(credential);

    }

    private void siginwithcredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()) {

                    otpeventlistner.OnverificationSuccesCallBack();



                  }else{
                       otpeventlistner.OnverficationFailedCallBack(task.getException().getMessage());
                }

            }
        });
    }



    private void sentverificationCode(String number){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number,40,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,mCallBack);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken){
            verificstionId=s;
            Log.i("Vcode","verification Code sent!");
            //otpeventlistner.OnCodeRecievedCallBack(Constants.OTP_CODESENDFLAG,"");
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            final String Code=phoneAuthCredential.getSmsCode();
            //otpeventlistner.OnCodeRecievedCallBack(Constants.OTP_CODERECIEVED,Code);

            if( Code!=null){

                //verifycode(Code);

            }



        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            otpeventlistner.OnverficationFailedCallBack(e.getMessage());


        }



    };

     public void resendotp(){

         sentverificationCode(Ccode+Mobileno);
     }
     public abstract interface Otpeventlistner{
         @NonNull
           void OnverificationSuccesCallBack();
           void OnverficationFailedCallBack(String Cause);
           void OnCodeRecievedCallBack(int Status, String Code);


     }
}
