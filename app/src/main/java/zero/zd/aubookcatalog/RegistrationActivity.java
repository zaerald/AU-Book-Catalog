package zero.zd.aubookcatalog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RegistrationActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;

    private EditText mFirstNameEditText;
    private EditText mLastNameEditText;
    private com.github.pinball83.maskededittext.MaskedEditText mStudentIdMaskedEditText;
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private EditText mConfirmPasswordEditText;
    private TextView mErrorTextView;
    private TextView mError2TextView;
    private TextView mError3TextView;
    private TextView mError4TextView;

    private String mFirstName;
    private String mLastName;
    private String mStudentId;
    private String mUsername;
    private String mPassword;


    public static Intent getStartIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);

        mPreferences = getSharedPreferences(ZHelper.PREFS, MODE_PRIVATE);

        mFirstNameEditText = (EditText) findViewById(R.id.txtFirstName);
        mLastNameEditText = (EditText) findViewById(R.id.txtLastName);
        mStudentIdMaskedEditText = (com.github.pinball83.maskededittext.MaskedEditText) findViewById(R.id.txtViewStudentId);
        mUsernameEditText = (EditText) findViewById(R.id.txtUsername);
        mPasswordEditText = (EditText) findViewById(R.id.txtPassword);
        mConfirmPasswordEditText = (EditText) findViewById(R.id.txtConfirmPassword);
        mErrorTextView = (TextView) findViewById(R.id.txtError);
        mError2TextView = (TextView) findViewById(R.id.txtError2);
        mError3TextView = (TextView) findViewById(R.id.txtError3);
        mError4TextView = (TextView) findViewById(R.id.txtError4);

        mPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pass = mPasswordEditText.getText().toString();
                String confirmPass = mConfirmPasswordEditText.getText().toString();

                if (confirmPass.equals(""))
                    return;
                if (!confirmPass.equalsIgnoreCase(pass))
                    mError2TextView.setVisibility(View.VISIBLE);
                else
                    mError2TextView.setVisibility(View.GONE);
            }
        });

        mConfirmPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pass = mPasswordEditText.getText().toString();
                String confirmPass = mConfirmPasswordEditText.getText().toString();

                if (pass.equals(""))
                    return;
                if (!pass.equalsIgnoreCase(confirmPass))
                    mError2TextView.setVisibility(View.VISIBLE);
                else
                    mError2TextView.setVisibility(View.GONE);
            }
        });
    }

    public void onClickSignUp(View v) {

        mFirstName = mFirstNameEditText.getText().toString();
        mLastName = mLastNameEditText.getText().toString();
        mStudentId = mStudentIdMaskedEditText.getText().toString();
        mUsername = mUsernameEditText.getText().toString();
        mPassword = mPasswordEditText.getText().toString();
        String confirmPassword = mConfirmPasswordEditText.getText().toString();

        //Log.i("NFO", "Unmasked Length: " + mStudentIdMaskedEditText.getUnmaskedText().length());

        // validate all inputs
        if (mFirstName.equals("") || mLastName.equals("") || mStudentId.equals("") ||
                mUsername.equals("") || mPassword.equals("") || confirmPassword.equals("")) {
            mErrorTextView.setVisibility(View.VISIBLE);
            return;
        } else
            mErrorTextView.setVisibility(View.GONE);

        // pass not equal
        if (!(mPassword.equalsIgnoreCase(confirmPassword)))
            return;

        if (mStudentIdMaskedEditText.getUnmaskedText().length() != 11) {
            mError3TextView.setVisibility(View.VISIBLE);
            return;
        } else
            mError3TextView.setVisibility(View.GONE);

        // manipulate strings
        mFirstName = nameFix(mFirstName);
        mLastName = nameFix(mFirstName);
        ;
        mUsername = mUsername.toLowerCase();

        // check if mUsername does exist
        mError4TextView.setVisibility(View.GONE);
        new CheckUserTask(v).execute();

    }

    private String nameFix(String name) {
        String out = "";

        if (name.length() == 1) {
            name = name.toUpperCase();
            return name;
        }
        // check if name has a space
        if (name.contains(" ")) {
            out += Character.toUpperCase(name.charAt(0));
            for (int i = 1; i < name.length(); i++) {
                if (name.charAt(i) == ' ') {
                    out += " ";
                    i++;
                    out += Character.toUpperCase(name.charAt(i));
                } else {
                    out += Character.toLowerCase(name.charAt(i));
                }
            }
            name = out;
        } else {
            name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        }

        return name;
    }

    private class RegisterTask extends AsyncTask<Void, Void, String> {
        Dialog mLoadingDialog;

        // view from btn to create Snackbar
        View mView;

        public RegisterTask(View view) {
            mView = view;
        }

        @Override
        protected String doInBackground(Void... strings) {

            String registerUrl = "http://" + mPreferences.getString("serverIp", ZHelper.SERVER_IP) +
                    "/aubookcatalog/register.php";

            try {

                URL url = new URL(registerUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(5000);

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(outputStream, ZHelper.DB_ENCODE_TYPE));

                String postData =
                        URLEncoder.encode("mFirstName", ZHelper.DB_ENCODE_TYPE) + "=" +
                                URLEncoder.encode(mFirstName, ZHelper.DB_ENCODE_TYPE) + "&" +

                                URLEncoder.encode("mLastName", ZHelper.DB_ENCODE_TYPE) + "=" +
                                URLEncoder.encode(mLastName, ZHelper.DB_ENCODE_TYPE) + "&" +

                                URLEncoder.encode("mStudentId", ZHelper.DB_ENCODE_TYPE) + "=" +
                                URLEncoder.encode(mStudentId, ZHelper.DB_ENCODE_TYPE) + "&" +

                                URLEncoder.encode("mUsername", ZHelper.DB_ENCODE_TYPE) + "=" +
                                URLEncoder.encode(mUsername, ZHelper.DB_ENCODE_TYPE) + "&" +

                                URLEncoder.encode("mPassword", ZHelper.DB_ENCODE_TYPE) + "=" +
                                URLEncoder.encode(mPassword, ZHelper.DB_ENCODE_TYPE);

                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream, ZHelper.DB_ENCODE_TYPE));

                String result = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.i("NFO", "no err");

                return result;

            } catch (IOException e) {
                Log.e("ERR", "Error in registration: " + e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            mLoadingDialog = ProgressDialog.show(RegistrationActivity.this, "Please wait", "Loading...");
        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            mLoadingDialog.dismiss();
            // check if connected
            if (s == null) {
                Snackbar.make(mView, "Please make sure that you are connected to the Internet.",
                        Snackbar.LENGTH_LONG).show();
                return;
            }

            String out = s.trim();

            if (out.equals("success")) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Snackbar.make(mView, "Something went wrong.", Snackbar.LENGTH_LONG).show();
            }

            Log.i("NFO", "Reg NFO: " + out);

        }

    }

    private class CheckUserTask extends AsyncTask<Void, Void, String> {
        Dialog mLoadingDialog;

        // view from btn to create Snackbar
        View mView;

        public CheckUserTask(View view) {
            mView = view;
        }

        @Override
        protected String doInBackground(Void... strings) {

            String registerUrl = "http://" + mPreferences.getString("serverIp", ZHelper.SERVER_IP) +
                    "/aubookcatalog/registerusernamecheck.php";

            try {

                URL url = new URL(registerUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(5000);

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(outputStream, ZHelper.DB_ENCODE_TYPE));

                String postData = URLEncoder.encode("mUsername", ZHelper.DB_ENCODE_TYPE) + "=" +
                        URLEncoder.encode(mUsername, ZHelper.DB_ENCODE_TYPE);

                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream, ZHelper.DB_ENCODE_TYPE));

                String result = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.i("NFO", "no err");

                return result;

            } catch (IOException e) {
                Log.e("ERR", "Error in registration: " + e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            mLoadingDialog = ProgressDialog.show(RegistrationActivity.this, "Please wait", "Loading...");
        }

        @Override
        protected void onPostExecute(String result) {
            //super.onPostExecute(s);
            mLoadingDialog.dismiss();
            // check if connected
            if (result == null) {
                Snackbar.make(mView, "Please make sure that you are connected to the Internet.",
                        Snackbar.LENGTH_LONG).show();
                return;
            }

            result = result.trim();

            if (result.equals("success")) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                View view = findViewById(R.id.activity_registration_layout);
                new RegisterTask(view).execute();
            } else if (result.equals("duplicate")) {
                mError4TextView.setVisibility(View.VISIBLE);
                return;
            } else {
                Snackbar.make(mView, "Something went wrong.", Snackbar.LENGTH_LONG).show();
                return;
            }

            Log.i("NFO", "user check NFO: " + result);


        }
    }


}

