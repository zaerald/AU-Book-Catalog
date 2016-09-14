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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_registration);

        final EditText txtPassword = (EditText) findViewById(R.id.txtPassword);
        final EditText txtConfirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);

        final TextView txtError = (TextView) findViewById(R.id.txtError);

        txtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String pass = txtPassword.getText().toString().trim();
                String confirmPass = txtConfirmPassword.getText().toString().trim();

                if (confirmPass.equals(""))
                    return;
                if (!confirmPass.equalsIgnoreCase(pass))
                    txtError.setVisibility(View.VISIBLE);
                else
                    txtError.setVisibility(View.GONE);
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
                String pass = txtPassword.getText().toString().trim();
                String confirmPass = txtConfirmPassword.getText().toString().trim();

                if (pass.equals(""))
                    return;
                if (!pass.equalsIgnoreCase(confirmPass))
                    txtError.setVisibility(View.VISIBLE);
                else
                    txtError.setVisibility(View.GONE);
            }
        });
    }

    public void onClickRegister(View v) {

    }



}

