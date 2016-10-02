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

    private SharedPreferences preferences;

    private EditText txtUserName;
    private EditText txtPass;

    private Dialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        // Remove title bar
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        preferences = getSharedPreferences(ZConstants.PREFS, MODE_PRIVATE);

        // check if user has already logged in
        boolean isLogged = preferences.getBoolean(ZConstants.IS_LOGGED, false);
        if (isLogged) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtPass = (EditText) findViewById(R.id.txtPass);


    }

    public void onClickLogin(View v) {
        String userName = txtUserName.getText().toString();
        String password = txtPass.getText().toString();

        // validate input
        userName = userName.trim();
        password = password.trim();

        if (userName.equals("")) {
            Snackbar.make(v, "Please input your username", Snackbar.LENGTH_LONG).show();
            return;
        }
        if (password.equals("")) {
            Snackbar.make(v, "Please input your password", Snackbar.LENGTH_LONG).show();
            return;
        }

        DatabaseWorker databaseWorker = new DatabaseWorker(v);
        databaseWorker.execute(userName, password);
    }

    public void onClickSignUp(View v) {
        startActivity(new Intent(this, RegistrationActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch(id) {
            case R.id.action_info:
                startActivity(new Intent(this, InformationActivity.class));
                break;

            case R.id.action_setup_ip:
                startActivity(new Intent(this, SetupIPActivity.class));
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

    private class DatabaseWorker extends AsyncTask<String, Void, String>{

        // view from btn to create Snackbar
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

            String loginUrl = "http://" + preferences.getString("serverIp", ZConstants.SERVER_IP)
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
                            new OutputStreamWriter(outputStream, ZConstants.DB_ENCODE_TYPE));

                    String postData =
                            URLEncoder.encode("username", ZConstants.DB_ENCODE_TYPE) + "=" +
                            URLEncoder.encode(userName, ZConstants.DB_ENCODE_TYPE) + "&" +

                            URLEncoder.encode("password", ZConstants.DB_ENCODE_TYPE) + "=" +
                            URLEncoder.encode(password, ZConstants.DB_ENCODE_TYPE);

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

                } catch(IOException e) {
                    Log.e("ERR", "Error in login: " + e.getMessage());
                    e.printStackTrace();
                }

            return null;
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

            if(!s.equals(ZConstants.DB_FAIL)) {
                // parse
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                    String studId = jsonArray.getJSONObject(0).getString("student_id");

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("student_id", studId);
                    startActivity(intent);
                    finish();

                } catch(JSONException e) {
                    e.printStackTrace();
                }


            } else {
                Snackbar.make(mView, "Invalid username or password.", Snackbar.LENGTH_LONG).show();
            }

            Log.i("NFO", "Login NFO: " + out);

        }
    }


}
