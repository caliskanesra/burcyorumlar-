package com.example.burc_yorumlari;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // --- 1. BENİ HATIRLA KONTROLÜ (HER ŞEYDEN ÖNCE) ---
        SharedPreferences pref = getSharedPreferences("KullaniciVerileri", MODE_PRIVATE);
        boolean isLogged = pref.getBoolean("girisYapildi", false);

        if (isLogged) {
            String kaydedilenIsim = pref.getString("finalIsim", "Kullanıcı");
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("finalIsim", kaydedilenIsim);
            startActivity(intent);
            finish(); // Bu sayfa kapansın
            super.onCreate(savedInstanceState); // Sistem için gerekli
            return; // Kodun geri kalanını okuma, direkt zıpla!
        }
        // --------------------------------------------------

        // --- 2. NORMAL KAYIT AKIŞI ---
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Bileşenleri Tanımlıyoruz
        EditText etName = findViewById(R.id.etRegName);
        Button btnRegister = findViewById(R.id.btnRegister);
        TextView tvGoToLogin = findViewById(R.id.tvGoToLogin);

        // Kayıt Ol Butonu Mantığı
        btnRegister.setOnClickListener(v -> {
            String userName = etName.getText().toString().trim();

            if (!userName.isEmpty()) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.putExtra("kayitliIsim", userName);
                startActivity(intent);
                Toast.makeText(this, "Kayıt Başarılı! Giriş yapabilirsiniz.", Toast.LENGTH_SHORT).show();
            } else {
                etName.setError("Lütfen isminizi girin");
            }
        });

        // Giriş Yap Yazısı Mantığı
        tvGoToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}