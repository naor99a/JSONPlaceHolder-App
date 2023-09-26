package Utilities;

import java.util.Collection;
import java.util.stream.Collectors;

public class Utilities {
    public static void printWithSeperatingLines(Collection<String> str) {
        System.out.println(str.stream().collect(Collectors.joining("\n\n")));
    }
}
