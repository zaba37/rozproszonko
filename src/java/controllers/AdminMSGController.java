/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entity.Ad;
import entity.Admindictionary;
import entity.Adminmesanges;
import entity.Users;
import facade.AdFacade;
import facade.AdmindictionaryFacade;
import facade.AdminmesangesFacade;
import facade.AdminsFacade;
import facade.UsersFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 *
 * @author zaba3
 */
@ManagedBean
@RequestScoped
public class AdminMSGController implements Serializable {

    @EJB
    private AdminmesangesFacade adminMSGFacade;

    @EJB
    private AdFacade adFacade;

    @EJB
    private UsersFacade usersFacade;

    @EJB
    private AdmindictionaryFacade dictionary;

    private List<Admindictionary> wordList;
    private List<Adminmesanges> msgList;
    private List<Ad> modAD;
    private List<Users> usersList;
    private Adminmesanges msg;
    private String dsc;
    private Integer id;
    private String title;
    private Ad modAd;
    private String word;
    private String modaAdId;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        List<Ad> tmp;

        msgList = adminMSGFacade.findAll();
        usersList = usersFacade.findAll();
        tmp = adFacade.findAll();
        modAD = new ArrayList();
        wordList = dictionary.findAll();

        for (Ad a : tmp) {
            if ("m".equals(a.getModerator())) {
                modAD.add(a);
            }
        }

        context.getExternalContext().getSessionMap().put("adminMSG", msgList);
        context.getExternalContext().getSessionMap().put("modAd", modAD);
        context.getExternalContext().getSessionMap().put("users", usersList);
        context.getExternalContext().getSessionMap().put("dictionary", wordList);
    }

    public String removeUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        String index = (String) context.getExternalContext().getRequestParameterMap().get("deleteUser");
        Users user = usersFacade.find(Integer.parseInt(index));

        for (Ad ad : user.getAdList()) {
            adFacade.remove(ad);
        }

        usersFacade.remove(user);
        context.getExternalContext().getSessionMap().put("users", usersFacade.findAll());

        return "/admin/successDeleteUser";
    }

    public List<Adminmesanges> getMsgList() {
        return msgList;
    }

    public String getModaAdId() {
        return modaAdId;
    }

    public void setModaAdId(String modaAdId) {
        this.modaAdId = modaAdId;
    }

    public void setMsgList(List<Adminmesanges> msgList) {
        this.msgList = msgList;
    }

    public String moderationAD() {
        FacesContext context = FacesContext.getCurrentInstance();
        String index = (String) context.getExternalContext().getRequestParameterMap().get("msgId");

        return "";
    }

    public void loadDataMSG() {
        FacesContext context = FacesContext.getCurrentInstance();
        String index = (String) context.getExternalContext().getRequestParameterMap().get("msgId");

        msg = adminMSGFacade.find(Integer.valueOf(index));
        title = msg.getTitle();
        dsc = msg.getDesctyption();
        id = Integer.valueOf(index);
    }

    public void loadModAd() {
        FacesContext context = FacesContext.getCurrentInstance();
        String index = (String) context.getExternalContext().getRequestParameterMap().get("modADid");

        modAd = adFacade.find(Integer.valueOf(index));
        title = modAd.getTitle();
        dsc = modAd.getDescryption();
        id = Integer.valueOf(index);
        context.getExternalContext().getSessionMap().put("modaAdId", index);

    }

    public String saveAdAfterMod() {
        FacesContext context = FacesContext.getCurrentInstance();
        String index = (String) context.getExternalContext().getSessionMap().get("modaAdId");
        modAd = adFacade.find(Integer.parseInt(index));
        modAd.setTitle(title);
        modAd.setDescryption(dsc);
        modAd.setModerator("b");
        adFacade.edit(modAd);

        List<Ad> tmp;

        msgList = adminMSGFacade.findAll();
        tmp = adFacade.findAll();
        modAD = new ArrayList();

        for (Ad a : tmp) {
            if ("m".equals(a.getModerator())) {
                modAD.add(a);
            }
        }

        context.getExternalContext().getSessionMap().put("adminMSG", msgList);
        context.getExternalContext().getSessionMap().put("modAd", modAD);

        return "/admin/successModeration";
    }

    public String editMSG() {
        msg = adminMSGFacade.find(id);
        msg.setTitle(title);
        msg.setDesctyption(dsc);
        adminMSGFacade.edit(msg);

        return "/admin/editMSGsuccess";
    }

    public String deleteMSG() {
        msg = adminMSGFacade.find(id);
        adminMSGFacade.remove(msg);

        return "/admin/deleteMSGsuccess";
    }

    public String createAdminMSG() {
        msg = new Adminmesanges();
        msg.setTitle(title);
        msg.setDesctyption(dsc);
        adminMSGFacade.create(msg);
        FacesContext context = FacesContext.getCurrentInstance();
        msgList = adminMSGFacade.findAll();
        context.getExternalContext().getSessionMap().put("adminMSG", msgList);

        return "/admin/successAddAdminMSG";
    }

    public Adminmesanges getMsg() {
        return msg;
    }

    public void setMsg(Adminmesanges msg) {
        this.msg = msg;
    }

    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String addWord() {
        Admindictionary tmp = new Admindictionary();
        tmp.setWord(word);
        dictionary.create(tmp);

        FacesContext context = FacesContext.getCurrentInstance();
        wordList = dictionary.findAll();
        context.getExternalContext().getSessionMap().put("dictionary", wordList);
        return "/admin/successAddToDictionary";
    }

    public String deleteWord() {
        FacesContext context = FacesContext.getCurrentInstance();
        String index = (String) context.getExternalContext().getRequestParameterMap().get("deleteWord");
        Admindictionary tmp = dictionary.find(Integer.parseInt(index));

        dictionary.remove(tmp);
        wordList = dictionary.findAll();
        context.getExternalContext().getSessionMap().put("dictionary", wordList);
        return "/admin/successDeleteFromDictionary";
    }

}
