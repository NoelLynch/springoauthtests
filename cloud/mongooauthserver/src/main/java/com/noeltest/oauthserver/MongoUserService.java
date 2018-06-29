package com.noeltest.oauthserver;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MongoUserService implements UserDetailsService {

    @Autowired
    private MongoTemplate mongo;
    
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return mongo.findOne(query(where("name").is(name)), AUser.class);
    }
    
    public void deleteAll() {
        mongo.dropCollection(AUser.class);
    }
    
    public void createUser(String name, String pass, List<String> auths) {
        AUser user = new AUser(name, pass);
        auths.forEach(user::addAuthority);
        mongo.save(user);
    }
    
    public static class AUser implements UserDetails {
        @Id
        private String name;
        private String pass;
        
        private List<UserGrantAuthority> authorities = new ArrayList<>();
        
        public AUser() {
            super();
        }

        public AUser(String name, String pass) {
            super();
            this.name = name;
            this.pass = pass;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        public void addAuthority(String authority) {
            authorities.add(new UserGrantAuthority(authority));
        }
        
        public void setAuthorities(List<UserGrantAuthority> authorities) {
            this.authorities = authorities;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        @Override
        public String getPassword() {
            return pass;
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
    
    public static class UserGrantAuthority implements GrantedAuthority {
        private String authority;

        public UserGrantAuthority() {
            super();
        }
        
        public UserGrantAuthority(String authority) {
            super();
            this.authority = authority;
        }

        @Override
        public String getAuthority() {
            return authority;
        }
        
        public void setAuthority(String authority) {
            this.authority = authority;
        }
    }
    
}
