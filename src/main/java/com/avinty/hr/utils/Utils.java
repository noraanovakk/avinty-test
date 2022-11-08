package com.avinty.hr.utils;

import com.avinty.hr.entities.Employee;
import com.avinty.hr.enums.Position;
import com.avinty.hr.security.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utils {

    private Utils() {
    }

    public static boolean isManager(Employee employee) {
        return employee.getPosition().equals(Position.MANAGER);
    }

    public static Long getLoggedInUser() {
        var userDetail = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetail.getId();
    }
}
