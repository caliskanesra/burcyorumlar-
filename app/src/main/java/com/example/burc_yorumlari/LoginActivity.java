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

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Bileşenleri Tanımlıyoruz
        EditText etEmail = findViewById(R.id.etLoginEmail);
        EditText etPassword = findViewById(R.id.etLoginPassword);
        Button btnLogin = findViewById(R.id.btnLogin);
        TextView tvGoToRegister = findViewById(R.id.tvGoToRegister);

        // 2. Register sayfasından gelen ismi teslim alıyoruz
        String gelenIsim = getIntent().getStringExtra("kayitliIsim");

        // 3. Giriş Yap Butonu Mantığı
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (!email.isEmpty() && !password.isEmpty()) {

                // --- BENİ HATIRLA: GİRİŞ BİLGİLERİNİ GARANTİYE AL ---
                SharedPreferences pref = getSharedPreferences("KullaniciVerileri", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                // editor.apply() yerine editor.commit() kullanarak anında yazıyoruz
                editor.putBoolean("girisYapildi", true);

                String finalIsim = (gelenIsim != null) ? gelenIsim : "Kullanıcı";
                editor.putString("finalIsim", finalIsim);

                // commit() işlemi true/false döner ve veriyi hemen diske işler
                editor.commit();
                // ----------------------------------------------------

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("finalIsim", finalIsim);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show();
            }
        });

        // 4. Hesabım Yok Yazısı
        tvGoToRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}