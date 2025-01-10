package uz.iqbolshoh.messenger;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.HashMap;
import java.util.Map;

public class TestActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://messenger.iqbolshoh.uz/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        // Retrofit obyektini yaratish
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) // Base URL
                .addConverterFactory(GsonConverterFactory.create()) // JSONni Java ob'ektlariga aylantirish
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        // Login qilish uchun credential ma'lumotlari
        Map<String, String> loginCredentials = new HashMap<>();
        loginCredentials.put("username", "your_username");
        loginCredentials.put("password", "your_password");

        // Login so'rovini yuborish
        login(apiService, loginCredentials);

        // Signup qilish uchun ma'lumotlar
        Map<String, String> signupDetails = new HashMap<>();
        signupDetails.put("username", "new_user");
        signupDetails.put("email", "new_user@example.com");
        signupDetails.put("password", "your_password");

        // Signup so'rovini yuborish
        signup(apiService, signupDetails);
    }

    private void login(ApiService apiService, Map<String, String> credentials) {
        Call<BaseResponse> loginCall = apiService.login(credentials);
        loginCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    // Muvaffaqiyatli login
                    BaseResponse baseResponse = response.body();
                    Toast.makeText(TestActivity.this, "Login Success: " + baseResponse.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    // Xato bo'lsa
                    Toast.makeText(TestActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(TestActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signup(ApiService apiService, Map<String, String> userDetails) {
        Call<BaseResponse> signupCall = apiService.signup(userDetails);
        signupCall.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful()) {
                    // Muvaffaqiyatli signup
                    BaseResponse baseResponse = response.body();
                    Toast.makeText(TestActivity.this, "Signup Success: " + baseResponse.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    // Xato bo'lsa
                    Toast.makeText(TestActivity.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(TestActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
