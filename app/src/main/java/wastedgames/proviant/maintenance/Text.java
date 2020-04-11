package wastedgames.proviant.maintenance;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.util.HashMap;
import java.util.Map;

import wastedgames.proviant.enumerations.Font;

public class Text {
    private final HashMap<Font, HashMap<String, Bitmap>> FONT_COLLECTION;

    private final HashMap<String, Integer> ALPHABET;
    private final String[] ALPHABET_ARRAY = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", ".", ",", "1",
            "2", "3", "4", "5", "6", "7", "8", "9", "0", ":"};
    private final int BLOCK_SIZE = 16;
    private final int UNITS_IN_LINE = 8;
    private final int OFFSET = 6;
    private Font currentFont;

    private void fillAlphabet() {
        for (int i = 0; i < ALPHABET_ARRAY.length; i++) {
            ALPHABET.put(ALPHABET_ARRAY[i], i);
        }
    }

    public Text(Font font) {
        FONT_COLLECTION = new HashMap<>();
        ALPHABET = new HashMap<>();
        currentFont = font;
        fillAlphabet();
        switch (font) {
            case BASIC:
                loadBasicFont();
                break;
        }
    }

    private void loadBasicFont() {
        final HashMap<String, Bitmap> BASIC_FONT = new HashMap<>();
        for (Map.Entry<String, Integer> unit : ALPHABET.entrySet()) {
            BASIC_FONT.put(unit.getKey(),
                    Bitmap.createBitmap(ResourcesLoader.BASIC_FONT,
                            (unit.getValue() % UNITS_IN_LINE) * BLOCK_SIZE,
                            (unit.getValue() / UNITS_IN_LINE) * BLOCK_SIZE,
                            BLOCK_SIZE,
                            BLOCK_SIZE));
        }
        FONT_COLLECTION.put(Font.BASIC, BASIC_FONT);
    }

    public void drawText(int x, int y, Canvas canvas, Paint paint, String text) {
        HashMap<String, Bitmap> currentFontMap = FONT_COLLECTION.get(currentFont);
        if (currentFontMap == null) {
            return;
        }
        for (int i = 0; i < text.length(); i++) {
            String unit = String.valueOf(text.charAt(i));
            Bitmap toDraw = currentFontMap.get(unit);
            if (toDraw == null) {
                continue;
            }
            Matrix pos = new Matrix();
            pos.setTranslate(x + i * (BLOCK_SIZE - OFFSET), y);
            canvas.drawBitmap(toDraw, pos, paint);
        }
    }
}
