package zero.zd.aubookcatalog;

import android.app.Dialog;
import android.app.ProgressDialog;
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

    private SharedPreferences preferences;

    private EditText txtFirstName;
    private EditText txtLastName;
    private com.github.pinball83.maskededittext.MaskedEditText txtStudentId;
    private EditText txtUsername;
    private EditText txtPassword;
    private EditText txtConfirmPassword;
    private TextView txtError;
    private TextView txtError2;
    private TextView txtError3;
    private TextView txtError4;

    private String firstName;
    private String lastName;
    private String studentId;
    private String username;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);

        preferences = getSharedPreferences(ZHelper.PREFS, MODE_PRIVATE);

        txtFirstName = (EditText) findViewById(R.id.txtFirstName);
        txtLastName = (EditText) findViewById(R.id.txtLastName);
        txtStudentId = (com.github.pinball83.maskededittext.MaskedEditText) findViewById(R.id.txtViewStudentId);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        txtError = (TextView) findViewById(R.id.txtError);
        txtError2 = (TextView) findViewById(R.id.txtError2);
        txtError3 = (TextView) findViewById(R.id.txtError3);
        txtError4 = (TextView) findViewById(R.id.txtError4);

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pass = txtPassword.getText().toString();
                String confirmPass = txtConfirmPassword.getText().toString();

                if (confirmPass.equals(""))
                    return;
                if (!confirmPass.equalsIgnoreCase(pass))
                    txtError2.setVisibility(View.VISIBLE);
                else
                    txtError2.setVisibility(View.GONE);
            }
        });

        txtConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pass = txtPassword.getText().toString();
                String confirmPass = txtConfirmPassword.getText().toString();

                if (pass.equals(""))
                    return;
                if (!pass.equalsIgnoreCase(confirmPass))
                    txtError2.setVisibility(View.VISIBLE);
                else
                    txtError2.setVisibility(View.GONE);
            }
        });
    }

    public void onClickSignUp(View v) {

        firstName = txtFirstName.getText().toString();
        lastName = txtLastName.getText().toString();
        studentId = txtStudentId.getText().toString();
        username = txtUsername.getText().toString();
        password = txtPassword.getText().toString();
        String confirmPassword = txtConfirmPassword.getText().toString();

        //Log.i("NFO", "Unmasked Length: " + txtStudentId.getUnmaskedText().length());

        // validate all inputs
        if (firstName.equals("") || lastName.equals("") || studentId.equals("") ||
                username.equals("") || password.equals("") || confirmPassword.equals("")) {
            txtError.setVisibility(View.VISIBLE);
            return;
        } else
            txtError.setVisibility(View.GONE);

        // pass not equal
        if (!(password.equalsIgnoreCase(confirmPassword)))
            return;

        if (txtStudentId.getUnmaskedText().length() != 11) {
            txtError3.setVisibility(View.VISIBLE);
            return;
        } else
            txtError3.setVisibility(View.GONE);

        // manipulate strings
        firstName = nameFix(firstName);
        lastName = nameFix(firstName);;
        username = username.toLowerCase();

        // check if username does exist
        txtError4.setVisibility(View.GONE);
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
                    out += " "; i++;
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

            String registerUrl = "http://" + preferences.getString("serverIp", ZHelper.SERVER_IP) +
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
                        URLEncoder.encode("firstName", ZHelper.DB_ENCODE_TYPE) + "=" +
                        URLEncoder.encode(firstName, ZHelper.DB_ENCODE_TYPE) + "&" +

                        URLEncoder.encode("lastName", ZHelper.DB_ENCODE_TYPE) + "=" +
                        URLEncoder.encode(lastName, ZHelper.DB_ENCODE_TYPE) + "&" +

                        URLEncoder.encode("studentId", ZHelper.DB_ENCODE_TYPE) + "=" +
                        URLEncoder.encode(studentId, ZHelper.DB_ENCODE_TYPE) + "&" +

                        URLEncoder.encode("username", ZHelper.DB_ENCODE_TYPE) + "=" +
                        URLEncoder.encode(username, ZHelper.DB_ENCODE_TYPE) + "&" +

                        URLEncoder.encode("password", ZHelper.DB_ENCODE_TYPE) + "=" +
                        URLEncoder.encode(password, ZHelper.DB_ENCODE_TYPE);

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

            String registerUrl = "http://" + preferences.getString("serverIp", ZHelper.SERVER_IP) +
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

                String postData =URLEncoder.encode("username", ZHelper.DB_ENCODE_TYPE) + "=" +
                        URLEncoder.encode(username, ZHelper.DB_ENCODE_TYPE);

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
            } else if (result.equals("duplicate")) {
                txtError4.setVisibility(View.VISIBLE);
                return;
            } else {
                Snackbar.make(mView, "Something went wrong.", Snackbar.LENGTH_LONG).show();
            }

            Log.i("NFO", "user check NFO: " + result);

            View view = findViewById(R.id.activity_registration_layout);
            new RegisterTask(view).execute();
        }
    }


}

