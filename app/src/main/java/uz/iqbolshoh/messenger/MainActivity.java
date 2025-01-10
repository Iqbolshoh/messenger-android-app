package uz.iqbolshoh.messenger;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // main_layout id'li viewga WindowInsetsListener qo'shish
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_layout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Button va TextView ob'ektlarini olish
        Button sampleButton = findViewById(R.id.sample_button);
        TextView sampleTextView = findViewById(R.id.sample_textview);

        // Buttonga klik listener qo'shish
        sampleButton.setOnClickListener(new View.OnClickListener() {
            private boolean isTextViewVisible = false; // Matnning holatini kuzatish

            @Override
            public void onClick(View v) {
                // Matnni ko'rsatish yoki yashirish
                if (isTextViewVisible) {
                    sampleTextView.setVisibility(View.GONE); // Matnni yashirish
                } else {
                    sampleTextView.setVisibility(View.VISIBLE); // Matnni ko'rsatish
                }
                isTextViewVisible = !isTextViewVisible; // Holatni o'zgartirish
            }
        });
    }
}
