package Utilities;

import java.util.Scanner;

public class DataInput {

    public static int getIntegerNumber(String displayMessage) throws Exception {
        int number = 0;
        System.out.print(displayMessage);
        number = getIntegerNumber();
        return number;
    }

    public static int getIntegerNumber() throws Exception {
        int number = 0;
        String strInput;
        strInput = getString();
        // if (m.matches("\\d{1,10}") == false)
        if (!DataValidation.checkStringWithFormat(strInput, "\\d{1,10}")) {
            throw new Exception("Data invalid.");
        } else {
            number = Integer.parseInt(strInput);
        }
        return number;
    }

    public static double getDoubleNumber(String displayMessage) throws Exception {
        double number = 0;
        String strInput = getString(displayMessage);
        if (DataValidation.checkStringEmpty(strInput)) {
            if (!DataValidation.checkStringWithFormat(strInput, "[0-9]+[.]?[0-9]*")) {
                throw new Exception("Data invalid.");
            } else {
                number = Double.parseDouble(strInput);
            }
        }
        return number;
    }
    
    public static boolean getYesNo(String displayMessage) {
        boolean check = false;
        String input = getString(displayMessage);
        if (input.equalsIgnoreCase("Y")){
            check = true;
        } else {
            check = false;
        }
        return check;
    }

    public static String getString(String displayMessage) {
        String strInput;
        System.out.print(displayMessage);
        strInput = getString();
        return strInput;
    }

    public static String getString() {
        String strInput;
        Scanner sc = new Scanner(System.in);
        strInput = sc.nextLine();
        return strInput;
    }
}
