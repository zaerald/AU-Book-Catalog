package zero.zd.aubookcatalog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
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

        List<SettingsModel> settingsModels = new ArrayList<>();

        settingsModels.add(new SettingsModel(SettingsModel.HEADING, "Palitan mo icons"));
        settingsModels.add(new SettingsModel(SettingsModel.CONTENT, R.drawable.au_logo, "Dota Muna", "OO nga namn dota na muna"));
        settingsModels.add(new SettingsModel(SettingsModel.CONTENT, R.drawable.dash_dummy, "Ano hero?", "Random!!!!"));
        settingsModels.add(new SettingsModel(SettingsModel.HEADING, "Dota na ituuu"));
        settingsModels.add(new SettingsModel(SettingsModel.CONTENT, R.drawable.dash_dummy, "Meepo", "Meepo 1"));
        settingsModels.add(new SettingsModel(SettingsModel.CONTENT, R.drawable.dash_dummy, "Meepo", "Meepo 2"));
        settingsModels.add(new SettingsModel(SettingsModel.CONTENT, R.drawable.dash_dummy, "Meepo", "Meepo 3"));
        settingsModels.add(new SettingsModel(SettingsModel.CONTENT, R.drawable.dash_dummy, "Meepo", "Meepo 4"));
        settingsModels.add(new SettingsModel(SettingsModel.CONTENT, R.drawable.dash_dummy, "Meepo", "Meepo 5"));

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new SettingsAdapter(this, R.layout.settings_layout, settingsModels));

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
