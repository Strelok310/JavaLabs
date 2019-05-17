package Labs;

import Utils.Matrix.Matrix;
import Utils.Streams2.UserData;
import Utils.Streams3.Combinatorics;
import Utils.Streams3.UserLogged;

import java.awt.geom.Point2D;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;
import java.util.function.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.*;

public class StreamLabs3 {
    private static final String LINE = "\n==============================================================================================================\n";

    public static void main(String args[]) throws Exception {
        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
        task8();
        task9();
        task10();
        task11();
        task12();
        task13();
        task14();
        task15();
        task16();
        task17();
        task18();
        task19();
        task20();
        task21();
        task22();
        task23();
        task24();
        task25();
    }

    static void task1() {
        System.out.println(LINE);
        System.out.println("Return first element of array");
        ArrayList<UserData> dataList = new ArrayList<>(Arrays.asList(
                new UserData(15,"Image.jpg", LocalDate.of(2019,5,1)),
                new UserData(9,"Star_Wars.mkv", LocalDate.of(2019,7,13)),
                new UserData(100,"Info.txt", LocalDate.of(2019,5,1)),
                new UserData(85,"Labs.docx", LocalDate.of(2022,5,5)),
                new UserData(100,"secret.rar", LocalDate.of(2019,5,1))
        ));
        String str = "Info.txt";

        System.out.println("Input:");
        System.out.println(dataList);
        System.out.println("Search for: " + str);


        //==============================================================================================================

        UserData item = dataList.stream()
                                .filter((x) -> x.name.equals(str))
                                .findFirst().orElse(new UserData(3,"Picture.png", LocalDate.of(2018,6,15)));

        System.out.println("\nOutput:");
        System.out.println(item.toStringItem());

        //==============================================================================================================

        dataList.clear();
        item = dataList.stream()
                       .filter((x) -> x.name.equals(str))
                       .findFirst().orElse(new UserData(3,"Picture.png", LocalDate.of(2018,6,15)));

        System.out.println("\nOutput (not found):");
        System.out.println(item.toStringItem());
    }

    static void task2() {
        System.out.println(LINE);
        System.out.println("Return any element of array with current year");
        ArrayList<UserData> list = new ArrayList<>(Arrays.asList(
                new UserData(15,"Image.jpg", LocalDate.of(2019,5,1)),
                new UserData(9,"Star_Wars.mkv", LocalDate.of(2019,7,13)),
                new UserData(100,"Info.txt", LocalDate.of(2020,5,17)),
                new UserData(85,"Labs.docx", LocalDate.of(2022,5,5)),
                new UserData(113,"secret.rar", LocalDate.of(2019,5,1))
        ));
        String str = "Info.txt";
        LocalDate date = LocalDate.now();
        //LocalDate date = LocalDate.of(2019,5,13);

        System.out.println("Input:");
        System.out.println(list);
        System.out.println("Search for: " + str);
        System.out.println("Current date: " + date);

        //==============================================================================================================

        UserData item = list.stream()
                            .filter((x) -> x.name.equals(str) && x.date.isAfter(date))
                            .findAny().orElse(null);

        System.out.println("\nOutput:");
        System.out.println(item.toStringItem());

        //==============================================================================================================

        list.clear();
        item = list.stream()
                   .filter((x) -> x.name.equals(str) && x.date.isAfter(date))
                   .findAny().orElse(null);

        System.out.println("\nOutput (not found):");
        if(item == null) System.out.println("Null");
        else System.out.println("not Null");
    }

    static void task3() throws Exception {
        System.out.println(LINE);
        System.out.println("Count words and sort");
        Stream<String> fileStream = Files.lines(Paths.get(ClassLoader.getSystemResource("wordCount.txt").toURI()));

        System.out.println("Input:\nwordCount.txt");

        //Variant 1
        System.out.println("\nVariant 1:");
        HashMap<String, Integer> map = new HashMap<>();
        String[] words = fileStream
            .flatMap((x) -> Arrays.stream(x.split(" ")))
            .peek((x) -> {
                if(map.containsKey(x)) map.replace(x, map.get(x) + 1);
                else map.put(x, 1);
            })
            .distinct()
            .sorted((x, y) -> map.get(y) - map.get(x))
            .toArray(String[]::new);

        System.out.println("Output:");
        System.out.println(Arrays.toString(words));
        System.out.println("Word frequency:");
        System.out.println(map);

        //==============================================================================================================

        //Variant 2
        System.out.println("\nVariant 2:");
        fileStream = Files.lines(Paths.get(ClassLoader.getSystemResource("wordCount.txt").toURI()));
        Stream<String> res = fileStream.flatMap((x) -> Arrays.stream(x.split(" ")))
                                            .collect(Collectors.groupingBy(String::toString, Collectors.counting()))
                                            .entrySet()
                                            .stream()
                                            .sorted((x,y) -> Integer.parseInt(String.valueOf(y.getValue() - x.getValue())))
                                            .map((x) -> x.getKey());
        System.out.println(Arrays.toString(res.toArray(String[]::new)));

    }

    static void task4() {
        System.out.println(LINE);
        System.out.println("Create ArrayList from stream");
        Integer[] mas = {1,2,3,4,5,6,7,8};

        System.out.println("\nVariant 1:");
        ArrayList<Integer> list = new ArrayList<>(Arrays.stream(mas).collect(Collectors.toList()));
        System.out.println(list);

        System.out.println("\nVariant 2:");
        list = Arrays.stream(mas).collect(Collectors.toCollection(ArrayList::new));
        System.out.println(list);

        System.out.println("\nVariant 3:");
        list = Arrays.stream(mas).collect(Collector.of( ArrayList::new,
                                                        (x, item) -> x.add(item),
                                                        (x, y) -> { x.addAll(y); return x; }));
        System.out.println(list);
    }

    static void task5() {
        System.out.println(LINE);
        System.out.println("Filter string list by length");

        ArrayList<String> list = new ArrayList<>(Arrays.asList(
                "Mind",
                "Desert",
                "Snowfall",
                "Rendering",
                "Harem",
                "Fiancee",
                "Overlord",
                "Professional",
                "Coal",
                "Art",
                "Meltdown"
        ));
        System.out.println("Input:");
        System.out.println(list);

        System.out.println("\nOutput:");

        System.out.println("Words with length less then 6:");
        Stream<String> stream = list.stream().filter((x) -> x.length() <= 5);
        System.out.println(stream.collect(Collectors.toList()));

        System.out.println("Words with length less then 9:");
        stream = list.stream().filter((x) -> x.length() <= 8);
        System.out.println(stream.collect(Collectors.toList()));
    }

    static void task6() throws Exception {
        System.out.println(LINE);
        System.out.println("Return HashMap with words and their count");
        Stream<String> fileStream = Files.lines(Paths.get(ClassLoader.getSystemResource("wordCount.txt").toURI()));

        System.out.println("Input:\nwordCount.txt");

        HashMap<String, Long> map = new HashMap<>(fileStream.flatMap((x) -> Arrays.stream(x.split(" ")))
                .collect(Collectors.groupingBy(String::toString, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((x,y) -> Math.toIntExact(y.getValue() - x.getValue()))
                .limit(10)
                .collect(Collectors.toMap((x) -> x.getKey(), (x) -> x.getValue())) );

        /*HashMap<String, Long> map = fileStream.flatMap((x) -> Arrays.stream(x.split(" ")))
                .collect(Collectors.groupingBy(String::toString, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((x,y) -> Math.toIntExact(y.getValue() - x.getValue()))
                .limit(10)
                .collect(Collector.of(  HashMap::new,
                                        (x, item) -> x.put(item.getKey(), item.getValue()),
                                        (x, y) -> { x.putAll(y); return x; }) );*/

        System.out.println("\nOutput:");
        System.out.println(map);
    }

    static void task7() throws Exception {
        System.out.println(LINE);
        System.out.println("Return HashMap with words and their probability");
        Stream<String> fileStream = Files.lines(Paths.get(ClassLoader.getSystemResource("wordCount.txt").toURI()));

        System.out.println("Input:\nwordCount.txt");

        HashMap<String, Long> map = new HashMap<>(fileStream.flatMap((x) -> Arrays.stream(x.split(" ")))
                                            .collect(Collectors.groupingBy(String::toString, Collectors.counting())));

        int words = map.entrySet()
                        .stream()
                        .mapToInt((x) -> Math.toIntExact(x.getValue()))
                        .sum();

        HashMap<String, Float> freqMap = new HashMap<>(map.entrySet()
                .stream()
                .sorted((x,y) -> Math.toIntExact(y.getValue() - x.getValue()))
                .limit(10)
                .collect(Collectors.toMap((x) -> x.getKey(), (x) -> x.getValue() * 100 / Float.valueOf(words))) );

        System.out.println("\nOutput:");
        System.out.println(freqMap);
    }

    static void task8() {
        System.out.println(LINE);
        System.out.println("Return size of array");
        Integer[] mas = {1,2,3,4,5,6,7,8};

        System.out.println("Input:");
        System.out.println(Arrays.toString(mas));

        System.out.println("\nOutput:");
        long size = Arrays.stream(mas).count();
        System.out.println(size);
    }

    static void task9() {
        System.out.println(LINE);
        System.out.println("Return number of elements with current date");
        ArrayList<UserData> list = new ArrayList<>(Arrays.asList(
                new UserData(15,"Image.jpg", LocalDate.of(2019,5,1)),
                new UserData(9,"Star_Wars.mkv", LocalDate.of(2019,7,13)),
                new UserData(100,"Info.txt", LocalDate.of(2020,5,17)),
                new UserData(85,"Labs.docx", LocalDate.of(2022,5,5)),
                new UserData(113,"secret.rar", LocalDate.of(2019,5,1))
        ));

        System.out.println("Input:");
        System.out.println(list);

        LocalDate date = LocalDate.now();
        long count = list.stream().filter((x) -> x.date.getYear() == date.getYear()).count();
        System.out.println("\nOutput:\n" + count);
    }

    static void task10() {
        System.out.println(LINE);
        System.out.println("Check whether array contains name \"admin\" or not");
        ArrayList<UserData> list = new ArrayList<>(Arrays.asList(
                new UserData(15,"Image.jpg", LocalDate.of(2019,5,1)),
                new UserData(9,"Star_Wars.mkv", LocalDate.of(2019,7,13)),
                new UserData(100,"admin", LocalDate.of(2020,5,17)),
                new UserData(85,"Labs.docx", LocalDate.of(2022,5,5)),
                new UserData(113,"secret.rar", LocalDate.of(2019,5,1))
        ));

        System.out.println("Input:");
        System.out.println(list);

        boolean adminPresented = list.stream().anyMatch((x) -> x.name.equals("admin"));
        System.out.println("\nOutput:\n" + adminPresented);

        list.remove(2);
        System.out.println("\nInput:");
        System.out.println(list);

        adminPresented = list.stream().anyMatch((x) -> x.name.equals("admin"));
        System.out.println("\nOutput:\n" + adminPresented);
    }

    static void task11() {
        System.out.println(LINE);
        System.out.println("Check whether all elements of ArrayList contains date with current year");
        ArrayList<UserData> list = new ArrayList<>(Arrays.asList(
                new UserData(15,"Image.jpg", LocalDate.of(2019,5,1)),
                new UserData(9,"Star_Wars.mkv", LocalDate.of(2019,7,13)),
                new UserData(100,"Info.txt", LocalDate.of(2020,5,17)),
                new UserData(85,"Labs.docx", LocalDate.of(2022,5,5)),
                new UserData(113,"secret.rar", LocalDate.of(2019,5,1))
        ));

        System.out.println("Input:");
        System.out.println(list);

        LocalDate date = LocalDate.now();
        boolean thisYear =  list.stream().allMatch((x) -> x.date.getYear() == date.getYear());
        System.out.println("\nOutput:\n" + thisYear);

        list.get(2).date = LocalDate.of(2019,5,17);
        list.get(3).date = LocalDate.of(2019,5,5);
        System.out.println("\nInput:");
        System.out.println(list);

        thisYear =  list.stream().allMatch((x) -> x.date.getYear() == date.getYear());
        System.out.println("\nOutput:\n" + thisYear);
    }

    static void task12() {
        System.out.println(LINE);
        System.out.println("Return minimal element of array");
        Integer[] mas = {6, 43, 3, 87, 6, 16, 4, 21, 13, 38};

        System.out.println("Input:");
        System.out.println(Arrays.toString(mas));

        Integer min = Arrays.stream(mas).min(Integer::compareTo).get();
        System.out.println("Output:\n" + min);
    }

    static void task13() {
        System.out.println(LINE);
        System.out.println("Return maximal element of array");
        Integer[] mas = {6, 43, 3, 87, 6, 16, 4, 21, 13, 38};

        System.out.println("Input:");
        System.out.println(Arrays.toString(mas));

        Integer max = Arrays.stream(mas).max(Integer::compareTo).get();
        System.out.println("Output:\n" + max);
    }

    static void task14() {
        System.out.println(LINE);
        System.out.println("Return nearest point to X");
        ArrayList<Point2D.Float> list = new ArrayList<>(Arrays.asList(
                new Point2D.Float(1.3f,6.4f),
                new Point2D.Float(10.5f,3.5f),
                new Point2D.Float(1.1f,4.7f),
                new Point2D.Float(10.3f,4.7f),
                new Point2D.Float(1.1f,7.6f),
                new Point2D.Float(1.5f,3.2f),
                new Point2D.Float(5.1f,8.5f),
                new Point2D.Float(6.3f,2.4f),
                new Point2D.Float(1.1f,5.7f),
                new Point2D.Float(8.4f,8.9f),
                new Point2D.Float(6.9f,4.8f)
        ));
        Point2D.Float point = new Point2D.Float(5.0f, 5.0f);

        System.out.println("Input:");
        list.stream().forEach((x) -> System.out.println(x));
        System.out.println("X: " + point);

        Point2D.Float nearest = list.stream().min((p1, p2) -> Double.compare(point.distance(p1), point.distance(p2))).get();
        System.out.println("\nOutput:");
        System.out.println(nearest);
    }

    static void task15() {
        System.out.println(LINE);
        System.out.println("Return furthest point to X");
        ArrayList<Point2D.Float> list = new ArrayList<>(Arrays.asList(
                new Point2D.Float(1.3f,6.4f),
                new Point2D.Float(10.5f,3.5f),
                new Point2D.Float(1.1f,4.7f),
                new Point2D.Float(10.3f,4.7f),
                new Point2D.Float(1.1f,7.6f),
                new Point2D.Float(1.5f,3.2f),
                new Point2D.Float(5.1f,8.5f),
                new Point2D.Float(6.3f,2.4f),
                new Point2D.Float(1.1f,5.7f),
                new Point2D.Float(8.4f,8.9f),
                new Point2D.Float(6.9f,4.8f)
        ));
        Point2D.Float point = new Point2D.Float(5.0f, 5.0f);

        System.out.println("Input:");
        list.stream().forEach((x) -> System.out.println(x));
        System.out.println("X: " + point);

        Point2D.Float furthest = list.stream().max((p1, p2) -> Double.compare(point.distance(p1), point.distance(p2))).get();
        System.out.println("\nOutput:");
        System.out.println(furthest);
    }

    static void task16() {
        System.out.println(LINE);
        System.out.println("Exponential Random stream");
        Supplier<Integer> expGenerator = () -> Math.toIntExact(Math.round( -1000 * Math.log(1 - Math.random()) ));

        System.out.println("Input:\nExponential Random generator");

        final int SIZE = 100000;

        IntSummaryStatistics stat = Stream.generate(expGenerator)
                                            .limit(SIZE)
                                            .collect(Collectors.summarizingInt((x) -> x));
        double variance = Stream.generate(expGenerator)
                                .limit(SIZE)
                                .mapToDouble(Double::valueOf)
                                .reduce(0.0, (x,y) -> x += Math.pow(y - stat.getAverage(), 2) / (SIZE - 1));
        double median = Stream.generate(expGenerator)
                                .limit(SIZE)
                                .sorted(Integer::compareTo)
                                .skip(SIZE / 2 - 1)
                                .limit(2)
                                .mapToInt(Integer::intValue)
                                .sum() / 2.0;

        int mode = Stream.generate(expGenerator)
                            .limit(SIZE)
                            .collect(Collectors.groupingBy(Integer::intValue, Collectors.counting()))
                            .entrySet()
                            .stream()
                            .sorted((x,y) -> Long.compare(x.getValue(), y.getValue()))
                            .findFirst()
                            .get()
                            .getKey();

        double deviation = Math.sqrt(variance);

        System.out.println("\nOutput:");
        System.out.println("Min: " + stat.getMin());
        System.out.println("Max: " + stat.getMax());
        System.out.println("Average: " + stat.getAverage());
        System.out.println("Sum: " + stat.getSum());

        System.out.println("Expected value (M): " + stat.getAverage());
        System.out.println("Variance (D): " + variance);
        System.out.println("Median: " + median);
        System.out.println("Mode: " + mode);
        System.out.println("Deviation: " + deviation);


        System.out.println("\nNote: Lambda = 0.001");
    }

    static void task17() {
        System.out.println(LINE);
        System.out.println("Return all combinations of strings");
        String[] str = {"A", "B", "C", "D"};

        System.out.println("Input:");
        System.out.println(Arrays.toString(str));

        System.out.println("\nOutput:");

        System.out.println("\nVariant 1");
        Combinatorics.combine(str)
                     .forEach(System.out::println);

        System.out.println("\nVariant 2");
        Combinatorics.combineInt(str)
                     .forEach(System.out::println);
    }

    static void task18() {
        System.out.println(LINE);
        System.out.println("Return all combinations of unique symbols in string");
        String[] str = {"A", "B", "C"};

        System.out.println("Input:");
        System.out.println(Arrays.toString(str));

        String result = Combinatorics.combineInt(str)
                                     .map((x) -> x.stream()
                                                  .reduce("", (z, w) -> z + w))
                                     .reduce("", (z,w) -> z + w);

        System.out.println("Output:");
        System.out.println(result);
    }

    static void task19() {
        System.out.println(LINE);
        System.out.println("Return min and max values on giving range");

        final int BEGIN = 1;
        final int END = 5;

        System.out.println("Input: ");
        System.out.format("Range from %s to %s\n", BEGIN, END);

        DoubleSummaryStatistics stat =  DoubleStream.iterate(Double.valueOf(BEGIN), (x) -> x + 0.2)
                    .limit(Math.round((END - BEGIN) / 0.2))
                    .map((x) -> Math.pow(x, Math.exp(x)) - 3 * Math.sqrt(2 * Math.round(Math.log(x)/Math.log(3)))
                                                            / (Math.pow(x, 2) + Math.pow(5, x)) )
                    .summaryStatistics();

        System.out.println("\nOutput:");
        System.out.println("Minimal: " + stat.getMin());
        System.out.println("Maxiamal: " + stat.getMax());
    }

    static void task20() {
        System.out.println(LINE);
        System.out.println("Return Integral");

        final double BEGIN = 1;
        final double END = 10;
        final double STEP = 0.001;

        System.out.println("Input: ");
        System.out.format("Range from %s to %s, step %s\n", BEGIN, END, STEP);

        double sum =  STEP * DoubleStream.iterate(Double.valueOf(BEGIN), (x) -> x + STEP)
                .limit(Math.round((END - BEGIN) / STEP))
                .map((x) ->  3 * Math.sqrt(2 * Math.round(Math.log(x)/Math.log(2)))
                             / (Math.pow(x, 2) + Math.pow(5, x)) )
                .sum();

        System.out.println("\nOutput:");
        System.out.println("Integral: " + sum);
    }

    static void task21() {
        System.out.println(LINE);
        System.out.println("Apply function to all elements of stream");
        StringBuilder[] str = {
            new StringBuilder("Snowfall"),
            new StringBuilder("Overlord"),
            new StringBuilder("Meltdown"),
            new StringBuilder("Nuclear"),
            new StringBuilder("Keanu")
        };

        System.out.println("Input:");
        System.out.println(Arrays.toString(str));

        Stream<StringBuilder> stream = Arrays.stream(str).peek((x) -> x.append("_new"));

        System.out.println("\nOutput:");
        System.out.println(Arrays.toString(stream.toArray(StringBuilder[]::new)));
    }

    static void task22() {
        System.out.println(LINE);
        System.out.println("Return IntStream");
        String[] str = {"A", "B123", "11", "A.5C", "Hello3.14world"};

        System.out.println("Input:");
        System.out.println(Arrays.toString(str));

        Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?", Pattern.CASE_INSENSITIVE);

        IntStream stream = Arrays.stream(str)
                                      .filter((x) -> x.matches(".*\\d+.*"))
                                      .map((x) -> {
                                          Matcher matcher = pattern.matcher(x);
                                          if(matcher.find()) return Double.valueOf(x.substring(matcher.start(), matcher.end()));
                                          return null;
                                      })
                                      .filter((x) -> x != null)
                                      .mapToInt((x) -> Math.toIntExact(Math.round(x)));

        System.out.println("Output:");
        System.out.println(Arrays.toString(stream.toArray()));
    }

    /**
     * Вернуть сумму нечетных чисел
     */

    static void task23() {
        System.out.println(LINE);
        System.out.println("Return sum of odd elements");
        int[] mas = {1,2,3,4,5,6,7,8,9,10};

        System.out.println("Input:");
        System.out.println(Arrays.toString(mas));

        int sum = Arrays.stream(mas)
                        .filter((x) -> x % 2 != 0)
                        .sum();

        System.out.println("\nOutput:");
        System.out.println(sum);
    }

    static void task24() {
        System.out.println(LINE);
        System.out.println("Return first element of array");
        ArrayList<UserLogged> list = new ArrayList<>(Arrays.asList(
                new UserLogged(15,"Snowfall", true),
                new UserLogged(9,"Overlord", false),
                new UserLogged(100,"Meltdown", true),
                new UserLogged(85,"Nuclear", true),
                new UserLogged(99,"Keanu", false)
        ));

        System.out.println("Input:");
        System.out.println(list);

        Map result = list.stream().collect(Collectors.groupingBy((x) -> x.logged));

        System.out.println("Output");
        System.out.println(result);
    }

    static void task25() {
        System.out.println(LINE);
        System.out.println("Return first element of array");
        ArrayList<UserLogged> list = new ArrayList<>(Arrays.asList(
                new UserLogged(15,"Snowfall", true),
                new UserLogged(1,"Sun", true),
                new UserLogged(23,"Snow", true),
                new UserLogged(9,"Overlord", false),
                new UserLogged(100,"Meltdown", true),
                new UserLogged(89,"Steam", true),
                new UserLogged(85,"Nuclear", true),
                new UserLogged(37,"Slime", false),
                new UserLogged(99,"Keanu", false)
        ));

        System.out.println("Input:");
        System.out.println(list);

        Map result = list.stream()
                .map((x) -> x.name)
                .collect(Collectors.groupingBy((x) -> x.substring(0,1)))
                .entrySet()
                .stream()
                .collect(Collectors.groupingBy((x) -> x.getValue().size() < 5))
                .entrySet()
                .stream()
                .map((x) -> {
                    if(!x.getKey()) return x;
                    HashMap<String, List<String>> map = new HashMap<String, List<String>>();
                    map.putAll(x.getValue());
                    return x.getValue()
                            .stream()
                            .reduce(new HashMap<String, List<String>>(), (z, y) -> {
                                return z;
                            });
                });

        //HashMap<String, List<String>>
        //HashMap<String, HashMap<String, List<String>>>


        System.out.println("Output");
        System.out.println(result);
    }
}






































