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
        var allRoles = strRoles.substring(1, strRoles.length() - 1).split(",");
        for (String role : allRoles) {
            roles.add(Role.valueOf(role.trim()));
        }
        return roles;
    }

    /**
     * @param roles set of roles
     * @return converted array of roles of string type
     */
    public static String[] convertSetToArray(Set<Role> roles) {
        String[] rolesArr = new String[roles.size()];
        int i = 0;
        for (Role role : roles) {
            rolesArr[i++] = role.name();
        }
        return rolesArr;
    }
}
