package stream;

import java.util.Arrays;
import java.util.List;

import static stream.DataConverter.*;

public class Main {

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 2, 3, 5);

        System.out.println(removingDuplicatesFromList(list));

        list = Arrays.asList(5, 2, 10, 9, 4, 3, 10, 1, 13);

        System.out.println(getHighestThirdValueInList(list));

        System.out.println(getHighestThirdUniqueValueInList(list));

        List<Employee> employees = Arrays.asList(
                new Employee("Иван", 35, "Инженер"),
                new Employee("Петр", 40, "Инженер"),
                new Employee("Мария", 30, "Врач"),
                new Employee("Анна", 46, "Инженер"),
                new Employee("Дмитрий", 38, "Менеджер")
        );

        System.out.println(getNamesThreeMostSeniorEmployees(employees));

        System.out.println(getAverageAgeOfEmployees(employees));

        List<String> names = Arrays.asList("Илья", "Александра", "Ефросинья", "Константин", "Олег");

        System.out.println(getLongestWord(names));

        String string = "Илья Александра Ефросинья Константин Олег Илья Александра Ефросинья Илья";

        System.out.println(getWordCountMap(string));

        printLinesInOrderOfIncreasingWordLength(names);

        String[] words = {
                "apple banana orange strawberry kiwi",
                "cat dog elephant giraffe lion",
                "red blue green yellow purple",
                "java python ruby javascript kotlin"
        };

        System.out.println(getLongestWord(words));

    }

}
