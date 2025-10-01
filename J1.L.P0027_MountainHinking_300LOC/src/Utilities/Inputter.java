package Utilities;

import java.util.*;

public class Inputter {

    private final static Scanner sc = new Scanner(System.in);

    public static String getString(String displayMessage) {
        String strInput;
        System.out.print(displayMessage);
        strInput = getString();
        return strInput;
    }

    public static int getIntegerNumber() throws Exception {
        int number = 0;
        String strInput;
        strInput = getString();
        if (!Validation.checkStringWithFormat(strInput, "\\d{1,10}")) {
            throw new Exception("Data invalid.");
        } else {
            number = Integer.parseInt(strInput);
        }
        return number;
    }

    public static String getString() {
        String strInput;
        Scanner sc = new Scanner(System.in);
        strInput = sc.nextLine();
        return strInput;
    }

    public static double getDoubleNumber(String displayMessage) throws Exception {
        double number = 0;
        String strInput = getString(displayMessage);
        if (Validation.checkStringEmpty(strInput)) {
            if (!Validation.checkStringWithFormat(strInput, "^-?\\.?d+(\\.\\d+)?$")) {
                throw new Exception("Invalid input");
            } else {
                number = Double.parseDouble(strInput);
            }
        }
        return number;
    }

    public static boolean getYesNo(String displayMessage) throws Exception {
        while (true) {
            System.out.println(displayMessage);
            String result = sc.nextLine();

            if (result.equalsIgnoreCase("Y")) {
                return true;
            } else if (result.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.out.println("Input not valid. Please enter 'Y' for YES or 'N' for NO: ");
            }
        }
    }
}
