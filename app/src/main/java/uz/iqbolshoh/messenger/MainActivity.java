package uz.iqbolshoh.messenger;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String URL = "https://messenger.iqbolshoh.uz/api/auth/check_login.php";
    private TextView tvResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResponse = findViewById(R.id.tv_response);

        // Ekran ochilishi bilan login holatini tekshirish
        checkLogin();
    }

    private void checkLogin() {
        tvResponse.setText("Checking login status...");

        // Asinxron so'rov
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder().url(URL).build();
                    Response response = client.newCall(request).execute();

                    if (response.isSuccessful() && response.body() != null) {
                        final String responseBody = response.body().string();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvResponse.setText(responseBody);
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvResponse.setText("Failed to fetch login status.");
                            }
                        });
                        Log.e("API_ERROR", "Response Code: " + response.code());
                    }
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