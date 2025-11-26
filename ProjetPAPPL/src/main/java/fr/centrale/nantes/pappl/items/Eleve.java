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
import jakarta.persistence.ManyToOne;
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
@Table(name = "eleve")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Eleve.findAll", query = "SELECT e FROM Eleve e"),
    @NamedQuery(name = "Eleve.findById", query = "SELECT e FROM Eleve e WHERE e.id = :id"),
    @NamedQuery(name = "Eleve.findByNom", query = "SELECT e FROM Eleve e WHERE e.nom = :nom"),
    @NamedQuery(name = "Eleve.findByPrenom", query = "SELECT e FROM Eleve e WHERE e.prenom = :prenom"),
    @NamedQuery(name = "Eleve.findByNationalite", query = "SELECT e FROM Eleve e WHERE e.nationalite = :nationalite"),
    @NamedQuery(name = "Eleve.findByEmail", query = "SELECT e FROM Eleve e WHERE e.email = :email"),
    @NamedQuery(name = "Eleve.findByTd", query = "SELECT e FROM Eleve e WHERE e.td = :td")})
public class Eleve implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "nom")
    private String nom;
    @Size(max = 2147483647)
    @Column(name = "prenom")
    private String prenom;
    @Size(max = 2147483647)
    @Column(name = "nationalite")
    private String nationalite;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 2147483647)
    @Column(name = "email")
    private String email;
    @Size(max = 2147483647)
    @Column(name = "td")
    private String td;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eleve")
    private Collection<ChoixEleveDomaine> choixEleveDomaineCollection;
    @JoinColumn(name = "annee", referencedColumnName = "annee")
    @ManyToOne
    private Annee annee;
    @JoinColumn(name = "numero_sujet", referencedColumnName = "numero")
    @ManyToOne
    private Sujet numeroSujet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eleve")
    private Collection<ChoixEleveTypo> choixEleveTypoCollection;

    public Eleve() {
    }

    public Eleve(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTd() {
        return td;
    }

    public void setTd(String td) {
        this.td = td;
    }

    @XmlTransient
    public Collection<ChoixEleveDomaine> getChoixEleveDomaineCollection() {
        return choixEleveDomaineCollection;
    }

    public void setChoixEleveDomaineCollection(Collection<ChoixEleveDomaine> choixEleveDomaineCollection) {
        this.choixEleveDomaineCollection = choixEleveDomaineCollection;
    }

    public Annee getAnnee() {
        return annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public Sujet getNumeroSujet() {
        return numeroSujet;
    }

    public void setNumeroSujet(Sujet numeroSujet) {
        this.numeroSujet = numeroSujet;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Eleve)) {
            return false;
        }
        Eleve other = (Eleve) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.centrale.nantes.pappl.items.Eleve[ id=" + id + " ]";
    }
    
}
