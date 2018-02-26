package com.umkc.team11.lab3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "TwitterLogin";

    private TextView mStatusTextView;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    private TwitterLoginButton mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Configure Twitter SDK
            TwitterAuthConfig authConfig =  new TwitterAuthConfig(
            getString(R.string.twitter_consumer_key),
            getString(R.string.twitter_consumer_secret));

            TwitterConfig twitterConfig = new TwitterConfig.Builder(this)
            .twitterAuthConfig(authConfig)
            .build();

            Twitter.initialize(twitterConfig);

            // Inflate layout (must be done after Twitter is configured)
            setContentView(R.layout.activity_login);

            // Views
            mStatusTextView = findViewById(R.id.txt_status);
            findViewById(R.id.sign_out_and_disconnect).setOnClickListener(this);
            findViewById(R.id.to_home).setOnClickListener(this);
            findViewById(R.id.btn_register).setOnClickListener(this);

            // [START initialize_auth]
            // Initialize Firebase Auth
            mAuth = FirebaseAuth.getInstance();
            // [END initialize_auth]

            // [START initialize_twitter_login]
            mLoginButton = findViewById(R.id.login_button);
            mLoginButton.setCallback(new Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {
                    Log.d(TAG, "twitterLogin:success" + result);
                    handleTwitterSession(result.data);
                }

                @Override
                public void failure(TwitterException exception) {
                        Log.w(TAG, "twitterLogin:failure", exception);
                        updateUI(null);
                        }
                        });
                        // [END initialize_twitter_login] //
                }

    // [START on_start_check_user]
    @Override
    public void onStart() {
            super.onStart();
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();
            updateUI(currentUser);
            }
    // [END on_start_check_user]

    // [START on_activity_result]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            // Pass the activity result to the Twitter login button.
            mLoginButton.onActivityResult(requestCode, resultCode, data);
            }
    // [END on_activity_result]

    // [START auth_with_twitter]
    private void handleTwitterSession(TwitterSession session) {
            Log.d(TAG, "handleTwitterSession:" + session);


            AuthCredential credential = TwitterAuthProvider.getCredential(
            session.getAuthToken().token,
            session.getAuthToken().secret);

            mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
            // Sign in success, update UI with the signed-in user's information
            Log.d(TAG, "signInWithCredential:success");
            FirebaseUser user = mAuth.getCurrentUser();
            updateUI(user);
            } else {
            // If sign in fails, display a message to the user.
            Log.w(TAG, "signInWithCredential:failure", task.getException());
            Toast.makeText(LoginActivity.this, "Authentication failed.",
            Toast.LENGTH_SHORT).show();
            updateUI(null);
            }

            }
            });
            }
    // [END auth_with_twitter]

    private void signOut() {
            mAuth.signOut();
            TwitterCore.getInstance().getSessionManager().clearActiveSession();

            updateUI(null);
            }

    private void updateUI(FirebaseUser user) {
            if (user != null) {
                mStatusTextView.setText(user.getDisplayName());

                findViewById(R.id.login_button).setVisibility(View.GONE);
                findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
                findViewById(R.id.to_home).setVisibility(View.VISIBLE);
            } else {
                mStatusTextView.setText(R.string.signed_out);

                findViewById(R.id.login_button).setVisibility(View.VISIBLE);
                findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
                findViewById(R.id.to_home).setVisibility(View.GONE);
            }
    }

    private void toHome()
    {
        Intent redirect = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(redirect);
    }

    private void toRegister()
    {
        Intent redirect = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(redirect);
    }

    @Override
    public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.sign_out_and_disconnect) {
            signOut();
            }

            if (i == R.id.to_home)
            {
                toHome();
            }

        if (i == R.id.btn_register)
        {
            toRegister();
        }
    }
}
