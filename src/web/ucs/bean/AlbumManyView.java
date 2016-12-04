package web.ucs.bean;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import web.ucs.model.Album;
import web.ucs.model.Artista;
 

 
@ManagedBean
public class AlbumManyView {
     
    private List<String> selectedOptions;
    private List<Album> selectedThemes;
    private List<Album> themes;
     
    @ManagedProperty("#{albumService}")
    private AlbumService service;
     
    @PostConstruct
    public void init() {
        themes = service.getThemes();
    }
 
    public List<Album> getThemes() {
        return themes;
    }
 
    public void setService(AlbumService service) {
        this.service = service;
    }
 
    public List<String> getSelectedOptions() {
        return selectedOptions;
    }
 
    public void setSelectedOptions(List<String> selectedOptions) {
        this.selectedOptions = selectedOptions;
    }
 
    public List<Album> getSelectedThemes() {
        return selectedThemes;
    }
 
    public void setSelectedThemes(List<Album> selectedThemes) {
        this.selectedThemes = selectedThemes;
    }
}
