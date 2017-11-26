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
@Table(name = "ADMINS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Admins.findAll", query = "SELECT a FROM Admins a"),
    @NamedQuery(name = "Admins.findById", query = "SELECT a FROM Admins a WHERE a.id = :id"),
    @NamedQuery(name = "Admins.findByLoginAndPassword", query = "SELECT u FROM Admins u WHERE u.login = :login AND u.password = :password"),
    @NamedQuery(name = "Admins.findByLogin", query = "SELECT a FROM Admins a WHERE a.login = :login"),
    @NamedQuery(name = "Admins.findByPassword", query = "SELECT a FROM Admins a WHERE a.password = :password"),
    @NamedQuery(name = "Admins.findByEmail", query = "SELECT a FROM Admins a WHERE a.email = :email"),
    @NamedQuery(name = "Admins.findByNumberoffiles", query = "SELECT a FROM Admins a WHERE a.numberoffiles = :numberoffiles"),
    @NamedQuery(name = "Admins.findBySizeoffile", query = "SELECT a FROM Admins a WHERE a.sizeoffile = :sizeoffile")})
public class Admins implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "LOGIN")
    private String login;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "PASSWORD")
    private String password;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "EMAIL")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NUMBEROFFILES")
    private int numberoffiles;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SIZEOFFILE")
    private int sizeoffile;

    public Admins() {
    }

    public Admins(Integer id) {
        this.id = id;
    }

    public Admins(Integer id, String login, String password, String email, int numberoffiles, int sizeoffile) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.email = email;
        this.numberoffiles = numberoffiles;
        this.sizeoffile = sizeoffile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumberoffiles() {
        return numberoffiles;
    }

    public void setNumberoffiles(int numberoffiles) {
        this.numberoffiles = numberoffiles;
    }

    public int getSizeoffile() {
        return sizeoffile;
    }

    public void setSizeoffile(int sizeoffile) {
        this.sizeoffile = sizeoffile;
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
        if (!(object instanceof Admins)) {
            return false;
        }
        Admins other = (Admins) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Admins[ id=" + id + " ]";
    }

}
