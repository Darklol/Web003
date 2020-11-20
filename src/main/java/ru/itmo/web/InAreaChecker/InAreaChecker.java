package ru.itmo.web.InAreaChecker;


public class InAreaChecker {

    private InAreaChecker(){}
    public static boolean getResult(double x, double y, double r) {
        //Quarter part
        boolean firstQuarter = x >= 0 && y >= 0;
        boolean thirdQuarter = x <= 0 && y <= 0;
        boolean fourthQuarter = x >= 0 && y <= 0;
        //check part
        return (thirdQuarter && (x >= -r) && (y >= -r/2)) ||
                (fourthQuarter && (x * x + y * y <= r * r)) ||
                (firstQuarter && (y <= -x + r));
    }
}
