package zero.zd.aubookcatalog;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

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
import java.util.ArrayList;
import java.util.List;

import zero.zd.aubookcatalog.model.UserModel;

public class BookInformationActivity extends AppCompatActivity {

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_information);

        // add up
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // load server string
        preferences = getSharedPreferences(ZConstants.PREFS, MODE_PRIVATE);

        // load book info
        new BookInformationTask().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class BookInformationTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String getName = "http://" + preferences.getString("serverIp", ZConstants.SERVER_IP)
                    + "/aubookcatalog/getbookinfo.php";

            try {
                String bookId = params[0];

                URL url = new URL(getName);
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
                        URLEncoder.encode("bookId", ZConstants.DB_ENCODE_TYPE) + "=" +
                                URLEncoder.encode(bookId, ZConstants.DB_ENCODE_TYPE);

                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream, ZConstants.DB_ENCODE_TYPE));

                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                }

                String JsonResult = builder.toString();
                JSONObject jsonObject = new JSONObject(JsonResult);
                JSONArray jsonArray = jsonObject.getJSONArray("result");

                List<UserModel> userList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject finalObject = jsonArray.getJSONObject(i);
                    UserModel userModel = new UserModel();
                    userModel.setStudentId(studentId);
                    userModel.setUsername(finalObject.getString("username"));
                    userModel.setFullname(finalObject.getString("fullname"));
                    userList.add(userModel);
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.i("NFO", "no err");

                return userList;

            } catch(IOException | JSONException e) {
                Log.e("ERR", "Error in getting name: " + e.getMessage());
            }

            return null;

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
