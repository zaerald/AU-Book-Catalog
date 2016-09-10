package zero.zd.aubookcatalog;


import android.content.Context;
import android.content.DialogInterface;
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

public class DatabaseWorker extends AsyncTask<String[], Void, String> {

    private Context mContext;
    private AlertDialog mAlertDialog;

    private String mEncodeType = "UTF-8";

    public DatabaseWorker(Context context) {
        mContext = context;
    }

    @Override
    protected String doInBackground(String[]... strings) {

        String type = strings[0][0];

        String serverIp = "192.168.22.3";
        String loginUrl = "http://" + serverIp + "/aubookcatalog/login.php";

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

                String ret = "";
                String line = bufferedReader.readLine();
                if (line != null) {
                    ret += line;
                } else {
                    ret = "FAIL";
                }

                return ret;

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


}
