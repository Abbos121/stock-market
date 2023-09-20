package com.vention.stockmarket.enumuration;


import java.util.HashSet;
import java.util.Set;

public enum Role {
    USER,
    ADMIN;


    /**
     * this method accepts string roles whose format is like [ADMIN, USER]
     * and converts it to Set of Role enums
     */
    public static Set<Role> convertFromStringToSet(String strRoles) {
        Set<Role> roles = new HashSet<>();
        var allRoles = strRoles.substring(0, strRoles.length() - 1).split(",");
        for (String role : allRoles) {
            roles.add(Role.valueOf(role.trim()));
        }
        return roles;
    }
}
