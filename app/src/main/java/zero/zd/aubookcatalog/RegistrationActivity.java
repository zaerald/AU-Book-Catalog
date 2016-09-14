package zero.zd.aubookcatalog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

public class RegistrationActivity extends AppCompatActivity {

    EditText txtFirstName;
    EditText txtLastName;
    EditText txtStudentId;
    EditText txtUsername;
    EditText txtPassword;
    EditText txtConfirmPassword;
    TextView txtError;
    TextView txtError2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_registration);

        txtFirstName = (EditText) findViewById(R.id.txtFirstName);
        txtLastName = (EditText) findViewById(R.id.txtLastName);
        txtStudentId = (EditText) findViewById(R.id.txtStudentId);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        txtError = (TextView) findViewById(R.id.txtError);
        txtError2 = (TextView) findViewById(R.id.txtError2);

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pass = txtPassword.getText().toString();
                String confirmPass = txtConfirmPassword.getText().toString();

                if (confirmPass.equals(""))
                    return;
                if (!confirmPass.equalsIgnoreCase(pass))
                    txtError2.setVisibility(View.VISIBLE);
                else
                    txtError2.setVisibility(View.GONE);
            }
        });

        txtConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pass = txtPassword.getText().toString();
                String confirmPass = txtConfirmPassword.getText().toString();

                if (pass.equals(""))
                    return;
                if (!pass.equalsIgnoreCase(confirmPass))
                    txtError2.setVisibility(View.VISIBLE);
                else
                    txtError2.setVisibility(View.GONE);
            }
        });
    }

    public void onClickRegister(View v) {

        String firstName = txtFirstName.getText().toString();
        String lastName = txtLastName.getText().toString();
        String studentId = txtStudentId.getText().toString();
        String username = txtUsername.getText().toString();
        String password = txtStudentId.getText().toString();
        String confirmPassword = txtStudentId.getText().toString();

        // validate all inputs
        if (firstName.equals("") || lastName.equals("") || studentId.equals("") ||
                username.equals("") || password.equals("") || confirmPassword.equals("")) {
            txtError.setVisibility(View.VISIBLE);
            return;
        }






    }



}

