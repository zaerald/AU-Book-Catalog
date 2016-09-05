package zero.zd.aubookcatalog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private EditText txtID;
    private EditText txtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtID = (EditText) findViewById(R.id.txtID);
        txtPass = (EditText) findViewById(R.id.txtPass);
    }

    public void btnLoginClick(View v) {
        String id = txtID.getText().toString();
        String pass = txtPass.getText().toString();

        startActivity(new Intent(this, MainActivity.class));
        finish();

//        if (id.length() < 13) {
//            Toast.makeText(this, "Please enter proper ID", Toast.LENGTH_SHORT).show();
//            txtID.setText("");
//            return;
//        }
//
//        if (id.equals("01-1415-00736") && pass.equals("LZAERALD")) {
//            // load main activity
//            Intent mainActivity = new Intent(this, MainActivity.class);
//            startActivity(mainActivity);
//            finish();
//        } else {
//            Toast.makeText(this, "The ID doesn't exist", Toast.LENGTH_SHORT).show();
//        }


    }


}
