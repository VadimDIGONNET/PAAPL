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
 * @author vadimdigonnet
 */
@Entity
@Table(name = "domaine")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Domaine.findAll", query = "SELECT d FROM Domaine d"),
    @NamedQuery(name = "Domaine.findByCode", query = "SELECT d FROM Domaine d WHERE d.code = :code"),
    @NamedQuery(name = "Domaine.findByNom", query = "SELECT d FROM Domaine d WHERE d.nom = :nom")})
public class Domaine implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "code")
    private Integer code;
    @Size(max = 2147483647)
    @Column(name = "nom")
    private String nom;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "code")
    private Collection<Appartient> appartientCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codeDomaine")
    private Collection<ChoixEleveDomaine> choixEleveDomaineCollection;

    public Domaine() {
    }

    public Domaine(Integer code) {
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
    public Collection<Appartient> getAppartientCollection() {
        return appartientCollection;
    }

    public void setAppartientCollection(Collection<Appartient> appartientCollection) {
        this.appartientCollection = appartientCollection;
    }

    @XmlTransient
    public Collection<ChoixEleveDomaine> getChoixEleveDomaineCollection() {
        return choixEleveDomaineCollection;
    }

    public void setChoixEleveDomaineCollection(Collection<ChoixEleveDomaine> choixEleveDomaineCollection) {
        this.choixEleveDomaineCollection = choixEleveDomaineCollection;
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
        if (!(object instanceof Domaine)) {
            return false;
        }
        Domaine other = (Domaine) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.centrale.nantes.pappl.items.Domaine[ code=" + code + " ]";
    }
    
}
