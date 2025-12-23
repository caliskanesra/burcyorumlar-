package com.example.burc_yorumlari;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ZodiacAdapter adapter;
    private ArrayList<ZodiacModel> zodiacList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Sistem çubukları ayarı
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Bileşenleri Tanımla
        TextView tvWelcome = findViewById(R.id.tvWelcomeUser);
        Button btnLogout = findViewById(R.id.btnLogout); // XML'e yeni eklediğimiz buton

        // --- İSİM VE HAFIZA İŞLEMLERİ ---
        SharedPreferences pref = getSharedPreferences("KullaniciVerileri", MODE_PRIVATE);
        String gelenIsim = getIntent().getStringExtra("finalIsim");

        // Eğer Intent boşsa hafızadan oku
        if (gelenIsim == null || gelenIsim.isEmpty()) {
            gelenIsim = pref.getString("finalIsim", "Kullanıcı");
        }
        tvWelcome.setText("Merhaba, " + gelenIsim + "!");

        // --- ÇIKIŞ YAP MANTIĞI ---
        btnLogout.setOnClickListener(v -> {
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("girisYapildi", false); // Giriş durumunu sıfırla
            editor.commit(); // Değişikliği anında diske yaz

            // Kullanıcıyı kayıt sayfasına geri gönder
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish(); // Bu sayfayı kapat
        });
        // -------------------------

        // 2. RecyclerView Kurulumu
        recyclerView = findViewById(R.id.recyclerView);
        zodiacList = new ArrayList<>();

        // Verileri ekle
        zodiacList.add(new ZodiacModel("Koç", "Bugün yerinizde duramayacak kadar enerjik olabilirsiniz.", 0));
        zodiacList.add(new ZodiacModel("Boğa", "Maddi konularda sabırlı olmanız gereken bir gün.", 0));
        zodiacList.add(new ZodiacModel("İkizler", "İletişim trafiğiniz oldukça yoğun geçebilir.", 0));
        zodiacList.add(new ZodiacModel("Yengeç", "Ailenizle vakit geçirmek size iyi gelecek.", 0));
        zodiacList.add(new ZodiacModel("Aslan", "Yaratıcılığınızı konuşturacağınız bir gündesiniz.", 0));
        zodiacList.add(new ZodiacModel("Başak", "Detaylara takılmak yerine büyük resme odaklanın.", 0));
        zodiacList.add(new ZodiacModel("Terazi", "İlişkilerinizde dengeyi bulmak önem kazanıyor.", 0));
        zodiacList.add(new ZodiacModel("Akrep", "Sezgilerinizin sizi yanıltmayacağı bir gün.", 0));
        zodiacList.add(new ZodiacModel("Yay", "Yeni şeyler öğrenme merakınız artıyor.", 0));
        zodiacList.add(new ZodiacModel("Oğlak", "Kariyer hedeflerinize odaklanmak için ideal.", 0));
        zodiacList.add(new ZodiacModel("Kova", "Sosyal çevrenizde dikkatleri üzerinize çekeceksiniz.", 0));
        zodiacList.add(new ZodiacModel("Balık", "Hayal gücünüzün sınırlarını zorlayabilirsiniz.", 0));

        // Adapter ayarları
        adapter = new ZodiacAdapter(zodiacList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}