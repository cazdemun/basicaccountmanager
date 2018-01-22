package com.example.cleonurb.basicsecondauth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * Basic Sample of an app that can interact with the Android Account Manager System
 * It just creates accounts and displays a list of the accounts registered with the app's account type
 * Authentication logic is not existent, but it is "implemented"
 * It also shows how to call an intent from the configuration page
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    // UI references
    private EditText mUsernameView;
    private EditText mPasswordView;

    // Account Manager references
    private AccountManager mAccountManager;
    private Account mCurrentAccount;

    // Account type, important (maybe should be declared in resources or a special class)
    final String ACCOUNT_TYPE = "cplu.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Setting up the elements on the layout
        mUsernameView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        Button mCreateAccountButton = (Button) findViewById(R.id.create_account_button);
        mCreateAccountButton.setOnClickListener(this);

        Button mShowAccountsButton = (Button) findViewById(R.id.show_accounts_button);
        mShowAccountsButton.setOnClickListener(this);

        // Instance of the Account Manager System
        mAccountManager = AccountManager.get(this);

    }

    // On click events for the buttons, added here to avoid overhead on the onCreate() method
    @Override
    public void onClick(View v){
        switch(v.getId()){

            case R.id.create_account_button:

                createAccount();

                logToast(":)");
                break;
            case R.id.show_accounts_button:

                showAccounts();

                logToast(":)");
                break;
        }
    }

    // Button methods
    private void createAccount(){
        // A bundle type should be passed as a parameter, but it can be empty
        mCurrentAccount = new Account(mUsernameView.getText().toString(), ACCOUNT_TYPE);
        Bundle userData = new Bundle();

        // Password field in the app is not used, dummy pass "1234" is always added as a parameter
        boolean addAccountResult = mAccountManager.addAccountExplicitly(mCurrentAccount, "1234", userData);

        if (addAccountResult){
            logToast("Success");
        } else {
            logToast("Error");
        }
    }

    private void showAccounts(){
        // String buffer is used as a simple way to display a list in an Alert Dialog
        // In theory, getAccountsByType() should be able to call any accounts of any type, as long as the ACCOUNT_TYPE field is correct
        Account[] accountByType = mAccountManager.getAccountsByType(ACCOUNT_TYPE);
        StringBuffer buffer = new StringBuffer();

        for (Account account : accountByType){
            buffer.append("* " + account.name + " " + account.type + "\n");
        }

        showDialog(buffer.toString(), "List of accounts");
    }

    // Utility function
    public void logToast(CharSequence message){
        // A toast is used as a way to test if a function is actually being called or behaving correctly
        // A log function for the Logcat can be used in the same way
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    public void showDialog(String message, String title){
        // Alert dialogs are used for its simplicity
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle(title);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}



