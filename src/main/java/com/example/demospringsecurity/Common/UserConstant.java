package com.example.demospringsecurity.Common;
// ở đây chúng ta có thể định nghĩa các quyền một cách chi tiết hơn ví dụ ADMIN_WRITE, USER_READ, ...
public class UserConstant {
    public static final String DEFAULT_ROLE = "ROLE_USER";
    public static final String[] ADMIN_ACCESS = {"ROLE_ADMIN", "ROLE_MODERATOR"};
    public static final String[] MODERATOR_ACCESS = {"ROLE_MODERATOR"};
}
