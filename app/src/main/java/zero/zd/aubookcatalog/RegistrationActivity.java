package zero.zd.aubookcatalog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
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

    EditText txtFirstName;
    EditText txtLastName;
    EditText txtStudentId;
    EditText txtUsername;
    EditText txtPassword;
    EditText txtConfirmPassword;
    TextView txtError;
    TextView txtError2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_registration);

        txtFirstName = (EditText) findViewById(R.id.txtFirstName);
        txtLastName = (EditText) findViewById(R.id.txtLastName);
        txtStudentId = (EditText) findViewById(R.id.txtStudentId);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        txtError = (TextView) findViewById(R.id.txtError);
        txtError2 = (TextView) findViewById(R.id.txtError2);

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

    public void onClickRegister(View v) {

        String firstName = txtFirstName.getText().toString();
        String lastName = txtLastName.getText().toString();
        String studentId = txtStudentId.getText().toString();
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        String confirmPassword = txtConfirmPassword.getText().toString();

        // validate all inputs
        if (firstName.equals("") || lastName.equals("") || studentId.equals("") ||
                username.equals("") || password.equals("") || confirmPassword.equals("")) {
            txtError.setVisibility(View.VISIBLE);
            return;
        }

        if (!(password.equalsIgnoreCase(confirmPassword)))
            return;

        DatabaseWorker databaseWorker = new DatabaseWorker(v);
        databaseWorker.execute(firstName, lastName, studentId, username, password);

    }


    private class DatabaseWorker extends AsyncTask<String, Void, String> {
        Dialog mLoadingDialog;

        // view from btn to create Snackbar
        View mView;

        public DatabaseWorker(View view) {
            mView = view;
        }

        @Override
        protected String doInBackground(String... strings) {

            String registerUrl = "http://" + ZConstants.SERVER_IP + "/aubookcatalog/register.php";

            String firstName = strings[0];
            String lastName = strings[1];
            String studentId = strings[2];
            String username = strings[3];
            String password = strings[4];

            try {

                URL url = new URL(registerUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(5000);

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(outputStream, ZConstants.DB_ENCODE_TYPE));

                String postData =
                        URLEncoder.encode("firstName", ZConstants.DB_ENCODE_TYPE) + "=" +
                        URLEncoder.encode(firstName, ZConstants.DB_ENCODE_TYPE) + "&" +

                        URLEncoder.encode("lastName", ZConstants.DB_ENCODE_TYPE) + "=" +
                        URLEncoder.encode(lastName, ZConstants.DB_ENCODE_TYPE) + "&" +

                        URLEncoder.encode("studentId", ZConstants.DB_ENCODE_TYPE) + "=" +
                        URLEncoder.encode(studentId, ZConstants.DB_ENCODE_TYPE) + "&" +

                        URLEncoder.encode("username", ZConstants.DB_ENCODE_TYPE) + "=" +
                        URLEncoder.encode(username, ZConstants.DB_ENCODE_TYPE) + "&" +

                        URLEncoder.encode("password", ZConstants.DB_ENCODE_TYPE) + "=" +
                        URLEncoder.encode(password, ZConstants.DB_ENCODE_TYPE);

                Log.i("NFO", postData);

                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream, ZConstants.DB_ENCODE_TYPE));

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
}

