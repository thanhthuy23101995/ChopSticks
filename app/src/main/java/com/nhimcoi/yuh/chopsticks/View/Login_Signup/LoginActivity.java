package com.nhimcoi.yuh.chopsticks.View.Login_Signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.nhimcoi.yuh.chopsticks.R;
import com.nhimcoi.yuh.chopsticks.View.Home.HomePageActivity;

public class LoginActivity extends AppCompatActivity
        implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, FirebaseAuth.AuthStateListener {

    Button btnLoginGG;
    GoogleApiClient apiClient;
    public static int RC_SIGN_IN = 99;
    public static int CHECK_PROVIDER = 0;
    FirebaseAuth firebaseAuth;
    TextView txtSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(FacebookSdk.getApplicationContext());
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signOut();
        btnLoginGG = (Button) findViewById(R.id.btnLoginGG);
        txtSignUp = (TextView) findViewById(R.id.txtsignup);
        btnLoginGG.setOnClickListener(this);
        txtSignUp.setOnClickListener(this);
        LoginGoogleSignInOptions();
    }

    //tích hợp loginGG vào app
    private void LoginGoogleSignInOptions() {
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions)
                .build();
    }

    private void mFirebaseAuthWithGoogle(String tokenId) {
        if (CHECK_PROVIDER == 1) {
            AuthCredential authCredential = GoogleAuthProvider.getCredential(tokenId, null);
            firebaseAuth.signInWithCredential(authCredential);

        }
    }

    private void LoginGG(GoogleApiClient googleApiClient) {
        CHECK_PROVIDER = 1;
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                GoogleSignInAccount account = signInResult.getSignInAccount();
                String tokenID = account.getIdToken();
                mFirebaseAuthWithGoogle(tokenID);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnLoginGG:
                LoginGG(apiClient);
                break;
            case R.id.txtsignup:
                SignUp();
                break;

        }
    }

    public void SignUp() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {

            Intent iTrangChu = new Intent(this, HomePageActivity.class);
            startActivity(iTrangChu);
        } else {

        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
