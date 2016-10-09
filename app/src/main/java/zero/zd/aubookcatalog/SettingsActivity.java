package zero.zd.aubookcatalog;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import zero.zd.aubookcatalog.adapter.SettingsAdapter;
import zero.zd.aubookcatalog.model.SettingsModel;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        List<SettingsModel> settingsModels = new ArrayList<>();

        settingsModels.add(new SettingsModel(SettingsModel.HEADING, "Profile"));
        settingsModels.add(new SettingsModel(SettingsModel.CONTENT, R.drawable.au_logo, "Change Username", "Admin"));
        settingsModels.add(new SettingsModel(SettingsModel.CONTENT, R.drawable.au_logo, "Change Password", ""));

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new SettingsAdapter(this, R.layout.settings_layout, settingsModels));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SettingsActivity.this, "You clicked" + position, Toast.LENGTH_SHORT).show();
            }
        });
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

    private class GetDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}
