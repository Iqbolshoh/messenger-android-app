package uz.iqbolshoh.messenger;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

public class CheckLoginActivity {

    private final Context context;
    private final TextView tvResponse;

    public CheckLoginActivity(Context context, TextView tvResponse) {
        this.context = context;
        this.tvResponse = tvResponse;
    }

    public void checkLogin() {
        tvResponse.setText(R.string.checking_login_status);

        new Thread(() -> {
            try {
                String responseBody = ApiConfig.getApiResponse("auth/check_login.php");

                if (responseBody.contains("\"loggedin\":false")) {
                    ((MainActivity) context).runOnUiThread(() -> {
                        tvResponse.setText(R.string.logged_out_message);
                        startLoginActivity();
                    });
                }
            } catch (Exception e) {
                ((MainActivity) context).runOnUiThread(() -> {
                    tvResponse.setText(R.string.failed_to_connect);
                    startLoginActivity();
                });
            }
        }).start();
    }

    private void startLoginActivity() {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        if (context instanceof MainActivity) {
            ((MainActivity) context).finish();
        }
    }
}