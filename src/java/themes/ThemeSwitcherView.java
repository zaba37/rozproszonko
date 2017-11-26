/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package themes;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author zaba3
 */
@ManagedBean(name = "themeSwitcherView")
@SessionScoped
public class ThemeSwitcherView {

    private List<Theme> themes;
    private Theme current;

    @ManagedProperty("#{themeService}")
    private ThemeService service;

    @PostConstruct
    public void init() {
        themes = service.getThemes();
        current = themes.get(0);
    }

    public List<Theme> getThemes() {
        return themes;
    }

    public void setService(ThemeService service) {
        this.service = service;
    }

    public Theme getCurrent() {
        return current;
    }

    public void setCurrent(Theme current) {
        this.current = current;
    }

    public void saveTheme() {

    }

    public void chanegeCupertino() {
        current = themes.get(1);
    }

    public void changeDark() {
        current = themes.get(0);
    }
}
