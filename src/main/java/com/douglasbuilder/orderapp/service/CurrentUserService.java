package com.douglasbuilder.orderapp.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
    public String getCurrentUserId(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
