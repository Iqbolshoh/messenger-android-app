package uz.iqbolshoh.messenger;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResponse = findViewById(R.id.tv_response);
        Button btnCheckStatus = findViewById(R.id.btn_check_status);
        Button btnLoginPage = findViewById(R.id.btn_login_page);
        btnCheckStatus.setOnClickListener(v -> checkLogin());

        btnLoginPage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void checkLogin() {
        tvResponse.setText(getString(R.string.checking_login_status));

        new Thread(() -> {
            try {
                String responseBody = ApiConfig.getApiResponse("auth/check_login.php");

                runOnUiThread(() -> tvResponse.setText(responseBody));
            } catch (Exception e) {
                runOnUiThread(() -> tvResponse.setText(getString(R.string.failed_to_connect)));
            }
        }).start();
    }
}
