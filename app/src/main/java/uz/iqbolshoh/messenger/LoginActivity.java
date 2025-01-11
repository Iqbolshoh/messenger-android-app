package uz.iqbolshoh.messenger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(v -> handleLogin());
    }

    private void handleLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, R.string.empty_fields_error, Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            try {
                // API'ga POST so'rov yuborish
                JSONObject jsonData = new JSONObject();
                jsonData.put("username", username);
                jsonData.put("password", password);

                String response = ApiConfig.postApiResponse("auth/login.php", jsonData.toString());

                JSONObject jsonResponse = new JSONObject(response);
                String status = jsonResponse.getString("status");

                if ("success".equals(status)) {
                    JSONObject userData = jsonResponse.getJSONObject("data");
                    String fullName = userData.getString("full_name");

                    runOnUiThread(() -> {
                        Toast.makeText(this, getString(R.string.login_success, fullName), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainActivity.class)); // Main sahifaga o'tish
                        finish(); // LoginActivity-ni yopish
                    });
                } else {
                    String message = jsonResponse.getString("message");
                    runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                Log.e("LOGIN_ERROR", "Error: " + e.getMessage());
                runOnUiThread(() -> Toast.makeText(this, R.string.server_error, Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
