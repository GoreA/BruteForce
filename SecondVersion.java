import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @author GoreA
 */
public class SecondVersion {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a sentence:\t");
        String sentence = scanner.nextLine();

        long start = System.currentTimeMillis();
        resolveSentence(sentence);
        long end = System.currentTimeMillis() - start;

        System.out.println("Time taken: " + end);
    }

    /**
     * Takes the expression and separates the numbers and signs into two different lists: numbers and signs.
     *
     * @param sentence
     */
    private static void resolveSentence(String sentence) {
        Pattern pattern = Pattern.compile("(\\d*x*)+");
        Matcher matcher = pattern.matcher(sentence);
        List<String> numbers = new ArrayList<String>();
        while (matcher.find()) {
            if (!matcher.group().equals(""))
                numbers.add(matcher.group());
        }

        List<String> signs = new ArrayList<String>();
        pattern = Pattern.compile("[\\/\\+\\-\\*\\=]");
        matcher = pattern.matcher(sentence);
        while (matcher.find()) {
            signs.add(matcher.group());
        }

        bruteNumbers(numbers, signs);
    }

    private static void bruteNumbers(List<String> numbers, List<String> signs) {
        switch (signs.get(0).charAt(0)) {
            case '*':
                getNumbersFromMultiply(numbers);
                break;
            default:
                System.out.println("Not implemented yet!");
        }
    }

    private static void getNumbersFromMultiply(List<String> numbers) {
        System.out.println("Multiply!");
        if (isValid(numbers)) {
            getValidValues(numbers);
        }
    }

    private static boolean isValid(List<String> numbers) {
        return true;
    }

    private static void getValidValues(List<String> numbers) {
        int counter = 0;

        List<List<Integer>> intNumbers = new ArrayList<List<Integer>>();
        List<String> firstTwoNumbers = new ArrayList<>();
        firstTwoNumbers.add(numbers.get(0));
        firstTwoNumbers.add(numbers.get(1));

        for (String number : firstTwoNumbers) {
            List<Integer> intNumber = new ArrayList<Integer>();
            for (int i = 0; i < number.length(); i++) {
                if (number.charAt(i) == 'x') {
                    intNumber.add(0);
                    counter++;
                } else {
                    intNumber.add(Integer.valueOf(String.valueOf(number.charAt(i))) + 10);
                }
            }
            intNumbers.add(intNumber);
        }

        String finalStringResult = numbers.get(numbers.size() - 1);
        String finalNumberPatternString = finalStringResult.replaceAll("x", "\\\\d");


        long maxNum = Math.round(Math.pow(10, counter)) - 1;
        long initNum = Math.round(Math.pow(10, counter - 1));
        for (long i = initNum; i < maxNum; i++) {
            String digits = String.valueOf(i);
            intNumbers = new ArrayList<List<Integer>>();
            for (String number : firstTwoNumbers) {
                List<Integer> intNumber = new ArrayList<Integer>();
                for (int k = 0; k < number.length(); k++) {
                    if (number.charAt(k) == 'x') {
                        intNumber.add(Integer.valueOf(digits.substring(0, 1)));
                        digits = digits.substring(1);
                    } else {
                        intNumber.add(Integer.valueOf(String.valueOf(number.charAt(k))));
                    }
                }
                intNumbers.add(intNumber);
            }

            Integer firstNum = formNumber(intNumbers.get(0));
            Integer secondNum = formNumber(intNumbers.get(1));
            Integer finalResult = firstNum * secondNum;

            if (finalResult.toString().matches(finalNumberPatternString)) {
                if (numbers.size() > 3) {
                    if(meetIntermidiateNumbers(intNumbers, numbers, firstNum)) {
                        System.out.println(firstNum + " " + secondNum + " " + finalResult);
                    }
                } else {
                    System.out.println(firstNum + " " + secondNum + " " + finalResult);
                }
            }
        }
    }

    private static boolean meetIntermidiateNumbers(List<List<Integer>> intNumbers, List<String> numbers, Integer firstNum){
        for (int j = 0; j < intNumbers.get(1).size(); j++) {
            String intermediarNumberPattern = numbers.get(j + 2).replaceAll("x", "\\\\d");
            if (!Integer.valueOf(firstNum * intNumbers.get(1).get(j)).toString().matches(intermediarNumberPattern)) {
                return false;
            }
        }
        return true;
    }

    private static Integer formNumber(List<Integer> numberList) {
        Integer number = 0;
        for (int i = 0; i < numberList.size(); i++) {
            number = number * 10 + numberList.get(i);
        }
        return number;
    }

    private static List<Integer> populateResults(List<List<Integer>> intNumbers){
        return intNumbers.stream().map(num -> formNumber(num)).collect(Collectors.toList());
    }
}
