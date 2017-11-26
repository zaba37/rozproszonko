package themes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

 
@ManagedBean(name="themeService", eager = true)
@ApplicationScoped
@SessionScoped
public class ThemeService {
     
    private List<Theme> themes;
     
    @PostConstruct
    public void init() {
        themes = new ArrayList<>();
        themes.add(new Theme(0, "Afterdark", "afterdark"));
        themes.add(new Theme(1, "Cupertino", "cupertino"));       
    }
     
    public List<Theme> getThemes() {
        return themes;
    } 
}
