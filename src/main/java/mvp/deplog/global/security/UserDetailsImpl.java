package mvp.deplog.global.security;

import mvp.deplog.domain.member.domain.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private final Member member;

    public UserDetailsImpl(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(() -> member.getRole().getKey()); // key: ROLE_권한
//        authorities.add(() -> new SimpleGrantedAuthority(member.getRole().getKey())); // key: ROLE_권한
        authorities.add(new SimpleGrantedAuthority(member.getRole().getKey())); // key: ROLE_권한
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.member.getPassword();
    }

    @Override
    public String getUsername() {
        return this.member.getEmail();
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
