package by.project.usermanagementsystem.jsfui.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
@Component
@Getter
@Setter
public class UserController {

    private String username;

    private String password;

    public User getCurrentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public boolean hasRole(String role){
        return getCurrentUser().getAuthorities().stream().filter(x -> x.getAuthority().equals("ROLE_" + role)).count() > 0;
    }

    public String getLoginFailedMessage(){
        Object obj = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginFailed");
        if (obj == null) return "";
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("loginFailed");
        return "Incorrect login or password";
    }
}
