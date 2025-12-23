package com.example.burc_yorumlari;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ZodiacAdapter extends RecyclerView.Adapter<ZodiacAdapter.ZodiacViewHolder> {

    private ArrayList<ZodiacModel> zodiacList;

    public ZodiacAdapter(ArrayList<ZodiacModel> zodiacList) {
        this.zodiacList = zodiacList;
    }

    @NonNull
    @Override
    public ZodiacViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_zodiac, parent, false);
        return new ZodiacViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ZodiacViewHolder holder, int position) {
        ZodiacModel currentZodiac = zodiacList.get(position);
        holder.txtZodiacName.setText(currentZodiac.getZodiacName());

        // --- TIKLAMA ÖZELLİĞİ BURADA BAŞLIYOR ---
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Yeni sayfaya geçiş niyetimizi (Intent) belirtiyoruz
                Intent intent = new Intent(v.getContext(), DetailActivity.class);

                // Burç bilgilerini "anahtar - değer" şeklinde paketliyoruz
                intent.putExtra("burcAdi", currentZodiac.getZodiacName());
                intent.putExtra("burcYorumu", currentZodiac.getZodiacDescription());

                // Ve sayfayı başlatıyoruz
                v.getContext().startActivity(intent);
            }
        });
        // --- TIKLAMA ÖZELLİĞİ BURADA BİTİYOR ---
    }

    @Override
    public int getItemCount() {
        return zodiacList.size();
    }

    public static class ZodiacViewHolder extends RecyclerView.ViewHolder {
        TextView txtZodiacName;

        public ZodiacViewHolder(@NonNull View itemView) {
            super(itemView);
            txtZodiacName = itemView.findViewById(R.id.txtZodiacName);
        }
    }
}