package zero.zd.aubookcatalog;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SetupIPActivity extends AppCompatActivity {

    // save states
    SharedPreferences preferences;
    SharedPreferences.Editor prefsEditor;

    EditText txtServerIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_ip);

        // set nav up
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        preferences = getSharedPreferences(ZConstants.PREFS, MODE_PRIVATE);
        txtServerIp = (EditText) findViewById(R.id.txtServerIp);
        updateText();
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

    public void onClickBtnUpdateIp(View v) {
        String serverIp = txtServerIp.getText().toString();

        // save prefs
        preferences = getSharedPreferences("settings", MODE_PRIVATE);
        prefsEditor = preferences.edit();
        prefsEditor.putString("serverIp", serverIp);
        prefsEditor.apply();

        updateText();
        Toast.makeText(this, "Server IP Updated!", Toast.LENGTH_SHORT).show();

        txtServerIp.setText("");
    }

    private void updateText() {
        TextView textViewCurIp = (TextView) findViewById(R.id.textViewCurIP);
        String serverIp = preferences.getString("serverIp", ZConstants.SERVER_IP);
        String out = "Current IP: " + serverIp;
        textViewCurIp.setText(out);
    }

}
