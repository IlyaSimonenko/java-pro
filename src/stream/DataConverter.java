package stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class DataConverter {

    private DataConverter() {
        throw new RuntimeException();
    }

    public static <T> List<T> removingDuplicatesFromList(List<T> list) {
        return list.stream()
                .distinct()
                .toList();
    }

    public static Integer getHighestThirdValueInList(List<Integer> list) {
        return list.stream()
                .sorted((element1, element2) -> element2 - element1)
                .limit(3)
                .skip(2)
                .findFirst()
                .orElseThrow();
    }

    public static Integer getHighestThirdUniqueValueInList(List<Integer> list) {
        return list.stream()
                .distinct()
                .sorted((element1, element2) -> element2 - element1)
                .limit(3)
                .skip(2)
                .findFirst()
                .orElseThrow();
    }

    public static List<String> getNamesThreeMostSeniorEmployees(List<Employee> employees) {
        return employees.stream()
                .filter(employee -> employee.getPosition().equals("Инженер"))
                .sorted((e1, e2) -> e2.getAge() - e1.getAge())
                .limit(3)
                .map(Employee::getName)
                .toList();
    }

    public static double getAverageAgeOfEmployees(List<Employee> employees) {
        return employees.stream()
                .filter(employee -> employee.getPosition().equals("Инженер"))
                .mapToInt(Employee::getAge)
                .average()
                .orElse(0);
    }

    public static String getLongestWord(List<String> list) {
        return list.stream()
                .max(Comparator.comparingInt(String::length))
                .orElse("");
    }

    public static String getLongestWord(String[] words) {
        return Arrays.stream(words)
                .flatMap(s -> Arrays.stream(s.split(" ")))
                .max(Comparator.comparingInt(String::length))
                .orElse("");
    }

    public static Map<String, Long> getWordCountMap(String string) {
        return Arrays.stream(string.split(" "))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    public static void printLinesInOrderOfIncreasingWordLength(List<String> wordList) {
        wordList.stream()
                .sorted((element1, element2) -> {
                    if (element1.length() != element2.length()) {
                        return element1.length() - element2.length();
                    } else {
                        return element1.compareTo(element2);
                    }
                })
                .forEach(System.out::println);
    }

}
