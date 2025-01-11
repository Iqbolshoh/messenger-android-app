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
        tvResponse.setText("Checking login status...");

        // Asinxron so'rov
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String responseBody = ApiConfig.getApiResponse("auth/check_login.php");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvResponse.setText(responseBody);
                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvResponse.setText("Failed to connect to the server.");
                        }
                    });
                    Log.e("API_ERROR", "Error: " + e.getMessage());
                }
            }
        }).start();
    }
}