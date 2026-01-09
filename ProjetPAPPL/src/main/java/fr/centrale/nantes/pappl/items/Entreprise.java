/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.items;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;

/**
 *
 * @author vadimdigonnet
 */
@Entity
@Table(name = "entreprise")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Entreprise.findAll", query = "SELECT e FROM Entreprise e"),
    @NamedQuery(name = "Entreprise.findByNom", query = "SELECT e FROM Entreprise e WHERE e.nom = :nom"),
    @NamedQuery(name = "Entreprise.findByAdresse", query = "SELECT e FROM Entreprise e WHERE e.adresse = :adresse"),
    @NamedQuery(name = "Entreprise.findByCodePostal", query = "SELECT e FROM Entreprise e WHERE e.codePostal = :codePostal"),
    @NamedQuery(name = "Entreprise.findByTel", query = "SELECT e FROM Entreprise e WHERE e.tel = :tel"),
    @NamedQuery(name = "Entreprise.findBySiteWeb", query = "SELECT e FROM Entreprise e WHERE e.siteWeb = :siteWeb"),
    @NamedQuery(name = "Entreprise.findByDescription", query = "SELECT e FROM Entreprise e WHERE e.description = :description")})
public class Entreprise implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nom")
    private String nom;
    @Size(max = 2147483647)
    @Column(name = "adresse")
    private String adresse;
    @Column(name = "code_postal")
    private Integer codePostal;
    @Column(name = "tel")
    private Integer tel;
    @Size(max = 2147483647)
    @Column(name = "site_web")
    private String siteWeb;
    @Size(max = 2147483647)
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "nomEntreprise")
    private Collection<DescriptionSujet> descriptionSujetCollection;
    @OneToMany(mappedBy = "entreprise")
    private Collection<Correspondant> correspondantCollection;

    public Entreprise() {
    }

    public Entreprise(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Integer getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(Integer codePostal) {
        this.codePostal = codePostal;
    }

    public Integer getTel() {
        return tel;
    }

    public void setTel(Integer tel) {
        this.tel = tel;
    }

    public String getSiteWeb() {
        return siteWeb;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<DescriptionSujet> getDescriptionSujetCollection() {
        return descriptionSujetCollection;
    }

    public void setDescriptionSujetCollection(Collection<DescriptionSujet> descriptionSujetCollection) {
        this.descriptionSujetCollection = descriptionSujetCollection;
    }

    @XmlTransient
    public Collection<Correspondant> getCorrespondantCollection() {
        return correspondantCollection;
    }

    public void setCorrespondantCollection(Collection<Correspondant> correspondantCollection) {
        this.correspondantCollection = correspondantCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nom != null ? nom.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entreprise)) {
            return false;
        }
        Entreprise other = (Entreprise) object;
        if ((this.nom == null && other.nom != null) || (this.nom != null && !this.nom.equals(other.nom))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.centrale.nantes.pappl.items.Entreprise[ nom=" + nom + " ]";
    }
    
    public int compareTo(Object object) {
        if (object == null) {
            return 1;
        } else if (! (object instanceof Entreprise)){
            return 1;
        }
        
        Entreprise itemId = (Entreprise) object;
        if (this.getNom().toUpperCase().equals(itemId.getNom().toUpperCase())) {
            return this.getAdresse().toUpperCase().compareTo(itemId.getAdresse().toUpperCase());
        } else {
            return this.getNom().toUpperCase().compareTo(itemId.getNom().toUpperCase());
        }
    }

    public static Comparator<Entreprise> getComparator() {
        return new Comparator<Entreprise>() {
            @Override
            public int compare(Entreprise object1, Entreprise object2) {
                if (object1 == null) {
                    return -1;
                } else {
                    return object1.compareTo(object2);
                }
            }
        };
    }
}
