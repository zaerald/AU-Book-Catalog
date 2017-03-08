package zero.zd.aubookcatalog;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences mPreferences;
    private int mSelectedNav;

    private boolean mIsStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mPreferences = getSharedPreferences(ZHelper.PREFS, MODE_PRIVATE);

        // for loading images
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions).build();
        ImageLoader.getInstance().init(config);

        // create and set pdf directory
        File file = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (file != null && !file.exists()) {
            if (!file.mkdirs()) {
                Log.e("ERR", "Failed creation");
            }
        }
        ZHelper.getInstance().setPdf(file);


        mIsStarted = true;
        loadStudent();
        navigationView.setCheckedItem(R.id.nav_dashboard);
        new GetDashboardTask().execute();
    }

    private void reloadAll() {
        loadStudent();

        switch (mSelectedNav) {
            case ZHelper.NAV_DASHBOARD:
                execDashboard();
                break;
//            case ZHelper.NAV_ALL_BOOKS:
//                execAllBooks();
//                break;
            case ZHelper.NAV_DISCOVER_BOOK:
                execDiscover();
                break;
            case ZHelper.NAV_DOWNLOADED_PDF_BOOK:
                execDownloadedPdf();
                break;
            case ZHelper.NAV_FAVORITES:
                execFavorite();
                break;
        }
    }

    private void loadStudent() {
        // check if isLogged
        boolean isLogged = mPreferences.getBoolean(ZHelper.IS_LOGGED, false);

        String studId;
        if (!isLogged) {
            Bundle extras = getIntent().getExtras();
            studId = extras.getString("student_id", "");

            // set login to true
            SharedPreferences.Editor prefsEditor;
            prefsEditor = mPreferences.edit();
            prefsEditor.putBoolean(ZHelper.IS_LOGGED, true);
            prefsEditor.putString("student_id", studId);
            prefsEditor.apply();
        }

        studId = mPreferences.getString("student_id", "");
        View view = getWindow().getDecorView().getRootView();
        ZHelper.getInstance().setStudentId(studId);
        new GetNameTask(view).execute(studId);
    }

    private void execDashboard() {
        View view = getWindow().getDecorView().getRootView();
        new GetDashboardTask(view).execute();
    }


    private void execAllBooks() {
        View view = getWindow().getDecorView().getRootView();
        new GetBookTask(view).execute();
    }

    private void execDiscover() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.rootView, new DiscoverFragment()).commit();
    }

    private void execDownloadedPdf() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.rootView, new ReadBookFragment()).commit();
    }

    private void execFavorite() {
        View view = getWindow().getDecorView().getRootView();
        new GetFavoritetask(view).execute();
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_refresh:
                reloadAll();
                if (mSelectedNav == ZHelper.NAV_ALL_BOOKS)
                    execAllBooks();
                break;

            case R.id.action_setup_ip:
                startActivity(new Intent(this, SetupIPActivity.class));
                break;

            case R.id.action_logout:
                SharedPreferences.Editor prefsEditor;
                prefsEditor = mPreferences.edit();
                prefsEditor.putBoolean(ZHelper.IS_LOGGED, false);
                prefsEditor.apply();

                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        FragmentManager fragmentManager = getFragmentManager();

        switch(id) {

            case R.id.nav_dashboard:
                mSelectedNav = ZHelper.NAV_DASHBOARD;
                execDashboard();
                break;

            case R.id.nav_all_books:
                mSelectedNav = ZHelper.NAV_ALL_BOOKS;
                execAllBooks();
                break;

            case R.id.nav_discover:
                mSelectedNav = ZHelper.NAV_DISCOVER_BOOK;
//                fragmentManager.beginTransaction()
//                        .replace(R.id.rootView, new DiscoverFragment()).commit();
                execDiscover();
                break;

            case R.id.nav_downloaded_pdf:
                mSelectedNav = ZHelper.NAV_DOWNLOADED_PDF_BOOK;
                execDownloadedPdf();
                break;

            case R.id.nav_favorites:
                mSelectedNav = ZHelper.NAV_FAVORITES;
                execFavorite();
                break;

            // options
//            case R.id.nav_settings:
//                startActivity(new Intent(this, SettingsActivity.class));
//                break;

            case R.id.nav_info:
                startActivity(new Intent(this, InformationActivity.class));
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIsStarted)
            return;

        reloadAll();
    }

    private class GetNameTask extends AsyncTask<String, Void, List<UserModel>> {
        Dialog mLoadingDialog;

        // view from btn to create Snackbar
        View mView;

        GetNameTask(View view) {
            mView = view;
        }

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            mLoadingDialog = ProgressDialog.show(MainActivity.this, "Please wait", "Loading...");
        }

        @Override
        protected List<UserModel> doInBackground(String... strings) {

            String server = "http://" + mPreferences.getString("serverIp", ZHelper.SERVER_IP)
                    + "/aubookcatalog/";
            String getName = server + "getname.php";
            ZHelper.getInstance().setServer(server);

            try {
                String studentId = strings[0];

                URL url = new URL(getName);
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
                        URLEncoder.encode("studentId", ZHelper.DB_ENCODE_TYPE) + "=" +
                                URLEncoder.encode(studentId, ZHelper.DB_ENCODE_TYPE);

                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream, ZHelper.DB_ENCODE_TYPE));

                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                }

                String JsonResult = builder.toString();
                JSONObject jsonObject = new JSONObject(JsonResult);
                JSONArray jsonArray = jsonObject.getJSONArray("result");

                List<UserModel> userList = new ArrayList<>();
                JSONObject finalObject = jsonArray.getJSONObject(0);
                UserModel userModel = new UserModel();
                userModel.setStudentId(studentId);
                userModel.setUsername(finalObject.getString("username"));
                userModel.setFullname(finalObject.getString("fullname"));
                userList.add(userModel);


                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return userList;

            } catch(IOException | JSONException e) {
                Log.e("ERR", "Error in getting name: " + e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<UserModel> result) {
            //super.onPostExecute(s);
            mLoadingDialog.dismiss();

            TextView txtViewName = (TextView) findViewById(R.id.txtViewName);
            TextView txtViewStudentid = (TextView) findViewById(R.id.txtViewStudentId);
            TextView txtViewUsername = (TextView) findViewById(R.id.txtViewUsername);

            // check if connected
            if (result == null) {
                Snackbar.make(mView, "Please make sure that you are connected to the Internet.",
                        Snackbar.LENGTH_LONG).show();

                // set user data from prefs
                txtViewName.setText(mPreferences.getString("stud_fullname", ""));
                txtViewStudentid.setText(mPreferences.getString("student_id", ""));
                String usrOut = "@" + mPreferences.getString("stud_username", "");
                txtViewUsername.setText(usrOut);
                return;
            }
            UserModel user = result.get(0);

            txtViewName.setText(user.getFullname());
            txtViewStudentid.setText(user.getStudentId());
            String usrOut = "@" + user.getUsername();
            txtViewUsername.setText(usrOut);

            // write to prefs
            SharedPreferences.Editor prefsEditor;
            prefsEditor = mPreferences.edit();
            prefsEditor.putString("stud_fullname", user.getFullname());
            prefsEditor.putString("stud_username", user.getUsername());
            prefsEditor.apply();
        }
    }

    private class GetDashboardTask extends AsyncTask<Void, Object, String> {

        Dialog mLoadingDialog;
        View view;

        GetDashboardTask() {}

        GetDashboardTask(View view) {
            this.view = view;
        }


        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            if (mIsStarted)
                mIsStarted =  false;

            if (view != null)
                mLoadingDialog = ProgressDialog.show(MainActivity.this, "Please wait", "Loading...");
        }

        @Override
        protected String doInBackground(Void... strings) {
            String server = "http://" + mPreferences.getString("serverIp", ZHelper.SERVER_IP)
                    + "/aubookcatalog/";
            String getDash = server + "getdash.php";
            ZHelper.getInstance().setServer(server);

            try {

                URL url = new URL(getDash);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(3000);
                httpURLConnection.setReadTimeout(3000);

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream, ZHelper.DB_ENCODE_TYPE));

                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return builder.toString();

            } catch(IOException e) {
                Log.e("ERR", "Error in getting dash: " + e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (view != null)
                mLoadingDialog.dismiss();

            if (result == null && view != null) {
                Snackbar.make(view, "Please make sure that you are connected to the Internet.",
                        Snackbar.LENGTH_LONG).show();
            }

            FragmentManager fragmentManager = getFragmentManager();
            DashboardFragment dashboardFragment = new DashboardFragment();
            Bundle args = new Bundle();
            args.putString("result", result);
            dashboardFragment.setArguments(args);
            fragmentManager.beginTransaction()
                    .replace(R.id.rootView, dashboardFragment).commit();
        }


    }

    private class GetBookTask extends AsyncTask<Object, Object, String> {

        Dialog mLoadingDialog;
        View view;

        GetBookTask(View view) {
            this.view = view;
        }

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            mLoadingDialog = ProgressDialog.show(MainActivity.this, "Please wait", "Loading...");
        }

        @Override
        protected String doInBackground(Object... strings) {
            String server = "http://" + mPreferences.getString("serverIp", ZHelper.SERVER_IP)
                    + "/aubookcatalog/";
            String getBook = server + "getbook.php";

            try {

                URL url = new URL(getBook);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(3000);
                httpURLConnection.setReadTimeout(3000);

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);



                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream, ZHelper.DB_ENCODE_TYPE));

                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return builder.toString();

            } catch(IOException e) {
                Log.e("ERR", "Error in getting book: " + e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            mLoadingDialog.dismiss();


            if (result == null) {
                Snackbar.make(view, "Please make sure that you are connected to the Internet.",
                        Snackbar.LENGTH_LONG).show();
                // load old result
                result = mPreferences.getString(ZHelper.ALL_BOOKS_RESULT, null);
            } else {
                // refresh prefs
                SharedPreferences.Editor prefsEditor;
                prefsEditor = mPreferences.edit();
                prefsEditor.putString(ZHelper.ALL_BOOKS_RESULT, result);
                prefsEditor.apply();
            }

            FragmentManager fragmentManager = getFragmentManager();
            AllBooksFragment allBooksFragment = new AllBooksFragment();
            Bundle args = new Bundle();
            args.putString("result", result);
            allBooksFragment.setArguments(args);
            fragmentManager.beginTransaction()
                    .replace(R.id.rootView, allBooksFragment).commit();
        }


    }

    private class GetFavoritetask extends AsyncTask<Object, Object, String> {

        Dialog mLoadingDialog;
        View view;

        GetFavoritetask(View view) {
            this.view = view;
        }

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            mLoadingDialog = ProgressDialog.show(MainActivity.this, "Please wait", "Loading...");
        }

        @Override
        protected String doInBackground(Object... strings) {
            String server = "http://" + mPreferences.getString("serverIp", ZHelper.SERVER_IP)
                    + "/aubookcatalog/";
            String getBook = server + "getfav.php";

            try {
                String studentId = mPreferences.getString("student_id", null);

                URL url = new URL(getBook);
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
                        URLEncoder.encode("studentId", ZHelper.DB_ENCODE_TYPE) + "=" +
                                URLEncoder.encode(studentId, ZHelper.DB_ENCODE_TYPE);

                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();


                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream, ZHelper.DB_ENCODE_TYPE));

                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return builder.toString();

            } catch(IOException e) {
                Log.e("ERR", "Error in getting fav: " + e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            mLoadingDialog.dismiss();


            if (result == null) {
                Snackbar.make(view, "Please make sure that you are connected to the Internet.",
                        Snackbar.LENGTH_LONG).show();
                // load old result
                result = mPreferences.getString(ZHelper.FAV_BOOKS_RESULT, null);
            } else {
                // refresh prefs
                SharedPreferences.Editor prefsEditor;
                prefsEditor = mPreferences.edit();
                prefsEditor.putString(ZHelper.FAV_BOOKS_RESULT, result);
                prefsEditor.apply();
            }

            FragmentManager fragmentManager = getFragmentManager();
            FavoritesFragment favoritesFragment = new FavoritesFragment();
            Bundle args = new Bundle();
            args.putString("result", result);
            favoritesFragment.setArguments(args);
            fragmentManager.beginTransaction()
                    .replace(R.id.rootView, favoritesFragment).commit();
        }
    }


}
