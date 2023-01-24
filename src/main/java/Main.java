import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static AtomicInteger counter3;
    static AtomicInteger counter4;
    static AtomicInteger counter5;
    static int WORDCOUNT = 100_000;

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[WORDCOUNT];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        counter3 = new AtomicInteger(0);
        counter4 = new AtomicInteger(0);
        counter5 = new AtomicInteger(0);

        Thread thread1 = new Thread(() -> {
            for (String text : texts) {
                if (isPalindrome(text)) {
                    addCounter(text);
                }
            }
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            for (String text : texts) {
                if (isOneChar(text)) {
                    addCounter(text);
                }
            }
        });
        thread2.start();

        Thread thread3 = new Thread(() -> {
            for (String text : texts) {
                if (isSorted(text)) {
                    addCounter(text);
                }
            }
        });
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println("Красивых слов с длиной 3: " + counter3 + " шт");
        System.out.println("Красивых слов с длиной 4: " + counter4 + " шт");
        System.out.println("Красивых слов с длиной 5: " + counter5 + " шт");

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

    public static void addCounter(String text) {
        int lengthText = text.length();
        if (lengthText == 3) counter3.getAndIncrement();
        if (lengthText == 4) counter4.getAndIncrement();
        if (lengthText == 5) counter5.getAndIncrement();
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

