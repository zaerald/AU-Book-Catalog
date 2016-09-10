package zero.zd.aubookcatalog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

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
        String userName = txtID.getText().toString();
        String password = txtPass.getText().toString();
        String type = "login";

        String[] arr = {type, userName, password};

        DatabaseWorker databaseWorker = new DatabaseWorker(this);
        databaseWorker.execute(arr);

        String result;
        try {
            result = databaseWorker.get();

            Log.i("NFO", "DatabaseWorker result: " + result);

            if (result.equals("success")) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else if (result.equals("fail")){
                Toast.makeText(this, "The ID doesn't exist", Toast.LENGTH_SHORT).show();
            }

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

    }


}
