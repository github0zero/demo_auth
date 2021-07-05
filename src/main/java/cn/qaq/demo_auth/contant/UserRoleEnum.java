package cn.qaq.demo_auth.contant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum UserRoleEnum {
    USER("user"),
    ADMIN("admin")
    ;

    private String role;
    UserRoleEnum(String role){
        this.role = role;
    }
    private static Map<String, UserRoleEnum> map = new HashMap<>();
    static {
        Arrays.stream(UserRoleEnum.values()).forEach(x-> map.put(x.getRole(), x));
    }

    UserRoleEnum ofValue(String role){
        return map.get(role);
    }

    public String getRole() {
        return role;
    }
}
