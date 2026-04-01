# 🎯 Emoji Tahmin Oyunu

Emojilerden kelime tahmin etme oyunu! Android platformu için Java ve XML ile geliştirilmiş eğlenceli bir mobil uygulama.
---

## 📖 Proje Hakkında

Bu proje, **Mobil Programlama** dersi kapsamında geliştirilmiş bir Android uygulamasıdır. Oyunun amacı, ekranda gösterilen emoji kombinasyonlarından doğru kelimeyi tahmin etmektir.

### 🎮 Nasıl Oynanır?

1. Ana menüden bir **kategori** seç (Film, Ülke, Meslek, Hayvan)
2. **OYUNU BAŞLAT** butonuna bas
3. Ekrandaki emojilere ve alt kısımdaki kelime çizgilerine bak
4. Tahmini **EditText**'e yaz ve **CEVAPLA** butonuna bas (veya Enter'a bas)
5. Doğru mu yanlış mı öğren, sonraki soruya geç!
6. 10 soru sonunda final skorunu gör 🏆

---

## ✨ Özellikler

| Özellik | Açıklama |
|---------|----------|
| 🎬 **4 Kategori** | Film & Dizi, Ülkeler, Meslekler, Hayvanlar |
| 📝 **40 Soru** | Her kategoride 10 benzersiz emoji sorusu |
| 📊 **Kelime Çizgileri** | `_ _ _ _ _   _ _ _ _` formatında kelime uzunluğu gösterimi |
| 💡 **İpucu Sistemi** | CheckBox ile ilk harfler açılır: `A _ _ _ _   K _ _ _` |
| 🔥 **Zor Mod** | Switch ile ipucu kısıtlanır |
| 🔀 **Karışık Sıra** | Her oyunda sorular rastgele sırada gelir |
| 🏆 **Performans Değerlendirme** | Skora göre final mesajı (Mükemmel / Harika / İyi / Pratik Yap) |
| 🔤 **Türkçe Karakter Desteği** | `ö, ü, ş, ç, ğ, ı` yazmasanız da cevap kabul edilir |
| ⌨️ **Enter Tuşu Desteği** | Klavyede Enter/Done ile de cevap gönderilebilir |
| ← **Geri Dönüş** | Oyun sırasında ana menüye dönme butonu |
| 🔥 **Seri Takibi** | Ardışık doğru cevap serisi gösterilir |

---

## 🧩 Kullanılan Android Bileşenleri

| Bileşen | Kullanım Amacı |
|---------|----------------|
| `TextView` | Başlık, soru, skor, kelime çizgileri, geri bildirim mesajları |
| `Button` | Başla, Cevapla, Sonraki Soru, Geri Dön, Yeniden Oyna |
| `ImageView` | Durum göstergesi (yeşil/kırmızı/mavi/altın renkli daire) |
| `EditText` | Kullanıcı cevap girişi (PlainText) |
| `CheckBox` | İlk harfleri göster/gizle seçeneği |
| `Switch` | Zor Mod açma/kapama |
| `Spinner` | Kategori seçimi (dropdown liste) |
| `Array` | 2D dizilerde soru ve cevap verileri |

---

## 📁 Proje Yapısı

```
ReflexTestGame/
├── app/
│   ├── build.gradle
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/reflextest/game/
│       │   └── MainActivity.java          # Tüm oyun mantığı
│       └── res/
│           ├── anim/
│           │   ├── bounce_in.xml          # Zıplama animasyonu
│           │   ├── fade_in_up.xml         # Yukarı kayma animasyonu
│           │   └── pulse.xml              # Nabız animasyonu
│           ├── drawable/
│           │   ├── btn_rounded_green.xml   # Yeşil yuvarlak buton
│           │   ├── btn_rounded_red.xml     # Kırmızı yuvarlak buton
│           │   ├── card_background.xml     # Cam efektli kart arkaplanı
│           │   ├── circle_emoji_bg.xml     # Dairesel emoji arkaplanı
│           │   ├── ic_status_correct.xml   # ✅ Yeşil daire (doğru)
│           │   ├── ic_status_neutral.xml   # 🔵 Mavi daire (nötr)
│           │   ├── ic_status_star.xml      # ⭐ Altın daire (şampiyon)
│           │   └── ic_status_wrong.xml     # 🔴 Kırmızı daire (yanlış)
│           ├── layout/
│           │   ├── activity_main.xml       # Ana ekran layout'u
│           │   ├── spinner_item.xml        # Spinner öğe görünümü
│           │   └── spinner_dropdown_item.xml
│           └── values/
│               ├── colors.xml              # Renk paleti
│               ├── strings.xml             # Tüm metin kaynakları
│               └── themes.xml              # Uygulama teması
├── build.gradle                            # Proje Gradle ayarları
├── settings.gradle                         # Gradle bağımlılıkları
├── gradle.properties                       # Gradle özellikleri
└── README.md                               # Bu dosya
```

---

## 🎬 Kategori Örnekleri

### Film & Dizi
| Emoji | Cevap |
|-------|-------|
| 🦁👑🌍 | Aslan Kral |
| 🧊🚢💔 | Titanic |
| 🕷️🕸️🦸 | Örümcek Adam |
| ❄️👸⛄ | Karlar Ülkesi |
| 🏴‍☠️⚓🗺️ | Karayip Korsanları |

### 🌍 Ülkeler
| Emoji | Cevap |
|-------|-------|
| 🗼🥐🍷 | Fransa |
| 🍕🏛️🤌 | İtalya |
| ☕🕌🥙 | Türkiye |
| 🗻🌸🍣 | Japonya |
| 🐨🦘🏄 | Avustralya |

### 🎭 Meslekler
| Emoji | Cevap |
|-------|-------|
| 👨‍⚕️💉🏥 | Doktor |
| 👩‍🏫📚✏️ | Öğretmen |
| 👨‍✈️✈️☁️ | Pilot |
| ⚽🥅🏟️ | Futbolcu |
| 🎤🎵🎶 | Şarkıcı |

### 🐾 Hayvanlar
| Emoji | Cevap |
|-------|-------|
| 🦈🌊🦷 | Köpekbalığı |
| 🦉🌙🌳 | Baykuş |
| 🦩💗🏖️ | Flamingo |
| 🐢🐌💚 | Kaplumbağa |
| 🦅⛰️🌤️ | Kartal |

---

## 🛠️ Kurulum

### Gereksinimler
- **Android Studio** (Arctic Fox veya üzeri)
- **Java 17**
- **Android SDK 35**
- **Min SDK 24** (Android 7.0+)

### Adımlar

1. Repoyu klonlayın:
   ```bash
   git clone https://github.com/aycacerence/Emoji_Tahmin_Oyunu.git
   ```

2. Android Studio'da projeyi açın:
   ```
   File → Open → ReflexTestGame klasörünü seçin
   ```

3. Gradle sync tamamlanana kadar bekleyin

4. Bir emülatör veya fiziksel cihaz seçin

5. ▶️ **Run** butonuna basın

---

## 🎯 Oyun Mantığı (Kısa Özet)

```java
// 1. Sorular 2D dizilerde (Array) saklanır
String[][] allEmojis = { {"🦁👑🌍", "🧊🚢💔", ...}, ... };
String[][] allAnswers = { {"aslan kral", "titanic", ...}, ... };

// 2. Fisher-Yates algoritması ile sorular karıştırılır
for (int i = totalQ - 1; i > 0; i--) {
    int j = random.nextInt(i + 1);
    // swap questionOrder[i] ve questionOrder[j]
}

// 3. Türkçe karakter normalizasyonu ile cevap karşılaştırılır
// "karlar ulkesi" == "karlar ülkesi" → her ikisi de kabul edilir
private String normalizeText(String text) {
    result = result.replace("ö", "o").replace("ü", "u")...
}

// 4. Kelime çizgileri oluşturulur
// "aslan kral" → "_ _ _ _ _   _ _ _ _"
// İpucu ile → "A _ _ _ _   K _ _ _"
```

---


---

## 📝 Lisans

Bu proje eğitim amaçlı geliştirilmiştir. Serbestçe kullanabilir ve değiştirebilirsiniz.

---

## 👨‍💻 Geliştirici

**Şevval Ayça Çerence**

---

<p align="center">
  <b>⭐ Projeyi beğendiyseniz yıldız vermeyi unutmayın!</b>
</p>
