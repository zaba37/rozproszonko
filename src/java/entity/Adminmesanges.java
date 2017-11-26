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
@Table(name = "ADMINMESANGES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Adminmesanges.findAll", query = "SELECT a FROM Adminmesanges a"),
    @NamedQuery(name = "Adminmesanges.findById", query = "SELECT a FROM Adminmesanges a WHERE a.id = :id"),
    @NamedQuery(name = "Adminmesanges.findByTitle", query = "SELECT a FROM Adminmesanges a WHERE a.title = :title"),
    @NamedQuery(name = "Adminmesanges.findByDesctyption", query = "SELECT a FROM Adminmesanges a WHERE a.desctyption = :desctyption")})
public class Adminmesanges implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "TITLE")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "DESCTYPTION")
    private String desctyption;

    public Adminmesanges() {
    }

    public Adminmesanges(Integer id) {
        this.id = id;
    }

    public Adminmesanges(Integer id, String title, String desctyption) {
        this.id = id;
        this.title = title;
        this.desctyption = desctyption;
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

    public String getDesctyption() {
        return desctyption;
    }

    public void setDesctyption(String desctyption) {
        this.desctyption = desctyption;
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
        if (!(object instanceof Adminmesanges)) {
            return false;
        }
        Adminmesanges other = (Adminmesanges) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Adminmesanges[ id=" + id + " ]";
    }
    
}
