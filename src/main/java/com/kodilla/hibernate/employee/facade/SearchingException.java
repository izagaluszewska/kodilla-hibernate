package com.kodilla.hibernate.employee.facade;

public class SearchingException extends Exception {
    public static String ERR_NO_EMPLOYEE_FIT = "No employee fits your query - no employee with queried letters in lastname";
    public static String ERR_NO_COMPANY_FIT = "No company fits your query - no company with queried letters in name";

    public SearchingException(String message) {
        super(message);
    }
}
