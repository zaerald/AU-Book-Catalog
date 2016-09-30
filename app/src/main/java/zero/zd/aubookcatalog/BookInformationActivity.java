package zero.zd.aubookcatalog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

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

import zero.zd.aubookcatalog.model.BookModel;

public class BookInformationActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private long bookId;
    private String bookType;
    private String studentId;

    private boolean isFavorite;


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
        // load studId
        studentId = preferences.getString("student_id", null);
        Bundle extras = getIntent().getExtras();
        bookId = extras.getLong("bookId");
        bookType = extras.getString("bookType");

        // load book info
        new BookInformationTask().execute(bookId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case R.id.action_refresh:
                new BookInformationTask().execute(bookId);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_book_info, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public void onClickFavorite (View view) {
        new FavoriteTask().execute();
    }

    private void setFavoriteImage() {
        ImageView imgView = (ImageView) findViewById(R.id.imgStar);

        if (isFavorite)
            imgView.setImageResource(R.drawable.ic_star_1);
        else
            imgView.setImageResource(R.drawable.ic_star_0);

    }


    class BookInformationTask extends AsyncTask<Long, String, List<BookModel>> {

        Dialog mLoadingDialog;
        boolean isBook;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingDialog = ProgressDialog.show(BookInformationActivity.this, "Please wait", "Loading...");
        }

        @Override
        protected List<BookModel> doInBackground(Long... params) {
            String getInfo = "http://" + preferences.getString("serverIp", ZConstants.SERVER_IP)
                    + "/aubookcatalog/";

            if(bookType.equals("Book")) {
                getInfo += "getbookinfo.php";
                isBook = true;
            }
            else {
                getInfo += "getbookinfopdf.php";
                isBook = false;
            }


            try {
                long bookId = params[0];

                URL url = new URL(getInfo);
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
                                URLEncoder.encode(bookId + "", ZConstants.DB_ENCODE_TYPE);

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

                List<BookModel> bookList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject finalObject = jsonArray.getJSONObject(i);
                    BookModel bookModel = new BookModel();
                    bookModel.setBookId(finalObject.getInt("book_id"));
                    bookModel.setBookTitle(finalObject.getString("book_title"));
                    bookModel.setBookImage(finalObject.getString("book_img"));
                    bookModel.setAuthor(finalObject.getString("author"));
                    bookModel.setSubject(finalObject.getString("subject"));
                    bookModel.setPages(finalObject.getInt("book_page"));
                    bookModel.setType(finalObject.getString("type"));

                    if (isBook) {
                        bookModel.setAvailable(finalObject.getInt("available"));
                        bookModel.setTotal(finalObject.getInt("total"));
                    }
                    bookModel.setDescription(finalObject.getString("description"));
                    bookList.add(bookModel);
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.i("NFO", "no err");

                return bookList;

            } catch(IOException | JSONException e) {
                Log.e("ERR", "Error in getting name: " + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<BookModel> bookModel) {
            super.onPostExecute(bookModel);

            mLoadingDialog.dismiss();

            if (bookModel == null) {
                View view = findViewById(R.id.activity_book_information);
                Snackbar.make(view, ZConstants.NO_CONN_PROMPT, Snackbar.LENGTH_SHORT).show();
                return;
            }

            TextView tvBookTitle = (TextView) findViewById(R.id.tvBookTitle);
            ImageView imgBook = (ImageView) findViewById(R.id.imgBook);
            final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
            TextView tvAuthor = (TextView) findViewById(R.id.tvAuthor);
            TextView tvSubject = (TextView) findViewById(R.id.tvSubject);
            TextView tvPages = (TextView) findViewById(R.id.tvPages);
            TextView tvType = (TextView) findViewById(R.id.tvType);
            TextView tvAvailable= (TextView) findViewById(R.id.tvAvailable);
            TextView tvTotal = (TextView) findViewById(R.id.tvTotal);
            TextView tvDescription = (TextView) findViewById(R.id.tvDescription);
            Button btnDownload = (Button) findViewById(R.id.btnDownload);


             tvBookTitle.setText(bookModel.get(0).getBookTitle());
            String author = "Author: " + bookModel.get(0).getAuthor();
            ImageLoader.getInstance().displayImage(bookModel.get(0).getBookImage(), imgBook, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    progressBar.setVisibility(View.GONE);
                }
            });
            tvAuthor.setText(author);
            String subject = "Subject: " + bookModel.get(0).getSubject();
            tvSubject.setText(subject);
            String pages = "Pages: " + bookModel.get(0).getPages();
            tvPages.setText(pages);
            String type = "Type: " + bookModel.get(0).getType();
            tvType.setText(type);

            if (isBook) {
                String available = "No. of Books Available: " + bookModel.get(0).getAvailable();
                tvAvailable.setText(available);
                String total = "Total No. of Books: : " + bookModel.get(0).getAvailable();
                tvTotal.setText(total);
                btnDownload.setVisibility(View.GONE);
            } else {
                tvAvailable.setVisibility(View.GONE);
                tvTotal.setVisibility(View.GONE);
            }
            tvDescription.setText(bookModel.get(0).getDescription());

            new CheckFavoriteTask().execute();
        }
    }

    class CheckFavoriteTask extends AsyncTask<Void, Void, String> {

        Dialog mLoadingDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingDialog = ProgressDialog.show(BookInformationActivity.this, "Please wait", "Loading...");
        }

        @Override
        protected String doInBackground(Void... params) {
            String getFav = "http://" + preferences.getString("serverIp", ZConstants.SERVER_IP)
                    + "/aubookcatalog/getbookfav.php";

            try {
                URL url = new URL(getFav);
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
                        URLEncoder.encode("studentId", ZConstants.DB_ENCODE_TYPE) + "=" +
                                URLEncoder.encode(studentId, ZConstants.DB_ENCODE_TYPE) + "&" +

                        URLEncoder.encode("bookId", ZConstants.DB_ENCODE_TYPE) + "=" +
                            URLEncoder.encode(bookId + "", ZConstants.DB_ENCODE_TYPE);

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

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return builder.toString();

            }catch(IOException e) {
                Log.e("ERR", "Error in getting updatefav: " + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            mLoadingDialog.dismiss();

            if (result.equals("success"))
                isFavorite = true;
            else if(result.equals("none"))
                isFavorite = false;
            else
                Log.e("ERR", "Error in retrieving fav");

            // update
            setFavoriteImage();
            Log.i("NFO", "Check result: " + result);
            Log.i("NFO", "Check Fav: " + isFavorite);
        }
    }

    class FavoriteTask extends AsyncTask<Void, String, String> {

        Dialog mLoadingDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingDialog = ProgressDialog.show(BookInformationActivity.this, "Please wait", "Loading...");
        }

        @Override
        protected String doInBackground(Void... params) {
            String getName = "http://" + preferences.getString("serverIp", ZConstants.SERVER_IP)
                    + "/aubookcatalog/setbookfav.php";

            try {

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
                        URLEncoder.encode("studentId", ZConstants.DB_ENCODE_TYPE) + "=" +
                                URLEncoder.encode(studentId + "", ZConstants.DB_ENCODE_TYPE) + "&" +

                                URLEncoder.encode("bookId", ZConstants.DB_ENCODE_TYPE) + "=" +
                                URLEncoder.encode(bookId + "", ZConstants.DB_ENCODE_TYPE) + "&" +

                                URLEncoder.encode("fav", ZConstants.DB_ENCODE_TYPE) + "=" +
                                URLEncoder.encode(isFavorite + "", ZConstants.DB_ENCODE_TYPE);

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

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                Log.i("NFO", "no err");

                return builder.toString();

            } catch(IOException  e) {
                Log.e("ERR", "Error in getting name: " + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            mLoadingDialog.dismiss();

            if (result == null)
                return;

            if(result.equals("success"))
                isFavorite = !isFavorite;


            Log.i("NFO", "CHECK RESULT: " + result + " : fav: " + isFavorite);
            setFavoriteImage();

            View view = findViewById(R.id.activity_book_information);
            if(isFavorite)
                Snackbar.make(view, "Book is added to favorites.", Snackbar.LENGTH_SHORT).show();
            else
                Snackbar.make(view, "Book is removed from favorites.", Snackbar.LENGTH_SHORT).show();


        }
    }

}
