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
public class FirstVersion {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a sentence:\t");
        String sentence = scanner.nextLine();

        long start = System.currentTimeMillis();
        List<Integer> response = resolveSentence(sentence);
        long end = System.currentTimeMillis() - start;
        if (response != null) {
            for (Integer responseNum : response)
                System.out.println(responseNum);
            System.out.println("Time taken: " + end);
        } else {
            System.out.println("No valid Numbers");
        }

    }

    /**
     * Takes the expression and separates the numbers and signs into two different lists: numbers and signs.
     *
     * @param sentence
     */
    private static List<Integer> resolveSentence(String sentence) {
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

        return bruteNumbers(numbers, signs);
    }

    private static List<Integer> bruteNumbers(List<String> numbers, List<String> signs) {
        switch (signs.get(0).charAt(0)) {
            case '*':
                return getNumbersFromMultiply(numbers);
            default:
                System.out.println("Not implemented yet!");
                return null;
        }
    }

    private static List<Integer> getNumbersFromMultiply(List<String> numbers) {
        System.out.println("Multiply!");
        if (isValid(numbers)) {
            return getValidValues(numbers);
        }
        return null;
    }

    private static boolean isValid(List<String> numbers) {
        return true;
    }

    private static List<Integer> getValidValues(List<String> numbers) {
        List<Integer> correctResult = new ArrayList<Integer>();
        int counter = 0;

        List<List<Integer>> intNumbers = new ArrayList<List<Integer>>();

        for (String number : numbers) {
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


        long maxNum = Math.round(Math.pow(10, counter)) - 1;
        long initNum = Math.round(Math.pow(10, counter - 1));

        for (long i = initNum; i < maxNum; i++) {
            String digits = String.valueOf(i);
            intNumbers = new ArrayList<List<Integer>>();
            for(String number : numbers){
                List<Integer> intNumber = new ArrayList<Integer>();
                for(int k = 0; k<number.length(); k++){
                   if(number.charAt(k) == 'x'){
                        intNumber.add(Integer.valueOf(digits.substring(0, 1)));
                        digits=digits.substring(1);
                    }
                    else{
                        intNumber.add(Integer.valueOf(String.valueOf(number.charAt(k))));
                    }
                }
                intNumbers.add(intNumber);
            }

            Integer firstNum = formNumber(intNumbers.get(0));
            correctResult.add(firstNum);
            Integer secondNum = formNumber(intNumbers.get(1));
            correctResult.add(secondNum);
            Integer finalResult = formNumber(intNumbers.get(intNumbers.size() - 1));

            if (firstNum * secondNum == finalResult) {
                for (int j = 0; j < intNumbers.get(1).size(); j++) {
                    if (firstNum * intNumbers.get(1).get(j) == formNumber(intNumbers.get(j + 2))) {
                        correctResult.add(formNumber(intNumbers.get(j + 2)));
                    } else {
                        correctResult.clear();
                    }
                }
            } else {
                correctResult.clear();
            }
            if (correctResult.size() > 0) {
                return populateResults(intNumbers);
            }
        }

        return null;
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
