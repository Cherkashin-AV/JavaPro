package ru.vtb.javapro.streamapi;


import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import ru.vtb.javapro.streamapi.data.Person;
import ru.vtb.javapro.streamapi.data.Person.Position;


public class StreamApiUtils {

    private StreamApiUtils() {
        throw new IllegalCallerException("Нельзя создать экземпляр этого класса, используйте статические методы");
    }

    public static <T> List<T> deleteDuplicates(List<T> list){
        return list.stream().distinct().toList();
    }

    public static <T> Optional<T> nthMaximumNumber(Collection<T> collections, Long n){
        return collections.stream()
                   .sorted(Collections.reverseOrder())
                   .skip(n - 1)
                   .findFirst();
    }
    public static <T> Optional<T> nthMaximumUniqueNumber(Collection<T> collections, Long n){
        return collections.stream()
                   .sorted(Collections.reverseOrder())
                   .distinct()
                   .skip(n-1)
                   .findFirst();
    }

    public static List<Person> oldestByPositionDescAge(List<Person> persons, Position position,  int top){
        return persons.stream()
            .filter(p->p.getPosition()== position)
            .sorted(Comparator.comparingInt(Person::getAge).reversed())
            .limit(top)
            .toList();
    }

    public static Double averageAge(List<Person> persons, Position position){
        return persons.stream()
            .filter(p->p.getPosition()==position)
            .collect(Collectors.averagingDouble(Person::getAge));
    }

    public static Optional<String> longestWord(List<String> words){
        return words.stream()
            .max(Comparator.comparingInt(String::length));
    }

    public static Map<String, Long> wordsToMap(String sourceString){
        return Stream.of(sourceString.split("[, ]+"))
            .collect(Collectors.groupingBy(w->w, Collectors.counting()));
    }

    public static List<String> wordsOrderedByLength(String sourceString){
         return Stream.of(sourceString.split("[, ]+"))
             .sorted(Comparator.comparingInt(String::length).thenComparing(s->s))
             .toList();
    }

    public static Optional<String> longestWord(String[] words){
         return Arrays.stream(words)
                    .flatMap(s -> Arrays.stream(s.split(" +")))
                    .max(Comparator.comparingInt(String::length));
    }
}
