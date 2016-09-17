package zero.zd.aubookcatalog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SetupIPActivity extends AppCompatActivity {


    EditText txtServerIp;

    ZConstants zConstants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_ip);

        zConstants = ZConstants.getInstance();
        txtServerIp = (EditText) findViewById(R.id.txtServerIp);
        updateText();
    }

    public void onClickBtnUpdateIp(View v) {
        String serverIp = txtServerIp.getText().toString();
        zConstants.setServerIp(serverIp);
        updateText();
        Toast.makeText(this, "Server IP Updated!", Toast.LENGTH_SHORT).show();
    }

    private void updateText() {
        TextView textViewCurIp = (TextView) findViewById(R.id.textViewCurIP);
        textViewCurIp.setText(zConstants.getServerIp());
    }
}
