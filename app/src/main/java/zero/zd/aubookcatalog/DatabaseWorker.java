package zero.zd.aubookcatalog;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;

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

public class DatabaseWorker extends AsyncTask<String[], Void, Boolean> {

    private Context mContext;
    private AlertDialog mAlertDialog;

    private String mEncodeType = "UTF-8";

    public DatabaseWorker(Context context) {
        mContext = context;
    }

    @Override
    protected Boolean doInBackground(String[]... strings) {

        String type = strings[0][0];

        String loginUrl = "http://10.0.2.2/aubookcatalog/login.php";

        if (type.equals("login")) {
            try {
                String userName = strings[0][1];
                String password = strings[0][2];

                URL url = new URL(loginUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(outputStream, mEncodeType));

                String postData =
                        URLEncoder.encode("userName", mEncodeType) + "=" +
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

                boolean b = false;
                String line = bufferedReader.readLine();
                if (line != null && line.equals("true")) {
                    b = true;
                }

                return b;

            } catch(IOException e) {
                Log.e("ERR", "Error in login: " + e.getMessage());
            }
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // dialog for failing login
        mAlertDialog = new AlertDialog.Builder(mContext).create();
        mAlertDialog.setTitle("Login Failed");
        mAlertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        Log.i("NFO", "aBoolean: " + aBoolean);

        if(aBoolean) {
            mContext.startActivity(new Intent(mContext, MainActivity.class));
        }
    }
}
