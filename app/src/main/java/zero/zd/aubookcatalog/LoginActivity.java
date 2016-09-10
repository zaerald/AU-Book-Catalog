package zero.zd.aubookcatalog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


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
        Toast.makeText(this, "Yeah", Toast.LENGTH_SHORT).show();

        String userName = txtID.getText().toString();
        String password = txtPass.getText().toString();
        String type = "login";

        String[] arr = {type, userName, password};

        DatabaseWorker databaseWorker = new DatabaseWorker(this);
        databaseWorker.execute(arr);



    }


}
