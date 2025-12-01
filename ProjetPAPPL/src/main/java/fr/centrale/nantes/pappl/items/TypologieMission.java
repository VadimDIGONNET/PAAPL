/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.items;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author agach
 */
@Entity
@Table(name = "typologiemission")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Typologiemission.findAll", query = "SELECT t FROM Typologiemission t"),
    @NamedQuery(name = "Typologiemission.findByCode", query = "SELECT t FROM Typologiemission t WHERE t.code = :code"),
    @NamedQuery(name = "Typologiemission.findByNom", query = "SELECT t FROM Typologiemission t WHERE t.nom = :nom")})
public class TypologieMission implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "code")
    private Integer code;
    @Size(max = 2147483647)
    @Column(name = "nom")
    private String nom;
    @JoinTable(name = "contient", joinColumns = {
        @JoinColumn(name = "code", referencedColumnName = "code")}, inverseJoinColumns = {
        @JoinColumn(name = "numero", referencedColumnName = "numero")})
    @ManyToMany
    private Collection<Sujet> sujetCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "typologiemission")
    private Collection<ChoixEleveTypo> choixEleveTypoCollection;

    public TypologieMission() {
    }

    public TypologieMission(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @XmlTransient
    public Collection<Sujet> getSujetCollection() {
        return sujetCollection;
    }

    public void setSujetCollection(Collection<Sujet> sujetCollection) {
        this.sujetCollection = sujetCollection;
    }

    @XmlTransient
    public Collection<ChoixEleveTypo> getChoixEleveTypoCollection() {
        return choixEleveTypoCollection;
    }

    public void setChoixEleveTypoCollection(Collection<ChoixEleveTypo> choixEleveTypoCollection) {
        this.choixEleveTypoCollection = choixEleveTypoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (code != null ? code.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TypologieMission)) {
            return false;
        }
        TypologieMission other = (TypologieMission) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.centrale.nantes.pappl.items.Typologiemission[ code=" + code + " ]";
    }
    
}
