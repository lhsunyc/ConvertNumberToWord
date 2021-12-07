import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A Converter that can transfer numeric value into words
 */
public class Converter {

    String[] largeValue = {"", "Thousand", "Million", "Billion", "Trillion"};
    String[] singleValue = {"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine"};
    String[] tenValue = {"Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
    String[] doubleValue = {"Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};

    /**
     * This method will read inputs and convert the integer and decimal part separately and then combine them together
     * @param input input values from the user
     * @return a converted words for currency value in String
     */
    public String convertToWords(String input) {
        StringBuilder convertedWords = new StringBuilder();

        //retrieve only digits from the input string
        input = input.replace(",","");

        //If the input value is equivalent to zero then return "Zero"
        if (input.equals("0") || input.equals("0.00")) return singleValue[0];

        if (input.contains(".")) { //Input string consists of both integer and decimal part
            String integer = input.split("\\.")[0];//Integer String
            String decimal = input.split("\\.")[1];//Decimal String
            if (integer.length() <= largeValue.length * 3) {
                convertedWords.append(integerConverter(integer));
            } else {
                return "too large";
            }
            if (!integer.equals("0") && !decimal.equals("00")) {
                convertedWords.append(" and ");
            }
            convertedWords.append(decimalConverter(decimal));
        } else { // Input String only contains integer
            if (input.length() <= largeValue.length * 3) {
                convertedWords.append(integerConverter(input));
            } else {
                return "too large";
            }
        }
        return convertedWords.toString();
    }

    /**
     * This method is responsible for converting the decimal part of the input string (if it has).
     * @param decimal retrieved decimal part from the input string
     * @return converted words for decimal part in String
     */
    public String decimalConverter(String decimal) {
        StringBuilder convertedWords = new StringBuilder();
        // Only executed when the decimal value is not zero
        if (!Objects.equals(decimal, "00")) {
            // Values between ten and nineteen
            if (decimal.charAt(0) == '1'){
                convertedWords.append(tenValue[Integer.parseInt(decimal.charAt(1) + "")]).append(" ");
            } else { // Other cases
                if (decimal.charAt(0) != '0') {
                    convertedWords.append(doubleValue[Integer.parseInt(decimal.charAt(0) + "") - 2]).append(" ");
                }
                convertedWords.append(singleValue[Integer.parseInt(decimal.charAt(1) + "")]).append(" ");
            }
            String decimalUnit = decimal.equals("01") ? "Cent" : "Cents";//determine plural units
            convertedWords.append(decimalUnit);
        }
        return convertedWords.toString();
    }

    /**
     * This method is responsible for converting the integer part of the input string (if it has).
     * A comma is placed every 3 decimal places for numbers larger than 999 to make them easy to read. Therefore,
     * the idea of the method is that we only need to consider up to 3 digits number because when we read a large number
     * like 123456, it will be [One Hundred Twenty Three] Thousand [Four Hundred Fifty Six] Dollars. With that being said,
     * we then only need to consider adding large value units such as thousand, million and billion etc for every three
     * digits (if the value is larger than 999).
     * @param integer retrieved integer part from the input string
     * @return converted words for integer part in String
     */
    public String integerConverter(String integer) {
        boolean skip = false;
        StringBuilder convertedWords = new StringBuilder();
        int integerLength = integer.length();
        for (int i = 0; i < integerLength; i++) {
            if ((integerLength - i) % 3 == 2) {
                // Determine the second digits for every three digits (e.g. 1[2]3,4[5]6).
                if (integer.charAt(i) == '1') { // Values between ten and nineteen
                    convertedWords.append(tenValue[Integer.parseInt(integer.charAt(i + 1) + "")]).append(" ");
                    i++;
                    skip = true;
                } else if (integer.charAt(i) != '0') { // Other cases
                    convertedWords.append(doubleValue[Integer.parseInt(integer.charAt(i) + "") - 2]).append(" ");
                    skip = true;
                }
            } else if (integer.charAt(i) != '0') {
                // The first and third digits in every three digits must be a single value (e.g. [1]2[3],[4]5[6]).
                convertedWords.append(singleValue[Integer.parseInt(integer.charAt(i) + "")]).append(" ");
                if ((integerLength - i) % 3 == 0) {
                    convertedWords.append("Hundred ");
                }
                skip = true;
            }
            // Determine the large value units
            if ((integerLength - i) % 3 == 1) {
                // The skip boolean is to prevent adding large value units for three zeros. For example,
                // 1000023 is One Million Twenty Three Dollars instead of One Million [Hundred] Twenty Three Dollars
                if (skip) {
                    convertedWords.append(largeValue[(integerLength - i - 1) / 3]);
                    if ((integerLength - i - 1) / 3 != 0) {
                        convertedWords.append(" ");
                    }
                }
                skip = false;
            }
        }
        // Determine plural units
        if (!integer.equals("0")) {
            String integerUnit = integer.equals("1") ? "Dollar" : "Dollars";
            convertedWords.append(integerUnit);
        }
        return convertedWords.toString();
    }

    /**
     * This method is responsible to run unit tests
     */
    public void runUnitTest() {
        System.out.println("Running tests");
        Result result = JUnitCore.runClasses(ConverterTest.class);

        if (result.wasSuccessful()) {
            System.out.println("All tests have been passed");
        } else {
            System.out.println(result.getFailureCount() + " tests fails");
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    /**
     * Main method
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Converter converter = new Converter();
        while(true) {
            boolean isValid = false;
            System.out.print("Please enter a numeric value:");
            // Enter data using BufferReader
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            // Reading data using readLine
            String input = reader.readLine();

            // Validate input
            Pattern p = Pattern.compile("(([1-9]\\d{0,2}(,\\d{3})*)|(([1-9]\\d*)?\\d))(\\.\\d\\d)?$");
            Matcher m = p.matcher(input);
            if (m.matches()) isValid = true;

            if (isValid) {
                System.out.println(converter.convertToWords(input));
            } else if (input.equals("exit") || input.equals("quit") || input.equals("q")) {
                System.exit(0);
            } else if (input.equals("test")) {
                converter.runUnitTest();
            } else {
                System.out.println("Please check the input value format");
            }
        }
    }
}
