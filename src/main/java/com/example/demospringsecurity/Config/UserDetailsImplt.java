package com.example.demospringsecurity.Config;

import com.example.demospringsecurity.Entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsImplt implements UserDetails {
    private String name;
    private String password;
    private List<GrantedAuthority> authorities;
    /*
    GrantedAuthority trong Spring Security là một interface đại diện cho quyền, phân quyền của người dùng trong hệ thống.
    Trong đoạn code trên, GrantedAuthority được sử dụng để lưu trữ danh sách các quyền của user:
            - authorities là một List các object GrantedAuthority.
            - lấy ra các roles
            - Dùng Arrays.stream() để split chuỗi đó thành mảng role.
            - map() sẽ chuyển mỗi phần tử trong mảng role thành object GrantedAuthority tương ứng.
            - collect() để add tất cả vào trong List authorities.

    Như vậy, authorities sẽ chứa các GrantedAuthority tương ứng với vai trò của user.
    Khi xác thực, Spring Security sẽ kiểm tra xem user đó có đủ quyền tương ứng để
    truy cập tài nguyên hay không dựa trên danh sách GrantedAuthority này.
    GrantedAuthority giúp phân quyền một cách linh hoạt và dễ quản lý thay vì hardcode trong code.
*/
    public UserDetailsImplt(User user) {
        name = user.getName();
        password = user.getPassword();
        authorities = Arrays.stream(user.getRole().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
