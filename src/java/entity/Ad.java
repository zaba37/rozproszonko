/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author zaba3
 */
@Entity
@Table(name = "AD")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ad.findAll", query = "SELECT a FROM Ad a"),
    @NamedQuery(name = "Ad.findById", query = "SELECT a FROM Ad a WHERE a.id = :id"),
    @NamedQuery(name = "Ad.findByOwnerId", query = "SELECT a FROM Ad a WHERE a.owner.id = :id"),
    @NamedQuery(name = "Ad.findByTitle", query = "SELECT a FROM Ad a WHERE a.title = :title"),
    @NamedQuery(name = "Ad.findByDescryption", query = "SELECT a FROM Ad a WHERE a.descryption = :descryption"),
    @NamedQuery(name = "Ad.findByVisit", query = "SELECT a FROM Ad a WHERE a.visit = :visit"),
    @NamedQuery(name = "Ad.findByModerator", query = "SELECT a FROM Ad a WHERE a.moderator = :moderator"),
    @NamedQuery(name = "Ad.findByPathtofile", query = "SELECT a FROM Ad a WHERE a.pathtofile = :pathtofile")})
public class Ad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "TITLE")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "DESCRYPTION")
    private String descryption;
    @Basic(optional = false)
    @NotNull
    @Column(name = "VISIT")
    private int visit;
    @Size(max = 1)
    @Column(name = "MODERATOR")
    private String moderator;
    @Size(max = 1000)
    @Column(name = "PATHTOFILE")
    private String pathtofile;
    @JoinColumn(name = "OWNER", referencedColumnName = "ID")
    @ManyToOne
    private Users owner;

    public Ad() {
    }

    public Ad(Integer id) {
        this.id = id;
    }

    public Ad(Integer id, String title, String descryption, int visit) {
        this.id = id;
        this.title = title;
        this.descryption = descryption;
        this.visit = visit;
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

    public String getDescryption() {
        return descryption;
    }

    public void setDescryption(String descryption) {
        this.descryption = descryption;
    }

    public int getVisit() {
        return visit;
    }

    public void setVisit(int visit) {
        this.visit = visit;
    }

    public String getModerator() {
        return moderator;
    }

    public void setModerator(String moderator) {
        this.moderator = moderator;
    }

    public String getPathtofile() {
        return pathtofile;
    }

    public void setPathtofile(String pathtofile) {
        this.pathtofile = pathtofile;
    }

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ad)) {
            return false;
        }
        Ad other = (Ad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Ad[ id=" + id + " ]";
    }

}
