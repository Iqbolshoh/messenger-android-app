package uz.iqbolshoh.messenger;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import androidx.annotation.StringRes;

public class CheckLoginActivity {

    private final Context context;
    private final TextView tvResponse;

    public CheckLoginActivity(Context context, TextView tvResponse) {
        this.context = context;
        this.tvResponse = tvResponse;
    }

    public void checkLogin() {
        setTextViewText(R.string.checking_login_status);

        new Thread(() -> {
            try {
                String responseBody = ApiConfig.getApiResponse("auth/check_login.php");

                if (responseBody.contains("\"loggedIn\":false")) { // Typo 'loggedin' corrected to 'loggedIn'
                    runOnUiThread(() -> {
                        setTextViewText(R.string.logged_out_message);
                        startLoginActivity();
                    });
                } else {
                    runOnUiThread(() -> setTextViewText(R.string.logged_in_message));
                }
            } catch (Exception e) {
                runOnUiThread(() -> {
                    setTextViewText(R.string.failed_to_connect);
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

    private void runOnUiThread(Runnable action) {
        if (context instanceof MainActivity) {
            ((MainActivity) context).runOnUiThread(action);
        }
    }

    private void setTextViewText(@StringRes int stringResId) {
        tvResponse.setText(context.getString(stringResId));
    }
}
