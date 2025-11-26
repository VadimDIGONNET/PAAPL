/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.items;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author agach
 */
@Entity
@Table(name = "annee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Annee.findAll", query = "SELECT a FROM Annee a"),
    @NamedQuery(name = "Annee.findByAnnee", query = "SELECT a FROM Annee a WHERE a.annee = :annee")})
public class Annee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "annee")
    private Integer annee;
    @JoinTable(name = "annee_activite", joinColumns = {
        @JoinColumn(name = "annee", referencedColumnName = "annee")}, inverseJoinColumns = {
        @JoinColumn(name = "id_correspondant", referencedColumnName = "id")})
    @ManyToMany
    private Collection<Correspondant> correspondantCollection;
    @OneToMany(mappedBy = "annee")
    private Collection<Eleve> eleveCollection;
    @OneToMany(mappedBy = "annee")
    private Collection<Sujet> sujetCollection;

    public Annee() {
    }

    public Annee(Integer annee) {
        this.annee = annee;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    @XmlTransient
    public Collection<Correspondant> getCorrespondantCollection() {
        return correspondantCollection;
    }

    public void setCorrespondantCollection(Collection<Correspondant> correspondantCollection) {
        this.correspondantCollection = correspondantCollection;
    }

    @XmlTransient
    public Collection<Eleve> getEleveCollection() {
        return eleveCollection;
    }

    public void setEleveCollection(Collection<Eleve> eleveCollection) {
        this.eleveCollection = eleveCollection;
    }

    @XmlTransient
    public Collection<Sujet> getSujetCollection() {
        return sujetCollection;
    }

    public void setSujetCollection(Collection<Sujet> sujetCollection) {
        this.sujetCollection = sujetCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (annee != null ? annee.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Annee)) {
            return false;
        }
        Annee other = (Annee) object;
        if ((this.annee == null && other.annee != null) || (this.annee != null && !this.annee.equals(other.annee))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.centrale.nantes.pappl.items.Annee[ annee=" + annee + " ]";
    }
    
}
