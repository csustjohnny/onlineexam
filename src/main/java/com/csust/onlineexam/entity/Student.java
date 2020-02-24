package com.csust.onlineexam.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * @author     ：Lenovo.
 * @date       ：Created in 19:01 2020/2/16
 * @Version: 1.1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Student extends StudentUser implements UserDetails {

    /**专业班级名称*/
    private String className;
    /**学院名称*/
    private String schoolName;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("student"));
    }

    @Override
    public String getUsername() {
        return super.getStudentNo();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
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
