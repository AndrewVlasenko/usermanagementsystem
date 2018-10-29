package by.project.usermanagementsystem.jsfui.controller;

import by.project.usermanagementsystem.dao.ClientDao;
import by.project.usermanagementsystem.domain.Client;
import by.project.usermanagementsystem.spring.controller.RedirectController;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.Date;

@ManagedBean
@ViewScoped
@Component
@Getter
@Setter
public class RegistrationController {

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private UserController userController;

    @Autowired
    private RedirectController redirectController;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private Client client;

    public void addClient() {
        client = new Client();
        showEditDialog();
    }

    public void save() throws IOException {
        isClientExists();
        setClientProperties();
        saveClient();
    }

    private void showEditDialog() {
        RequestContext.getCurrentInstance().execute("PF('dialogRequest').show()");
    }

    private void isClientExists() throws IOException {
        if (clientDao.findByName(client.getName()) != null) {
            redirectController.setErrorMessage("User with the same name exists");
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        }
    }

    private void setClientProperties(){
        client.setDateCreation(new Date());
        client.setEnabled((byte) 1);
        client.setPassword(passwordEncoder().encode(client.getPassword()));
    }

    private void saveClient() throws IOException {
        clientDao.save(client);
        redirectController.setSuccessMessage("user successfully registered");
        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
    }
}
