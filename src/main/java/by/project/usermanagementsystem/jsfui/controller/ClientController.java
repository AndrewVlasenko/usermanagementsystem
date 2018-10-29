package by.project.usermanagementsystem.jsfui.controller;

import by.project.usermanagementsystem.dao.ClientDao;
import by.project.usermanagementsystem.domain.Client;
import by.project.usermanagementsystem.spring.controller.RedirectController;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.ManagedBean;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ManagedBean
@SessionScoped
@Component
@Getter
@Setter
@PreAuthorize("hasRole('ROLE_UNLOCKED')")
public class ClientController implements Serializable {

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private UserController userController;

    @Autowired
    private RedirectController redirectController;


    private List<Client> selectedClientList;

    private List<Client> clientList;

    private List<Client> lockedOrDeletedClientList;


    public void delete() {
        lockedOrDeletedClientList = selectedClientList;
        clientDao.delete(selectedClientList);
    }

    @Transactional
    public List<Client> getAll(){
        clientDao.updateLastEntrance(new Date(), userController.getCurrentUser().getUsername());
        clientList = clientDao.getAll();
        return clientList;
    }

    @Transactional
    public void unlock(){
        for (Client client : selectedClientList) {
            clientDao.changeRole("ROLE_UNLOCKED", client.getId());
        }
    }

    @Transactional
    public void lock(){
        lockedOrDeletedClientList = selectedClientList;
        for (Client client : selectedClientList) {
            clientDao.changeRole("ROLE_LOCKED", client.getId());
        }
    }

    public void isCurrentUserLock() throws IOException {
        redirect("Account is locked");
    }

    public void isCurrentUserDelete() throws IOException {
       redirect("Account deleted");
    }

    private void redirect(String message) throws IOException {
        if(lockedOrDeletedClientList.stream().anyMatch(x -> x.getName().equals(userController.getCurrentUser().getUsername()))) {
            redirectController.setErrorMessage(message);
            FacesContext.getCurrentInstance().getExternalContext().redirect("../logout");
        }
    }

    public int getNumberUser(){
        return clientList.size();
    }

    public int getNumberSelectedUser(){
        if(selectedClientList == null)return 0;
        return selectedClientList.size();
    }

    public String getStatus(String role){
        return role.substring(5).toLowerCase();
    }

    public boolean isExistDate(Date date){
        return (date != null);
    }
}
