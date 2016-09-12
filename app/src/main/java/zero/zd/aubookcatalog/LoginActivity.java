package zero.zd.aubookcatalog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

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

    private EditText txtUserName;
    private EditText txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtPass = (EditText) findViewById(R.id.txtPass);
    }

    public void btnLoginClick(View v) {
        String userName = txtUserName.getText().toString();
        String password = txtPass.getText().toString();

        DatabaseWorker databaseWorker = new DatabaseWorker();
        databaseWorker.execute(userName, password);
    }

    public void btnRegistrationClick(View v) {

        

    }

    private class DatabaseWorker extends AsyncTask<String, Void, String>{
        Dialog mLoadingDialog;

        String mEncodeType = "UTF-8";

        @Override
        protected String doInBackground(String... strings) {

            String serverIp = "192.168.22.7";
            String loginUrl = "http://" + serverIp + "/aubookcatalog/login.php";
            //String loginUrl = "http://192.168.1.100/aubookcatalog/login.php";
            //String loginUrl = "http://192.168.1.100/programmingknowledge/login.php";
                try {
                    String userName = strings[0];
                    String password = strings[1];

                    URL url = new URL(loginUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setConnectTimeout(5000);

                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(
                            new OutputStreamWriter(outputStream, mEncodeType));

                    String postData =
                            URLEncoder.encode("username", mEncodeType) + "=" +
                            URLEncoder.encode(userName, mEncodeType) + "&" +

                            URLEncoder.encode("password", mEncodeType) + "=" +
                            URLEncoder.encode(password, mEncodeType);

                    bufferedWriter.write(postData);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(inputStream, mEncodeType));

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
                }


            return null;
        }

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
           mLoadingDialog = ProgressDialog.show(LoginActivity.this, "Please wait", "Loading...");
        }

        @Override
        protected void onPostExecute(String s) {
            //super.onPostExecute(s);
            mLoadingDialog.dismiss();
            // check if connected
            if (s == null) {
                Toast.makeText(getApplicationContext(),
                        "Please make sure that you are connected to the Internet.", Toast.LENGTH_SHORT).show();
                return;
            }

            String out = s.trim();

            if(out.equals("success")) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Invalid User Name or Password", Toast.LENGTH_SHORT).show();
            }

            Log.i("NFO", out);

        }
    }


}
