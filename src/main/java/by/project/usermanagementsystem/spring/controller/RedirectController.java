package by.project.usermanagementsystem.spring.controller;

import lombok.Getter;
import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@Log
@Getter
public class RedirectController {

    private String errorMessage;

    private String successMessage;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String baseUrlRedirect(HttpServletRequest request, HttpServletResponse httpServletResponse) {
        return "redirect:" + request.getRequestURL().append("index.xhtml").toString();
    }

    @RequestMapping("/success")
    public void loginPageRedirect(HttpServletRequest request, HttpServletResponse response, Authentication authResult) throws IOException{
        String role =  authResult.getAuthorities().toString();
        if(role.contains("ROLE_UNLOCKED")) redirect("", "/pages/client.xhtml", request, response);
        else if(role.contains("ROLE_LOCKED")) redirect("Account is locked", "/index.xhtml", request, response);
    }

    private void redirect(String message, String page, HttpServletRequest request, HttpServletResponse response) throws IOException {
        setErrorMessage(message);
        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + page));
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
}

