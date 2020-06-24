//package com.myweb.bookstore.service.impl;
//
//import com.myweb.bookstore.entity.Customer;
//import com.myweb.bookstore.entity.Role;
//import com.myweb.bookstore.repository.CustomerReponsitory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.HashSet;
//import java.util.Set;
//
//public class CustomerDetailServiceImpl implements UserDetailsService {
//    @Autowired
//    private CustomerReponsitory customerReponsitory;
//
//    @Override
//    @Transactional(readOnly = true)
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        Customer customer = customerReponsitory.findByEmail(email);
//
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        for (Role role: customer.getRoleList()){
//              grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//
//        }
//        return new org.springframework.security.core.userdetails.User(customer.getEmail(),customer.getPassword(),grantedAuthorities);
//    }
//
//}
