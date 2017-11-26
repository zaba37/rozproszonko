/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import entity.Ad;
import entity.Admindictionary;
import entity.Users;
import facade.AdFacade;
import facade.AdmindictionaryFacade;
import facade.AdminmesangesFacade;
import facade.UsersFacade;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.xml.registry.infomodel.User;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author zaba3
 */
@ManagedBean
@RequestScoped
@ViewScoped
public class AdController {

    @EJB
    private UsersFacade users;

    @EJB
    private AdFacade ad;

    @EJB
    private AdminmesangesFacade adminMsg;

    @EJB
    private AdmindictionaryFacade dictionary;

    private ArrayList<Ad> allAd = new ArrayList();
    private Users owner;
    private ArrayList<Ad> searchAdList = new ArrayList();
    private Ad chosenAd;
    private Integer id;
    private String filePath;
    private boolean render;
    private String title;
    private String dsc;
    private UploadedFile file;
    private String searchValue;
    private String searchString;

    public AdController() {
    }

    @PostConstruct
    public void init() {
        allAd.addAll(ad.findAll());
        FacesContext context = FacesContext.getCurrentInstance();
        String login = (String) context.getExternalContext().getSessionMap().get("login");
        if (login != null && !"admin".equals(login)) {
            owner = users.FindByLogin(login);

            ArrayList<Ad> adList1 = ad.FindByOwnerId(owner.getId());
            owner.setAdList(adList1);

            users.edit(owner);
        }
    }

    public String getSearchString() {
        return searchString;
    }

    public boolean isRender() {
        return render;
    }

    public void setRender(boolean render) {
        this.render = render;
    }
    
    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String toModeration() {
        chosenAd = ad.find(id);
        chosenAd.setModerator("m");
        ad.edit(chosenAd);
        return "/ad/addToModeration";
    }

    public String search() {

        String[] wordFromSearch = searchString.split(" ");
        String option;
        ArrayList<Ad> adlistsearch = new ArrayList();
        adlistsearch.addAll(ad.findAll());
        ArrayList<Ad> result = new ArrayList();

        if (searchValue == null) {
            option = "or";
        } else {
            option = searchValue;
        }

        for (Ad ad : adlistsearch) {
            String[] mad = ad.getDescryption().split(" ");
            String[] tad = ad.getTitle().split(" ");
            ArrayList<String> madList = new ArrayList();
            ArrayList<String> tadList = new ArrayList();
            boolean andFlag = false;
            boolean orFlag = false;
            boolean notFlag = false;

            for (String m : mad) {
                madList.add(m);
            }

            for (String t : tad) {
                tadList.add(t);
            }

            if (option.equals("and")) {
                if (wordFromSearch.length == (madList.size() + tadList.size())) {
                    for (String w : wordFromSearch) {
                        madList.remove(w);
                        tadList.remove(w);
                    }

                    if (madList.isEmpty() && tadList.isEmpty()) {
                        andFlag = true;
                    }
                }
            }

            if (option.equals("not")) {
                for (String m : mad) {
                    for (String s : wordFromSearch) {
                        if (m.toLowerCase().equals(s.toLowerCase())) {
                            notFlag = true;
                        }
                    }
                }

                for (String t : tad) {
                    for (String s : wordFromSearch) {
                        if (t.toLowerCase().equals(s.toLowerCase())) {
                            notFlag = true;
                        }
                    }
                }
            }

            if (option.equals("or")) {
                for (String m : mad) {
                    for (String s : wordFromSearch) {
                        if (m.toLowerCase().equals(s.toLowerCase())) {
                            orFlag = true;
                        }
                    }
                }

                for (String t : tad) {
                    for (String s : wordFromSearch) {
                        if (t.toLowerCase().equals(s.toLowerCase())) {
                            orFlag = true;
                        }
                    }
                }
            }

            if (option.equals("and") && andFlag) {
                result.add(ad);
            }

            if (option.equals("or") && orFlag) {
                result.add(ad);
            }

            if (option.equals("not") && !notFlag) {
                result.add(ad);
            }
        }

        allAd = new ArrayList();
        allAd.addAll(result);

        return "/ad/showAllAd";
    }

    public Users yourAdList() {
        FacesContext context = FacesContext.getCurrentInstance();
        String login = (String) context.getExternalContext().getSessionMap().get("login");
        owner = users.FindByLogin(login);

        ArrayList<Ad> adList1 = ad.FindByOwnerId(owner.getId());
        owner.setAdList(adList1);

        users.edit(owner);

        return owner;
    }

    public ArrayList<Ad> getAllAd() {
        return allAd;
    }

    public void setAllAd(ArrayList<Ad> allAd) {
        this.allAd = allAd;
    }

    public ArrayList<Ad> getSearchAdList() {
        return searchAdList;
    }

    public void setSearchAdList(ArrayList<Ad> searchAdList) {
        this.searchAdList = searchAdList;
    }

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public Ad getChosenAd() {
        return chosenAd;
    }

    public void setChosenAd(Ad chosenAd) {
        this.chosenAd = chosenAd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void loadChosenAd() {
        FacesContext context = FacesContext.getCurrentInstance();
        String index = (String) context.getExternalContext().getRequestParameterMap().get("auctinId");

        chosenAd = ad.find(Integer.valueOf(index));
        chosenAd.setVisit(chosenAd.getVisit() + 1);
        ad.edit(chosenAd);
        title = chosenAd.getTitle();
        dsc = chosenAd.getDescryption();
        id = Integer.valueOf(index);
        
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        render = "m".equals(chosenAd.getModerator());

        String applicationPath = servletContext.getRealPath("/");// request.getServlet
        filePath = chosenAd.getPathtofile();
        context.getExternalContext().getSessionMap().put("chosenAd", chosenAd);
    }

//    public String getFilePath(){
//        return chosenAd.getId() + chosenAd.getPathtofile();
//    }
//    
    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDsc() {
        return dsc;
    }

    public void setDsc(String dsc) {
        this.dsc = dsc;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public String createNewAd() throws IOException {
        boolean badWord = false;
        Ad add = new Ad();
        Users user = new Users();
        FacesContext context = FacesContext.getCurrentInstance();
        String login = (String) context.getExternalContext().getSessionMap().get("login");
        user = users.FindByLogin(login);

        ArrayList<Admindictionary> wordList = new ArrayList();
        wordList.addAll(dictionary.findAll());

        String[] wordFromDescryption = dsc.split(" ");
        String[] wordFromTitle = title.split(" ");
        ArrayList<String> banedWord = new ArrayList();

        for (Admindictionary word : wordList) {
            for (int i = 0; i < wordFromDescryption.length; i++) {
                if ((word.getWord().toLowerCase()).equals(wordFromDescryption[i].toLowerCase())) {
                    banedWord.add(wordFromDescryption[i].toLowerCase());
                }
            }

            for (int j = 0; j < wordFromTitle.length; j++) {
                if ((word.getWord().toLowerCase()).equals(wordFromTitle[j].toLowerCase())) {
                    banedWord.add(wordFromTitle[j].toLowerCase());
                }
            }
        }

        if (banedWord.isEmpty()) {
            add.setOwner(user);
            add.setTitle(title);
            add.setDescryption(dsc);
            add.setVisit(0);

            ad.create(add);
            //Users user10 = users.FindByLogin(user.getLogin());
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

            String applicationPath = servletContext.getRealPath("/");// request.getServletContext().getRealPath("");

            File fileSaveDir = new File(applicationPath + "/" + add.getId());
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
            }

            if (!file.getFileName().isEmpty()) {
                InputStream ip;
                ip = file.getInputstream();
                String fileName = extractFileName(file.getFileName());

                FileOutputStream fos = new FileOutputStream(applicationPath + "/" + add.getId() + "/" + fileName);
                int len;
                int size = 1024;
                byte[] buf;
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                buf = new byte[size];
                while ((len = ip.read(buf, 0, size)) != -1) {
                    bos.write(buf, 0, len);
                }
                buf = bos.toByteArray();

                fos.write(buf);
                add.setPathtofile(file.getFileName());
                fos.close();
            }

            ad.edit(add);
            return "/ad/successCreateNewAd";
        } else {
            return "/ad/banedWordDetected";
        }
    }

    private String extractFileName(String filePath) {
        int index = filePath.lastIndexOf("\\");
        String name = filePath.substring(index + 1);
        return name;
    }

    public String editAD() {
        chosenAd = ad.find(id);
        chosenAd.setTitle(title);
        chosenAd.setDescryption(dsc);
        ad.edit(chosenAd);
        return "/ad/editSuccess";
    }

    public void deleteAd() {
        FacesContext context = FacesContext.getCurrentInstance();
        String index = (String) context.getExternalContext().getRequestParameterMap().get("auctinId");
        String login = (String) context.getExternalContext().getSessionMap().get("login");
        owner = users.FindByLogin(login);
        chosenAd = ad.find(Integer.valueOf(index));

        ArrayList<Ad> adList1 = ad.FindByOwnerId(owner.getId());
        adList1.remove(chosenAd);
        owner.setAdList(adList1);

        users.edit(owner);
        ad.remove(chosenAd);
    }
}
