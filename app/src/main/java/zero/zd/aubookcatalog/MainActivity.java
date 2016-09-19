package zero.zd.aubookcatalog;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.view.WindowManager;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences preferences;
    private SharedPreferences.Editor prefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

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

        // welcome
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.rootView, new DashboardFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_dashboard);



        preferences = getSharedPreferences(ZConstants.SETTINGS, MODE_PRIVATE);

        // check if isLogged
        boolean isLogged = preferences.getBoolean(ZConstants.IS_LOGGED, false);



        String studId;
        if (!isLogged) {
            Bundle extras = getIntent().getExtras();
            studId = extras.getString(ZConstants.DB_STUDENT_ID, "");

            // set login to true
            prefsEditor = preferences.edit();
            prefsEditor.putBoolean(ZConstants.IS_LOGGED, true);
            prefsEditor.putString(ZConstants.DB_STUDENT_ID, studId);
            prefsEditor.apply();
        }

        studId = preferences.getString(ZConstants.DB_STUDENT_ID, "");
        View v = getWindow().getDecorView().getRootView();
        new DatabaseWorker(v).execute(studId);

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

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                break;

            case R.id.action_logout:
                prefsEditor = preferences.edit();
                prefsEditor.putBoolean(ZConstants.IS_LOGGED, false);
                prefsEditor.apply();

                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;

        }

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getFragmentManager();

        switch(id) {

            case R.id.nav_dashboard:
                fragmentManager.beginTransaction()
                        .replace(R.id.rootView, new DashboardFragment()).commit();
                break;

            case R.id.nav_all_books:
                fragmentManager.beginTransaction()
                        .replace(R.id.rootView, new AllBooksFragment()).commit();
                break;

            case R.id.nav_discover:
                fragmentManager.beginTransaction()
                        .replace(R.id.rootView, new DiscoverFragment()).commit();
                break;

            case R.id.nav_favorites:
                fragmentManager.beginTransaction()
                        .replace(R.id.rootView, new FavoritesFragment()).commit();
                break;

            case R.id.nav_recent:
                fragmentManager.beginTransaction()
                        .replace(R.id.rootView, new RecentBorrowedFragment()).commit();
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

            String getName = "http://" + preferences.getString("serverIp", ZConstants.SERVER_IP)
                    + "/aubookcatalog/getname.php";

            try {
                String studentId = strings[0];

                URL url = new URL(getName);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(3000);

                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(
                        new OutputStreamWriter(outputStream, ZConstants.DB_ENCODE_TYPE));

                String postData =
                        URLEncoder.encode("studentId", ZConstants.DB_ENCODE_TYPE) + "=" +
                                URLEncoder.encode(studentId, ZConstants.DB_ENCODE_TYPE);

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
                Log.e("ERR", "Error in getting name: " + e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            mLoadingDialog = ProgressDialog.show(MainActivity.this, "Please wait", "Loading...");
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

                String name = "";
                String username = "";
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jo = jsonArray.getJSONObject(i);
                        username = jo.getString("username");
                        name = jo.getString("name");
                    }

                } catch(JSONException e) {
                    e.printStackTrace();
                }

                TextView txtViewName = (TextView) findViewById(R.id.txtViewName);
                TextView txtViewUsername = (TextView) findViewById(R.id.txtViewUsername);

                txtViewName.setText(name);
                String usrOut = "@" + username;
                txtViewUsername.setText(usrOut);

            } else {
                Snackbar.make(mView, "Cannot find Student ID", Snackbar.LENGTH_LONG).show();
            }

            Log.i("NFO", "Getting Name NFO: " + out);


        }
    }
}
