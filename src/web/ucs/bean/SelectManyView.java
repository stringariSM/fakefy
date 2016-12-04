package web.ucs.bean;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import web.ucs.model.Artista;
 

 
@ManagedBean
public class SelectManyView {
     
    private List<String> selectedOptions;
    private List<Artista> selectedThemes;
    private List<Artista> themes;
     
    @ManagedProperty("#{themeService}")
    private ThemeService service;
     
    @PostConstruct
    public void init() {
        themes = service.getThemes();
    }
 
    public List<Artista> getThemes() {
        return themes;
    }
 
    public void setService(ThemeService service) {
        this.service = service;
    }
 
    public List<String> getSelectedOptions() {
        return selectedOptions;
    }
 
    public void setSelectedOptions(List<String> selectedOptions) {
        this.selectedOptions = selectedOptions;
    }
 
    public List<Artista> getSelectedThemes() {
        return selectedThemes;
    }
 
    public void setSelectedThemes(List<Artista> selectedThemes) {
        this.selectedThemes = selectedThemes;
    }
}