package zero.zd.aubookcatalog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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


public class LoginActivity extends AppCompatActivity {

    private SharedPreferences mPreferences;

    private EditText mUserNameEditText;
    private EditText mPassEditText;

    private Dialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        mPreferences = getSharedPreferences(ZHelper.PREFS, MODE_PRIVATE);

        // check if user has already logged in
        boolean isLogged = mPreferences.getBoolean(ZHelper.IS_LOGGED, false);
        if (isLogged) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        mUserNameEditText = (EditText) findViewById(R.id.txtUserName);
        mPassEditText = (EditText) findViewById(R.id.txtPass);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                startActivity(InformationActivity.getStartIntent(this));
                break;

            case R.id.action_setup_ip:
                startActivity(SetupIpActivity.getStartIntent(this));
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        try {
            if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
                mLoadingDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    public void onClickLogin(View view) {
        String userName = mUserNameEditText.getText().toString();
        String password = mPassEditText.getText().toString();

        if (!isInputValid(userName)) {
            Snackbar.make(view, "Please input your username", Snackbar.LENGTH_LONG).show();
            return;
        }
        if (!isInputValid(password)) {
            Snackbar.make(view, "Please input your password", Snackbar.LENGTH_LONG).show();
            return;
        }

        DatabaseWorker databaseWorker = new DatabaseWorker(view);
        databaseWorker.execute(userName, password);
    }

    public void onClickSignUp(View v) {
        startActivity(RegistrationActivity.getStartIntent(this));
    }

    private boolean isInputValid(String input) {
        input = input.trim();
        return !input.equals("");
    }

    private class DatabaseWorker extends AsyncTask<String, Void, String> {

        View mView;

        public DatabaseWorker(View view) {
            mView = view;
        }

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            mLoadingDialog = ProgressDialog.show(LoginActivity.this, "Please wait", "Loading...");
        }

        @Override
        protected String doInBackground(String... strings) {

            String loginUrl = "http://" + mPreferences.getString("serverIp", ZHelper.SERVER_IP)
                    + "/aubookcatalog/login.php";

            try {
                String userName = strings[0];
                String password = strings[1];

                URL url = new URL(loginUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(3000);
                httpURLConnection.setReadTimeout(3000);

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(outputStream, ZHelper.DB_ENCODE_TYPE));

                String postData =
                        URLEncoder.encode("username", ZHelper.DB_ENCODE_TYPE) + "=" +
                                URLEncoder.encode(userName, ZHelper.DB_ENCODE_TYPE) + "&" +

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
                Log.e("ERR", "Error in login: " + e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mLoadingDialog.dismiss();

            if (s == null) {
                Snackbar.make(mView, "Please make sure that you are connected to the Internet.",
                        Snackbar.LENGTH_LONG).show();
                return;
            }

            String out = s.trim();

            if (!s.equals(ZHelper.DB_FAIL)) {
                // parse
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                    String studId = jsonArray.getJSONObject(0).getString("student_id");

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("student_id", studId);
                    startActivity(intent);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {
                Snackbar.make(mView, "Invalid username or password.", Snackbar.LENGTH_LONG).show();
            }

            Log.i("NFO", "Login NFO: " + out);
        }
    }
}
