package uz.iqbolshoh.messenger;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvResponse = findViewById(R.id.tv_response);

        CheckLoginActivity checkLogin = new CheckLoginActivity(this, tvResponse);
        checkLogin.checkLogin();
    }
}
