/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entity.Admins;
import entity.Users;
import facade.AdFacade;
import facade.AdminmesangesFacade;
import facade.AdminsFacade;
import facade.UsersFacade;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author zaba3
 */
@ManagedBean
@ViewScoped
public class NewUserController {

    @EJB
    private UsersFacade usersFacade;

    @EJB
    private AdminsFacade adminFacade;

    private String username;
    private String email;
    private String password1;

    public NewUserController() {
    }

    public String create() {
        boolean checkLoginAvilable = false;
        boolean checkPasswordCorrect = false;
        boolean checkEmailAvilable = false;
        boolean checkEmpty = false;
        FacesContext context = FacesContext.getCurrentInstance();

        ArrayList<Users> users = new ArrayList();
        ArrayList<Admins> admins = new ArrayList();
        admins.addAll(adminFacade.findAll());
        users.addAll(usersFacade.findAll());

        if (!username.equals("") && !email.equals("") && !password1.equals("")) {
            for (Users u : users) {
                if (u.getLogin().equals(username)) {
                    checkLoginAvilable = true;
                    break;
                }

                if (u.getEmail().equals(email)) {
                    checkEmailAvilable = true;
                    break;
                }
            }

            for (Admins a : admins) {
                if (a.getLogin().equals(username)) {
                    checkLoginAvilable = true;
                    break;
                }
            }

        } else {
            if (username.equals("")) {
                context.addMessage(null, new FacesMessage("Login nie może być pusty"));
            }

            if (email.equals("")) {
                context.addMessage(null, new FacesMessage("Email nie może być pusty"));
            }

            if (password1.equals("")) {
                context.addMessage(null, new FacesMessage("Hasło nie może być puste"));
            }

            checkEmpty = true;
        }

        if (!checkEmpty) {
            if (checkLoginAvilable) {
                context.addMessage(null, new FacesMessage("Login juz istnieje"));
                return null;
            } else if (checkPasswordCorrect) {
                context.addMessage(null, new FacesMessage("Hasło niepoprawne"));
                return null;
            } else if (checkEmailAvilable) {
                context.addMessage(null, new FacesMessage("Email juz zostal uzyty"));
                return null;
            } else {              
                Users user = new Users();
                user.setEmail(email);
                user.setLogin(username);
                user.setPassword(password1);
                user.setNumberadforonepage(3);
                usersFacade.create(user);
                return "/newUser/successCreate";
            }
        } else {
            return null;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }
    
    
}
