package com.reflextest.game;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

/**
 * MainActivity.java — Emoji Tahmin Oyunu
 * ========================================
 * Emojilerden kelime tahmin etme oyunu.
 *
 * Kullanılan bileşenler:
 *   - TextView   : Başlık, soru, skor, kelime çizgileri, geri bildirim
 *   - Button     : Cevapla, sonraki, geri dön, yeniden oyna
 *   - ImageView  : Durum göstergesi (doğru/yanlış renkli daire)
 *   - EditText   : Cevap girişi (PlainText)
 *   - CheckBox   : İlk harfleri göster
 *   - Switch     : Zor mod
 *   - Spinner    : Kategori seçimi
 *   - Array      : Soru/cevap verileri 2D dizilerde tutulur
 */
public class MainActivity extends AppCompatActivity {

    // ==================== UI BİLEŞENLERİ ====================

    // TextView bileşenleri
    private TextView tvTitle;           // Oyun başlığı
    private TextView tvSubtitle;        // Alt başlık
    private TextView tvScore;           // Skor göstergesi
    private TextView tvStreak;          // Seri göstergesi
    private TextView tvQuestionNumber;  // Soru numarası
    private TextView tvQuestion;        // Emoji sorusu
    private TextView tvWordDisplay;     // Kelime çizgileri: _ _ _ _ _
    private TextView tvLetterCount;     // Harf sayısı bilgisi
    private TextView tvHint;            // İpucu metni
    private TextView tvFeedback;        // Doğru/yanlış mesajı

    // ImageView bileşeni
    private ImageView ivStatus;         // Durum göstergesi (renkli daire)

    // EditText bileşeni (PlainText)
    private EditText etAnswer;          // Cevap giriş alanı

    // Button bileşenleri
    private Button btnAnswer;           // Ana buton (Başla / Cevapla)
    private Button btnNext;             // Sonraki soru butonu
    private Button btnBack;             // Geri dön / Ana menü butonu

    // CheckBox bileşeni
    private CheckBox cbHint;            // İlk harfleri göster seçeneği

    // Switch bileşeni
    private Switch switchHardMode;      // Zor mod anahtarı

    // Spinner bileşeni
    private Spinner spinnerCategory;    // Kategori seçimi

    // Konteyner
    private LinearLayout settingsCard;  // Ayarlar kartı

    // ==================== OYUN DEĞİŞKENLERİ ====================
    private int currentQuestion = 0;    // Şu anki soru indeksi
    private int score = 0;              // Doğru cevap sayısı
    private int streak = 0;             // Ardışık doğru sayısı
    private int bestStreak = 0;         // En iyi seri
    private boolean gameStarted = false;// Oyun başladı mı?
    private boolean isHardMode = false; // Zor mod açık mı?
    private int selectedCategory = 0;   // Seçili kategori indeksi
    private Random random = new Random();// Rastgele sayı üreteci
    private int[] questionOrder;        // Soru sırası dizisi (Array)

    // ================================================================
    // ==================== SORU VERİLERİ (ARRAY) ====================
    // ================================================================

    // Kategori isimleri dizisi (Array) — Spinner'da gösterilir
    private String[] categoryNames = {
            "🎬 Film ve Dizi",
            "🌍 Ülkeler",
            "🎭 Meslekler",
            "🐾 Hayvanlar"
    };

    // ---- EMOJİ SORULARI — 2 Boyutlu Dizi (Array) ----
    // allEmojis[kategori][soru] şeklinde erişilir
    private String[][] allEmojis = {
            // ===== Kategori 0: Film ve Dizi =====
            {
                    "🦁👑🌍",           // 0: aslan kral
                    "🧊🚢💔💃",         // 1: titanic
                    "🕷️🕸️🦸",          // 2: örümcek adam
                    "❄️👸⛄",           // 3: karlar ülkesi
                    "🐠🔍🌊",           // 4: kayıp balık nemo
                    "🧙‍♂️💍🌋",          // 5: yüzüklerin efendisi
                    "🏴‍☠️⚓🗺️",          // 6: karayip korsanları
                    "🚗⚡💨",           // 7: hızlı ve öfkeli
                    "👻👻🚫",           // 8: hayalet avcıları
                    "🦖🏝️🔬"            // 9: jurassic park
            },
            // ===== Kategori 1: Ülkeler =====
            {
                    "🗼🥐🍷",           // 0: fransa
                    "🍕🏛️🤌",           // 1: italya
                    "🗽🦅🍔",           // 2: amerika
                    "🗻🌸🍣",           // 3: japonya
                    "🐨🦘🏄",           // 4: avustralya
                    "☕🕌🥙",           // 5: türkiye
                    "🏰🍺🥨",           // 6: almanya
                    "💃🐂🥘",           // 7: ispanya
                    "🧀🌷⚽",           // 8: hollanda
                    "🎭🫖👑"            // 9: ingiltere
            },
            // ===== Kategori 2: Meslekler =====
            {
                    "👨‍⚕️💉🏥",          // 0: doktor
                    "👨‍🍳🍽️🔥",          // 1: aşçı
                    "👩‍🏫📚✏️",          // 2: öğretmen
                    "👨‍🚒🔥🚒",          // 3: itfaiyeci
                    "👮‍♂️🚔⚖️",          // 4: polis
                    "👨‍✈️✈️☁️",          // 5: pilot
                    "👩‍🌾🌾🚜",          // 6: çiftçi
                    "👨‍🎨🎨🖌️",          // 7: ressam
                    "⚽🥅🏟️",           // 8: futbolcu
                    "🎤🎵🎶"            // 9: şarkıcı
            },
            // ===== Kategori 3: Hayvanlar =====
            {
                    "🐆🌳🐾",           // 0: leopar
                    "🦈🌊🦷",           // 1: köpekbalığı
                    "🦉🌙🌳",           // 2: baykuş
                    "🦋🌸🌈",           // 3: kelebek
                    "🐢🐌💚",           // 4: kaplumbağa
                    "🦩💗🏖️",           // 5: flamingo
                    "🐺🌙🏔️",           // 6: kurt
                    "🦅⛰️🌤️",           // 7: kartal
                    "🐧❄️🏔️",           // 8: penguen
                    "🦒🌳🌍"            // 9: zürafa
            }
    };

    // ---- CEVAPLAR — 2 Boyutlu Dizi (Array) ----
    // allAnswers[kategori][soru] — küçük harfle yazılır
    private String[][] allAnswers = {
            // ===== Kategori 0: Film ve Dizi cevapları =====
            {
                    "aslan kral",
                    "titanic",
                    "örümcek adam",
                    "karlar ülkesi",
                    "kayıp balık nemo",
                    "yüzüklerin efendisi",
                    "karayip korsanları",
                    "hızlı ve öfkeli",
                    "hayalet avcıları",
                    "jurassic park"
            },
            // ===== Kategori 1: Ülke cevapları =====
            {
                    "fransa",
                    "italya",
                    "amerika",
                    "japonya",
                    "avustralya",
                    "türkiye",
                    "almanya",
                    "ispanya",
                    "hollanda",
                    "ingiltere"
            },
            // ===== Kategori 2: Meslek cevapları =====
            {
                    "doktor",
                    "aşçı",
                    "öğretmen",
                    "itfaiyeci",
                    "polis",
                    "pilot",
                    "çiftçi",
                    "ressam",
                    "futbolcu",
                    "şarkıcı"
            },
            // ===== Kategori 3: Hayvan cevapları =====
            {
                    "leopar",
                    "köpekbalığı",
                    "baykuş",
                    "kelebek",
                    "kaplumbağa",
                    "flamingo",
                    "kurt",
                    "kartal",
                    "penguen",
                    "zürafa"
            }
    };

    // ================================================================
    // ==================== ACTIVITY YAŞAM DÖNGÜSÜ ====================
    // ================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bileşenleri bul
        initViews();

        // Spinner'ı ayarla
        setupSpinner();

        // Dinleyicileri ayarla
        setupListeners();
    }

    // ================================================================
    // ==================== BİLEŞENLERİ BULMA ====================
    // ================================================================

    private void initViews() {
        // TextView
        tvTitle = findViewById(R.id.tvTitle);
        tvSubtitle = findViewById(R.id.tvSubtitle);
        tvScore = findViewById(R.id.tvScore);
        tvStreak = findViewById(R.id.tvStreak);
        tvQuestionNumber = findViewById(R.id.tvQuestionNumber);
        tvQuestion = findViewById(R.id.tvQuestion);
        tvWordDisplay = findViewById(R.id.tvWordDisplay);
        tvLetterCount = findViewById(R.id.tvLetterCount);
        tvHint = findViewById(R.id.tvHint);
        tvFeedback = findViewById(R.id.tvFeedback);

        // ImageView
        ivStatus = findViewById(R.id.ivStatus);

        // EditText (PlainText)
        etAnswer = findViewById(R.id.etAnswer);

        // Button
        btnAnswer = findViewById(R.id.btnAnswer);
        btnNext = findViewById(R.id.btnNext);
        btnBack = findViewById(R.id.btnBack);

        // CheckBox
        cbHint = findViewById(R.id.cbHint);

        // Switch
        switchHardMode = findViewById(R.id.switchHardMode);

        // Spinner
        spinnerCategory = findViewById(R.id.spinnerCategory);

        // Konteyner
        settingsCard = findViewById(R.id.settingsCard);
    }

    // ================================================================
    // ==================== SPINNER AYARI ====================
    // ================================================================

    private void setupSpinner() {
        // ArrayAdapter: String dizisini (Array) Spinner'a bağlar
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                categoryNames
        );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
    }

    // ================================================================
    // ==================== DİNLEYİCİLER ====================
    // ================================================================

    private void setupListeners() {

        // BUTTON: Ana buton — Başla veya Cevapla
        btnAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!gameStarted) {
                    startGame();
                } else {
                    checkAnswer();
                }
            }
        });

        // BUTTON: Sonraki soru
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });

        // BUTTON: Geri dön — oyundan çıkış
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToMenu();
            }
        });

        // SWITCH: Zor mod
        switchHardMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isHardMode = isChecked;
                if (isChecked) {
                    Toast.makeText(MainActivity.this,
                            getString(R.string.toast_hard_on), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,
                            getString(R.string.toast_hard_off), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // SPINNER: Kategori seçimi
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCategory = 0;
            }
        });

        // CHECKBOX: İpucu — ilk harfleri göster/gizle
        cbHint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (gameStarted) {
                    updateWordDisplay();
                }
            }
        });

        // EDITTEXT: Enter (Done) tuşuna basınca cevabı kontrol et
        etAnswer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Klavyede Done/Enter tuşuna basıldığında
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (gameStarted && btnAnswer.getVisibility() == View.VISIBLE) {
                        checkAnswer();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    // ================================================================
    // ==================== OYUN MANTIGI ====================
    // ================================================================

    /**
     * Yeni bir oyun başlatır.
     */
    private void startGame() {
        gameStarted = true;
        currentQuestion = 0;
        score = 0;
        streak = 0;
        bestStreak = 0;

        // --- ARRAY: Soru sırasını oluştur ve karıştır ---
        int totalQ = allEmojis[selectedCategory].length;
        questionOrder = new int[totalQ];
        for (int i = 0; i < totalQ; i++) {
            questionOrder[i] = i;
        }
        // Fisher-Yates karıştırma algoritması
        for (int i = totalQ - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = questionOrder[i];
            questionOrder[i] = questionOrder[j];
            questionOrder[j] = temp;
        }

        // UI güncelle
        settingsCard.setVisibility(View.GONE);
        btnBack.setVisibility(View.VISIBLE);
        btnAnswer.setText(getString(R.string.btn_answer));
        updateScore();

        Toast.makeText(this, getString(R.string.toast_game_start), Toast.LENGTH_SHORT).show();

        showQuestion();
    }

    /**
     * Mevcut soruyu gösterir.
     */
    private void showQuestion() {
        // ARRAY: Soru indeksini diziden al
        int qIndex = questionOrder[currentQuestion];
        String emoji = allEmojis[selectedCategory][qIndex];

        // Soru numarası
        tvQuestionNumber.setText("Soru " + (currentQuestion + 1) + " / " + questionOrder.length);
        tvQuestionNumber.setVisibility(View.VISIBLE);

        // Emoji sorusunu göster
        tvQuestion.setText(emoji);
        tvQuestion.setTextSize(52);

        // Kelime çizgilerini göster: _ _ _ _ _
        updateWordDisplay();
        tvWordDisplay.setVisibility(View.VISIBLE);

        // Harf sayısını göster
        String answer = allAnswers[selectedCategory][qIndex];
        int letterCount = answer.replace(" ", "").length();
        int wordCount = answer.split(" ").length;
        if (wordCount > 1) {
            tvLetterCount.setText("(" + wordCount + " kelime, " + letterCount + " harf)");
        } else {
            tvLetterCount.setText("(" + letterCount + " harf)");
        }
        tvLetterCount.setVisibility(View.VISIBLE);

        // EditText: Temizle ve etkinleştir
        etAnswer.setText("");
        etAnswer.setVisibility(View.VISIBLE);
        etAnswer.setEnabled(true);
        etAnswer.requestFocus();

        // Butonlar
        btnAnswer.setVisibility(View.VISIBLE);
        btnAnswer.setText(getString(R.string.btn_answer));
        btnNext.setVisibility(View.GONE);

        // Geri bildirim gizle
        tvFeedback.setVisibility(View.GONE);
        tvHint.setVisibility(View.GONE);

        // ImageView: Nötr (mavi) daire
        ivStatus.setImageResource(R.drawable.ic_status_neutral);
    }

    /**
     * Kelime çizgilerini oluşturur ve günceller.
     * Normal: _ _ _ _ _   _ _ _ _
     * İpucu açık: A _ _ _ _   K _ _ _
     * Zor mod + ipucu: sadece harf sayısı
     */
    private void updateWordDisplay() {
        if (!gameStarted || questionOrder == null) return;

        int qIndex = questionOrder[currentQuestion];
        String answer = allAnswers[selectedCategory][qIndex];
        boolean showFirstLetters = cbHint.isChecked();

        // Zor modda ipucu kısıtlı
        if (isHardMode && showFirstLetters) {
            // Zor mod + CheckBox: sadece kelime sayısını ipucu ver
            tvWordDisplay.setText(generateWordLines(answer, false));
            tvHint.setText("💡 İpucu: " + answer.split(" ").length + " kelimeden oluşuyor");
            tvHint.setVisibility(View.VISIBLE);
        } else if (showFirstLetters) {
            // Normal mod + CheckBox: ilk harfleri göster
            tvWordDisplay.setText(generateWordLines(answer, true));
            tvHint.setVisibility(View.GONE);
        } else {
            // İpucu kapalı: sadece çizgiler
            tvWordDisplay.setText(generateWordLines(answer, false));
            tvHint.setVisibility(View.GONE);
        }
    }

    /**
     * Kelime çizgilerini oluşturur.
     * Örnek: "aslan kral" → "_ _ _ _ _   _ _ _ _"
     * İpucu ile: "aslan kral" → "A _ _ _ _   K _ _ _"
     *
     * @param answer         Doğru cevap metni
     * @param showFirstLetter İlk harfleri göster mi?
     * @return Çizgi formatında metin
     */
    private String generateWordLines(String answer, boolean showFirstLetter) {
        // StringBuilder ile çizgi metnini oluştur
        StringBuilder display = new StringBuilder();
        boolean isFirstOfWord = true;

        // Cevabın her karakterini dolaş
        for (int i = 0; i < answer.length(); i++) {
            char c = answer.charAt(i);

            if (c == ' ') {
                // Kelimeler arasında boşluk
                display.append("   ");
                isFirstOfWord = true;
            } else {
                // Harfler arasında boşluk ekle (ilk harf hariç)
                if (!isFirstOfWord || (i > 0 && answer.charAt(i - 1) != ' ')) {
                    if (i > 0 && answer.charAt(i - 1) != ' ') {
                        display.append(" ");
                    }
                }

                if (isFirstOfWord && showFirstLetter) {
                    // İpucu: ilk harfi büyük göster
                    display.append(Character.toUpperCase(c));
                    isFirstOfWord = false;
                } else {
                    // Çizgi göster
                    display.append("_");
                    isFirstOfWord = false;
                }
            }
        }

        return display.toString();
    }

    /**
     * Cevabı kontrol eder.
     */
    private void checkAnswer() {
        // EditText'ten cevabı al
        String userAnswer = etAnswer.getText().toString().trim();

        if (userAnswer.isEmpty()) {
            Toast.makeText(this, getString(R.string.toast_empty), Toast.LENGTH_SHORT).show();
            return;
        }

        // Normalize et (Türkçe karakter desteği)
        userAnswer = normalizeText(userAnswer);

        // ARRAY: Doğru cevabı diziden al
        int qIndex = questionOrder[currentQuestion];
        String correctAnswer = allAnswers[selectedCategory][qIndex];
        String normalizedCorrect = normalizeText(correctAnswer);

        if (userAnswer.equals(normalizedCorrect)) {
            // ===== DOĞRU CEVAP =====
            score++;
            streak++;
            if (streak > bestStreak) {
                bestStreak = streak;
            }

            // Çizgilerde doğru cevabı göster
            tvWordDisplay.setText(correctAnswer.toUpperCase());
            tvWordDisplay.setTextColor(Color.parseColor("#4CAF50"));

            // Geri bildirim
            tvFeedback.setText("✅ Doğru! Harika! 🎉");
            tvFeedback.setTextColor(Color.parseColor("#4CAF50"));
            tvFeedback.setVisibility(View.VISIBLE);

            // ImageView: Yeşil daire
            ivStatus.setImageResource(R.drawable.ic_status_correct);

            Toast.makeText(this, getString(R.string.toast_correct), Toast.LENGTH_SHORT).show();

        } else {
            // ===== YANLIŞ CEVAP =====
            streak = 0;

            // Çizgilerde doğru cevabı göster
            tvWordDisplay.setText(correctAnswer.toUpperCase());
            tvWordDisplay.setTextColor(Color.parseColor("#F44336"));

            // Geri bildirim
            tvFeedback.setText("❌ Yanlış!\nDoğru cevap: " + correctAnswer);
            tvFeedback.setTextColor(Color.parseColor("#F44336"));
            tvFeedback.setVisibility(View.VISIBLE);

            // ImageView: Kırmızı daire
            ivStatus.setImageResource(R.drawable.ic_status_wrong);

            Toast.makeText(this, getString(R.string.toast_wrong), Toast.LENGTH_SHORT).show();
        }

        // Skoru güncelle
        updateScore();

        // EditText devre dışı
        etAnswer.setEnabled(false);

        // Butonlar
        btnAnswer.setVisibility(View.GONE);
        if (currentQuestion >= questionOrder.length - 1) {
            btnNext.setText(getString(R.string.btn_results));
        } else {
            btnNext.setText(getString(R.string.btn_next));
        }
        btnNext.setVisibility(View.VISIBLE);
    }

    /**
     * Sonraki soruya geçer.
     */
    private void nextQuestion() {
        // Çizgi rengini beyaza sıfırla
        tvWordDisplay.setTextColor(Color.parseColor("#FFFFFF"));

        currentQuestion++;
        if (currentQuestion >= questionOrder.length) {
            showGameOver();
        } else {
            showQuestion();
        }
    }

    /**
     * Oyun bitişini gösterir — final skor ekranı.
     */
    private void showGameOver() {
        gameStarted = false;
        int total = questionOrder.length;

        // Performans mesajı
        String message;
        if (score == total) {
            message = "🏆 MÜKEMMEL!\nTam puan aldın!";
            ivStatus.setImageResource(R.drawable.ic_status_star);
        } else if (score >= total * 0.7) {
            message = "🌟 HARİKA!\nÇok iyi gidiyorsun!";
            ivStatus.setImageResource(R.drawable.ic_status_correct);
        } else if (score >= total * 0.4) {
            message = "👍 İYİ!\nFena değil!";
            ivStatus.setImageResource(R.drawable.ic_status_neutral);
        } else {
            message = "💪 Pratik yap!\nGelişeceksin!";
            ivStatus.setImageResource(R.drawable.ic_status_wrong);
        }

        // UI güncelle
        tvQuestionNumber.setText("Oyun Bitti!");
        tvQuestion.setText(message);
        tvQuestion.setTextSize(22);
        tvWordDisplay.setVisibility(View.GONE);
        tvLetterCount.setVisibility(View.GONE);
        tvHint.setVisibility(View.GONE);

        // Skor detayları
        tvFeedback.setText("✅ Doğru: " + score + " / " + total
                + "\n🔥 En iyi seri: " + bestStreak);
        tvFeedback.setTextColor(Color.parseColor("#FFAB40"));
        tvFeedback.setVisibility(View.VISIBLE);

        // EditText gizle
        etAnswer.setVisibility(View.GONE);

        // Butonlar
        btnAnswer.setVisibility(View.VISIBLE);
        btnAnswer.setText(getString(R.string.btn_restart));
        btnNext.setVisibility(View.GONE);
        btnBack.setVisibility(View.GONE);

        // Ayarlar kartını göster
        settingsCard.setVisibility(View.VISIBLE);

        Toast.makeText(this, getString(R.string.toast_game_over), Toast.LENGTH_SHORT).show();
    }

    /**
     * Ana menüye döner — oyun sırasında geri çıkış.
     */
    private void returnToMenu() {
        gameStarted = false;

        // Tüm oyun alanlarını sıfırla
        tvQuestionNumber.setText(getString(R.string.label_ready_text));
        tvQuestion.setText(getString(R.string.label_ready));
        tvQuestion.setTextSize(52);
        tvWordDisplay.setVisibility(View.GONE);
        tvWordDisplay.setTextColor(Color.parseColor("#FFFFFF"));
        tvLetterCount.setVisibility(View.GONE);
        tvHint.setVisibility(View.GONE);
        tvFeedback.setVisibility(View.GONE);
        etAnswer.setVisibility(View.GONE);
        btnNext.setVisibility(View.GONE);
        btnBack.setVisibility(View.GONE);

        // Skor sıfırla
        tvScore.setText(getString(R.string.label_score));
        tvStreak.setText(getString(R.string.label_streak));

        // ImageView: Nötr
        ivStatus.setImageResource(R.drawable.ic_status_neutral);

        // Ana buton
        btnAnswer.setVisibility(View.VISIBLE);
        btnAnswer.setText(getString(R.string.btn_start));

        // Ayarlar kartını göster
        settingsCard.setVisibility(View.VISIBLE);

        Toast.makeText(this, getString(R.string.toast_back), Toast.LENGTH_SHORT).show();
    }

    // ================================================================
    // ==================== YARDIMCI METOTLAR ====================
    // ================================================================

    /**
     * Skor göstergelerini günceller.
     */
    private void updateScore() {
        int total = 0;
        if (questionOrder != null) {
            total = questionOrder.length;
        }
        tvScore.setText("Skor: " + score + " / " + total);
        tvStreak.setText("Seri: " + streak + " 🔥");
    }

    /**
     * Türkçe karakterleri normalize eder.
     * "Örümcek" ve "orumcek" aynı kabul edilir.
     */
    private String normalizeText(String text) {
        // Önce Türkçe büyük harfleri küçüğe çevir
        String result = text;
        result = result.replace("İ", "i");
        result = result.replace("I", "i");
        result = result.replace("Ö", "ö");
        result = result.replace("Ü", "ü");
        result = result.replace("Ş", "ş");
        result = result.replace("Ç", "ç");
        result = result.replace("Ğ", "ğ");

        // Küçük harfe çevir
        result = result.toLowerCase();

        // Türkçe karakterleri ASCII karşılıklarına dönüştür
        result = result.replace("ı", "i");
        result = result.replace("ö", "o");
        result = result.replace("ü", "u");
        result = result.replace("ş", "s");
        result = result.replace("ç", "c");
        result = result.replace("ğ", "g");

        return result;
    }
}
