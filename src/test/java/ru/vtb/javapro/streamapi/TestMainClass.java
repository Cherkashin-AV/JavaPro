package ru.vtb.javapro.streamapi;


import com.sun.jdi.InternalException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.vtb.javapro.streamapi.data.Person;
import ru.vtb.javapro.streamapi.data.Person.Position;


class TestMainClass {

    private final static List<Integer> integerList = List.of(5, 2, 10, 9, 4, 3, 10, 1, 13);
    private final static List<Person> personList =  List.of(
            new Person("Bob0", 29, Position.ENGINEER),
            new Person("Bob1", 30, Position.ENGINEER),
            new Person("Bob2", 31, Position.DEVELOPER),
            new Person("Bob3", 32, Position.MANAGER),
            new Person("Bob4", 33, Position.ENGINEER),
            new Person("Bob5", 34, Position.DEVELOPER),
            new Person("Bob6", 35, Position.MANAGER),
            new Person("Bob7", 36, Position.ENGINEER),
            new Person("Bob8", 37, Position.DEVELOPER),
            new Person("Bob9", 38, Position.MANAGER)
        );
    private final static List<String> words = List.of("один", "два", "три", "четыре", "пять");

    @Test
    @DisplayName("1. Удаление из списка всех дубликатов")
    void test(){
        List<Integer> result = StreamApiUtils.deleteDuplicates(List.of(5, 5, 4, 3, 2, 2, 3, 4, 1, 1));
        Assertions.assertEquals(5, result.size());
        Assertions.assertTrue(result.containsAll(List.of(1, 2, 3, 4, 5)));
    }


    @Test
    @DisplayName("2. 3-е наибольшее число")
    void testThirdMaximumNumber() throws InternalError {
        Integer res = StreamApiUtils.nthMaximumNumber(integerList, 3L)
                                    .orElseThrow(()->new InternalError("Не удалось получить максимальный элемент"));
        Assertions.assertEquals(10, res);
    }

    @Test
    @DisplayName("3. 3-е наибольшее «уникальное» число")
    void testThirdMaximumUniqueNumber() throws InternalException {
        Integer res = StreamApiUtils.nthMaximumUniqueNumber(integerList, 3L)
                                    .orElseThrow(()->new InternalException("Не удалось получить максимальный элемент"));
        Assertions.assertEquals(9, res);
    }

    @Test
    @DisplayName("4. 3-е наибольшее старших инженеров")
    void testThreeOldestEngineersDescAge(){

        List<Person> result = StreamApiUtils.oldestByPositionDescAge(personList, Position.ENGINEER ,3);
        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals("Bob7", result.get(0).getName());
        Assertions.assertEquals("Bob4", result.get(1).getName());
        Assertions.assertEquals("Bob1", result.get(2).getName());
    }

    @Test
    @DisplayName("5. Средний возраст инженеров")
    void testAverageAge(){
        Double avgAge = StreamApiUtils.averageAge(personList, Position.ENGINEER);
        Assertions.assertEquals(32,avgAge);
    }

    @Test
    @DisplayName("6. Самое длинное слово из списка")
    void testLongestWord(){
        String result = StreamApiUtils.longestWord(words)
                            .orElseThrow(InternalError::new);
        Assertions.assertEquals("четыре",result);
    }

    @Test
    @DisplayName("7. Количество слов в строке (Map)")
    void testWordsToMap(){
        String sourceString = "1, 2, 3, 1, 2, 1";
        Map<String, Long> strings = StreamApiUtils.wordsToMap(sourceString);
        Assertions.assertEquals(3, strings.size());
        Assertions.assertTrue(strings.containsKey("1") && strings.get("1") == 3);
        Assertions.assertTrue(strings.containsKey("2") && strings.get("2") == 2);
        Assertions.assertTrue(strings.containsKey("3") && strings.get("3") == 1);
    }

    @Test
    @DisplayName("8. Слова в порядке увеличения длины")
    void testWordsOrderByLength(){
        String sourceString = "ааа, бб, а, ба, в, б";
        List<String> strings = StreamApiUtils.wordsOrderedByLength(sourceString);
        Assertions.assertEquals(6, strings.size());
        Assertions.assertEquals("а", strings.get(0));
        Assertions.assertEquals("б", strings.get(1));
        Assertions.assertEquals("в", strings.get(2));
        Assertions.assertEquals("ба", strings.get(3));
        Assertions.assertEquals("бб", strings.get(4));
        Assertions.assertEquals("ааа", strings.get(5));
    }

    @Test
    @DisplayName("8. Самое длинное слово в двухмерном массиве")
    void testLongestWord2D() {
        String[] array = {
            "Слово слово слово самое_длинное_слово простое_слово",
            "Слово слово слово простое_слово слово"};

        String longestWord = StreamApiUtils.longestWord(array).orElseThrow(()->new RuntimeException("Не удалось найти самое длинное слово"));
        Assertions.assertEquals("самое_длинное_слово", longestWord);
    }

}
