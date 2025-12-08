package com.douglasbuilder.orderapp.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.Period;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private String phoneNumber;
    private String address;
    private boolean enabled;
    private String fullName;
    private String memberSince; // "2 months ago", "1 year ago", etc.
    private String lastLoginAgo; // "5 minutes ago", "2 days ago", etc.
    
    public void setMemberSince(LocalDateTime createdAt) {
        this.memberSince = formatTimeAgo(createdAt);
    }
    
    public void setLastLoginAgo(LocalDateTime lastLogin) {
        this.lastLoginAgo = lastLogin != null ? formatTimeAgo(lastLogin) : "Never logged in";
    }
    
    private String formatTimeAgo(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(dateTime, now);
        
        long minutes = duration.toMinutes();
        long hours = duration.toHours();
        long days = duration.toDays();
        
        if (minutes < 1) return "Just now";
        if (minutes < 60) return minutes + (minutes == 1 ? " minute ago" : " minutes ago");
        if (hours < 24) return hours + (hours == 1 ? " hour ago" : " hours ago");
        if (days < 30) return days + (days == 1 ? " day ago" : " days ago");
        
        Period period = Period.between(dateTime.toLocalDate(), now.toLocalDate());
        long months = period.toTotalMonths();
        long years = period.getYears();
        
        if (months < 12) return months + (months == 1 ? " month ago" : " months ago");
        return years + (years == 1 ? " year ago" : " years ago");
    }
}