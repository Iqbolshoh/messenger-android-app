package uz.iqbolshoh.messenger;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResponse = findViewById(R.id.tv_response);

        checkLogin();
    }

    private void checkLogin() {
        tvResponse.setText(getString(R.string.checking_login_status));

        new Thread(() -> {
            try {
                String responseBody = ApiConfig.getApiResponse("auth/check_login.php");

                runOnUiThread(() -> tvResponse.setText(responseBody));
            } catch (Exception e) {
                runOnUiThread(() -> tvResponse.setText(getString(R.string.failed_to_connect)));
                Log.e("API_ERROR", "Error: " + e.getMessage());
            }
        }).start();
    }
}
