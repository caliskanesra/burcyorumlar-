package com.example.burc_yorumlari;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DetailActivity extends AppCompatActivity {

    private String burcAdi = "Genel"; // Varsayılan değer atadık ki null crash olmasın

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView nameTextView = findViewById(R.id.txtDetailName);
        TextView descTextView = findViewById(R.id.txtDetailDescription);
        EditText etUserComment = findViewById(R.id.etUserComment);
        Button btnSubmitComment = findViewById(R.id.btnSubmitComment);
        TextView txtCommentsDisplay = findViewById(R.id.txtCommentsDisplay);

        if (getIntent() != null) {
            String gelenAd = getIntent().getStringExtra("burcAdi");
            if (gelenAd != null) burcAdi = gelenAd; // Eğer intent doluysa ismi güncelle

            String burcYorumu = getIntent().getStringExtra("burcYorumu");
            nameTextView.setText(burcAdi);
            descTextView.setText(burcYorumu);
        }

        // --- HAFIZADAN ESKİ YORUMLARI YÜKLE ---
        SharedPreferences pref = getSharedPreferences("BurcYorumlari", MODE_PRIVATE);
        String eskiYorumlar = pref.getString(burcAdi, "Henüz yorum yapılmamış. İlk yorumu sen yap!");
        txtCommentsDisplay.setText(eskiYorumlar);

        btnSubmitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newComment = etUserComment.getText().toString().trim();

                if (!newComment.isEmpty()) {
                    // Taze veriyi tekrar hafızadan çekiyoruz (en güncel hali için)
                    String currentDisplay = pref.getString(burcAdi, "Henüz yorum yapılmamış. İlk yorumu sen yap!");
                    String guncelMetin;

                    if (currentDisplay.contains("Henüz yorum yapılmamış")) {
                        guncelMetin = "• " + newComment;
                    } else {
                        guncelMetin = "• " + newComment + "\n\n" + currentDisplay;
                    }

                    txtCommentsDisplay.setText(guncelMetin);

                    // --- TELEFONA KAYDET (COMMIT İLE GARANTİYE AL) ---
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(burcAdi, guncelMetin);
                    editor.commit(); // Veriyi anında diske yazar, uygulama kapansa da gitmez!
                    // ------------------------------------------------

                    etUserComment.setText("");
                    Toast.makeText(DetailActivity.this, "Yorumun kaydedildi!", Toast.LENGTH_SHORT).show();
                } else {
                    etUserComment.setError("Lütfen bir yorum yazın!");
                }
            }
        });
    }
}