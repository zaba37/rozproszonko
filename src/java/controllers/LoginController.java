/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entity.Admins;
import entity.Users;
import facade.AdminsFacade;
import facade.UsersFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author zaba3
 */
@ManagedBean(name = "loginController")
@RequestScoped
public class LoginController {

    private String username;
    private String password;
    private String email;
    private String number;
    private Users user;
    private Integer userId;

    @EJB
    private UsersFacade userFacade;

    @EJB
    private AdminsFacade adminsFacade;

    public String login() {
        Users user = userFacade.findByLoginAndPassword(username, password);
        Admins admin = adminsFacade.findByLoginAndPassword(username, password);
        FacesContext context = FacesContext.getCurrentInstance();

        if (admin != null) {
            user = null;
            context.getExternalContext().getSessionMap().put("login", admin.getLogin());
            context.getExternalContext().getSessionMap().put("pageSize", 6);
            return "/login/successAdminLogin";
        } else if (user != null) {
            context.getExternalContext().getSessionMap().put("login", user.getLogin());

            List<Admins> a = adminsFacade.findAll();
            context.getExternalContext().getSessionMap().put("numberOfFiles", a.get(0).getNumberoffiles());
            context.getExternalContext().getSessionMap().put("style", user.getNumberofstyle());
            context.getExternalContext().getSessionMap().put("sizeOfFile", a.get(0).getSizeoffile());
            context.getExternalContext().getSessionMap().put("pageSize", user.getNumberadforonepage());
            return "/login/successLogin";
        } else {
            context.addMessage(null, new FacesMessage("Unknown login, try again"));
            username = null;
            password = null;
            return null;
        }
    }

    public void loadUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        String login = (String) context.getExternalContext().getSessionMap().get("login");
        user = userFacade.FindByLogin(login);
        username = user.getLogin();
        password = user.getPassword();
        email = user.getEmail();
        number = String.valueOf(user.getNumberadforonepage());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/login/logout";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String editUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        String login = (String) context.getExternalContext().getSessionMap().get("login");
        user = userFacade.FindByLogin(login);
        user.setEmail(email);
        user.setLogin(username);
        user.setPassword(password);
        user.setNumberadforonepage(Integer.valueOf(number));
        context.getExternalContext().getSessionMap().put("login", user.getLogin());
        context.getExternalContext().getSessionMap().put("pageSize", user.getNumberadforonepage());
        userFacade.edit(user);
        return "/login/successEditUserData";
    }
}
