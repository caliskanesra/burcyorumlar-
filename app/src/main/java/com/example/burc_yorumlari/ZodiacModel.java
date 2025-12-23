package com.example.burc_yorumlari;

public class ZodiacModel {
    private String zodiacName;
    private String zodiacDescription;
    private int zodiacImage; // Resimleri hafızada tutmak için ID kullanırız

    // Constructor (Yapıcı Metot): Yeni bir burç eklerken bilgileri buraya gireceğiz
    public ZodiacModel(String zodiacName, String zodiacDescription, int zodiacImage) {
        this.zodiacName = zodiacName;
        this.zodiacDescription = zodiacDescription;
        this.zodiacImage = zodiacImage;
    }

    // Getter Metotları: Bu bilgileri diğer sayfalardan okumamızı sağlar
    public String getZodiacName() {
        return zodiacName;
    }

    public String getZodiacDescription() {
        return zodiacDescription;
    }

    public int getZodiacImage() {
        return zodiacImage;
    }
}