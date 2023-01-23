# GeneratedNickname
## Задача 1. Генерация никнеймов
Описание

Предположим, что для реализации сервиса по подбору никнеймов, вами был разработан следующий генератор случайного текста:

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

Для генерации 100'000 коротких слов вы использовали:

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

Пользователь имеет возможность выбрать из них только те, которые соответствуют критериям "красивого" никнейма, а именно:

    сгенерированное слово является палиндромом, т.е. читается одинаково как слева направо, так и справа налево, например, abba;
    сгенерированное слово состоит из одной и той же буквы, например, aaa;
    буквы в слове идут по возрастанию: сперва все a (при наличии), затем все b (при наличии), затем все c и т.д. Например, aaccc.

Вы хотите подсчитать, сколько "красивых" слов встречаются среди сгенерированных с длиной 3, 4, 5, для чего заводите три счётчика в статических полях. Проверка каждого критерия должна осуществляться в отдельном потоке.

После завершения всех трёх потоков выведите сообщение вида:

Красивых слов с длиной 3: 100 шт
Красивых слов с длиной 4: 104 шт
Красивых слов с длиной 5: 90 шт

Не используйте при этом synchronized, посмотрите в сторону AtomicInteger.
