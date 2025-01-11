package uz.iqbolshoh.messenger;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://messenger.iqbolshoh.uz/api/";

    private EditText etUserId;
    private TextView tvResponse;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI elementlarini bog'lash
        etUserId = findViewById(R.id.et_user_id);
        tvResponse = findViewById(R.id.tv_response);
        Button btnCheckLogin = findViewById(R.id.btn_check_login);

        // Retrofit sozlash
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        // Tugma bosilganda ishlash
        btnCheckLogin.setOnClickListener(v -> {
            String userIdStr = etUserId.getText().toString().trim();
            if (!userIdStr.isEmpty()) {
                int userId = Integer.parseInt(userIdStr);
                checkLogin(userId);
            } else {
                Toast.makeText(MainActivity.this, "Please enter a valid User ID.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Login holatini tekshirish uchun API so'rovi.
     *
     * @param userId Foydalanuvchi ID
     */
    private void checkLogin(int userId) {
        Call<BaseResponse> call = apiService.checkLogin(userId);

        tvResponse.setText("Checking login status...");

        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BaseResponse baseResponse = response.body();
                    tvResponse.setText("Status: " + baseResponse.getStatus() +
                            "\nMessage: " + baseResponse.getMessage() +
                            "\nData: " + baseResponse.getData());
                } else {
                    tvResponse.setText("Failed to fetch login status. Response error.");
                    Log.e("API_ERROR", "Response Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                tvResponse.setText("Failed to connect to the server.");
                Log.e("API_ERROR", "Error: " + t.getMessage());
            }
        });
    }
}
