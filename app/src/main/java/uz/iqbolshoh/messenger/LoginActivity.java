package uz.iqbolshoh.messenger;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        Button btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(v -> handleLogin());
    }

    private void handleLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, R.string.empty_fields_error, Toast.LENGTH_SHORT).show();
            return;
        }

        // Hash the password
        String hashedPassword = hashPassword(password);

        new Thread(() -> {
            try {
                JSONObject jsonData = new JSONObject();
                jsonData.put("username", username);
                jsonData.put("password", hashedPassword);

                String response = ApiConfig.postApiResponse("auth/login.php", jsonData.toString());

                JSONObject jsonResponse = new JSONObject(response);
                String status = jsonResponse.getString("status");

                if ("success".equals(status)) {
                    JSONObject userData = jsonResponse.getJSONObject("data");
                    String fullName = userData.getString("full_name");

                    runOnUiThread(() -> {
                        Toast.makeText(this, getString(R.string.login_success, fullName), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, MainActivity.class));
                        finish();
                    });
                } else {
                    String message = jsonResponse.getString("message");
                    runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                Log.e("LOGIN_ERROR", "Error: " + e.getMessage(), e);
                runOnUiThread(() -> Toast.makeText(this, R.string.server_error, Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private String hashPassword(String password) {
        try {
            String secret = "iqbolshoh";
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hashBytes = mac.doFinal(password.getBytes());
            StringBuilder hashString = new StringBuilder();
            for (byte b : hashBytes) {
                hashString.append(String.format("%02x", b));
            }
            return hashString.toString();
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}