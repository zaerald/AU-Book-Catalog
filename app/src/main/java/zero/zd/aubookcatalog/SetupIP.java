package zero.zd.aubookcatalog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class SetupIP extends AppCompatActivity {

    EditText txtServerIp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_ip);

        txtServerIp = (EditText) findViewById(R.id.txtServerIp);
    }

    public void onClickBtnUpdateIp(View v) {
        ZConstants zConstants = ZConstants.getInstance();
        String serverIp = txtServerIp.getText().toString();
        zConstants.setServerIp(serverIp);
    }
}
