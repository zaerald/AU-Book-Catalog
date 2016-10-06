package zero.zd.aubookcatalog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

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

        SettingsModel[] settingsModels = new SettingsModel[2];
        settingsModels[0] = new SettingsModel(SettingsModel.HEADING, "Palitan mo icons");
        settingsModels[1] = new SettingsModel(SettingsModel.CONTENT, R.drawable.au_logo, "Dota Muna", "OO nga namn dota na muna");

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new SettingsAdapter(this, R.layout.settings_layout, settingsModels));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
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
}
