import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


public class Main {
    static AtomicInteger[] counter3;
    static AtomicInteger[] counter4;
    static AtomicInteger[] counter5;
    static int WORDCOUNT = 100_000;

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[WORDCOUNT];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

//        String[] texts = new String[]{"aaa"};

        counter3 = new AtomicInteger[WORDCOUNT];
        counter4 = new AtomicInteger[WORDCOUNT];
        counter5 = new AtomicInteger[WORDCOUNT];
        for (int i = 0; i < WORDCOUNT; i++) {
            counter3[i] = new AtomicInteger(0);
            counter4[i] = new AtomicInteger(0);
            counter5[i] = new AtomicInteger(0);
        }

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                if (isPalindrome(texts[i])) {
                    addCounter(i, texts[i]);
                }
            }
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                if (isOneChar(texts[i])) {
                    addCounter(i, texts[i]);
                }
            }
        });
        thread2.start();

        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                if (isSorted(texts[i])) {
                    addCounter(i, texts[i]);
                }
            }
        });
        thread3.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        long s3 = Arrays.stream(counter3).filter(x -> x.get() != 0).count();
        long s4 = Arrays.stream(counter4).filter(x -> x.get() != 0).count();
        long s5 = Arrays.stream(counter5).filter(x -> x.get() != 0).count();

        System.out.printf("Красивых слов с длиной 3: %d шт\n", s3);
        System.out.printf("Красивых слов с длиной 4: %d шт\n", s4);
        System.out.printf("Красивых слов с длиной 5: %d шт\n", s5);

    }

    private static boolean isSorted(String text) {
        char[] chars = text.toCharArray();
        Arrays.sort(chars);
        String strRes = new String(chars);
        return strRes.equals(text);
    }

    private static boolean isOneChar(String text) {
        char ch = text.charAt(0);
        return text.chars().allMatch(x -> x == ch);
    }

    public static boolean isPalindrome(String text) {
        String textReverse = new StringBuilder(text).reverse().toString();
        return text.equals(textReverse);
    }

    public static void addCounter(int position, String text) {
        int lengthText = text.length();
        if (lengthText == 3) counter3[position].set(1);
        if (lengthText == 4) counter4[position].set(1);
        if (lengthText == 5) counter5[position].set(1);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}

