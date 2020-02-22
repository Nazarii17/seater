package com.example.sweater.domain;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Set;

@Entity
@Data
@Table(name = "usr")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "username")
    @NotBlank(message = "Username can't be empty!")
    private String username;
    @Column(name = "password")
    @NotBlank(message = "Password can't be empty!")
    private String password;
    @Column(name = "active")
    private boolean active;
    @Column(name = "email")
    @Email(message = "Email's not correct!")
    @NotBlank(message = "Email can't be empty!")
    private String email;
    @Column(name = "activationCode")
    private String activationCode;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Message> messages;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof User)) return false;
//        User user = (User) o;
//        return active == user.active &&
//                Objects.equals(id, user.id) &&
//                Objects.equals(username, user.username) &&
//                Objects.equals(password, user.password) &&
//                Objects.equals(email, user.email) &&
//                Objects.equals(activationCode, user.activationCode) &&
//                Objects.equals(roles, user.roles) &&
//                Objects.equals(messages, user.messages);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, username, password, active, email, activationCode, roles, messages);
//    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
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
        return isActive();
    }
}
