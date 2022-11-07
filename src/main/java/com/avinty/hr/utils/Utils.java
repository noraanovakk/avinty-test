package com.avinty.hr.utils;

import com.avinty.hr.entities.Employee;
import com.avinty.hr.enums.Position;

public class Utils {

    private Utils() {
    }

    public static boolean isManager(Employee employee) {
        return employee.getPosition().equals(Position.MANAGER);
    }
}
