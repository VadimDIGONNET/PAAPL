/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.items;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.NoResultException;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PersistenceContext;
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
@Table(name = "correspondant")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Correspondant.findAll", query = "SELECT c FROM Correspondant c"),
    @NamedQuery(name = "Correspondant.findById", query = "SELECT c FROM Correspondant c WHERE c.id = :id"),
    @NamedQuery(name = "Correspondant.findByNom", query = "SELECT c FROM Correspondant c WHERE c.nom = :nom"),
    @NamedQuery(name = "Correspondant.findByPrenom", query = "SELECT c FROM Correspondant c WHERE c.prenom = :prenom"),
    @NamedQuery(name = "Correspondant.findByTel", query = "SELECT c FROM Correspondant c WHERE c.tel = :tel"),
    @NamedQuery(name = "Correspondant.findByEmail", query = "SELECT c FROM Correspondant c WHERE c.email = :email")})
public class Correspondant implements Serializable {

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
    @Column(name = "tel")
    private Integer tel;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 2147483647)
    @Column(name = "email")
    private String email;
    @ManyToMany(mappedBy = "correspondantCollection")
    private Collection<Annee> anneeCollection;
    @OneToMany(mappedBy = "idCorrespondant")
    private Collection<Sujet> sujetCollection;
    @JoinColumn(name = "entreprise", referencedColumnName = "nom")
    @ManyToOne
    private Entreprise entreprise;

    public Correspondant() {
    }

    public Correspondant(Integer id) {
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

    public Integer getTel() {
        return tel;
    }

    public void setTel(Integer tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlTransient
    public Collection<Annee> getAnneeCollection() {
        return anneeCollection;
    }

    public void setAnneeCollection(Collection<Annee> anneeCollection) {
        this.anneeCollection = anneeCollection;
    }

    @XmlTransient
    public Collection<Sujet> getSujetCollection() {
        return sujetCollection;
    }

    public void setSujetCollection(Collection<Sujet> sujetCollection) {
        this.sujetCollection = sujetCollection;
    }

    public Entreprise getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
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
        if (!(object instanceof Correspondant)) {
            return false;
        }
        Correspondant other = (Correspondant) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.centrale.nantes.pappl.items.Correspondant[ id=" + id + " ]";
    }
    
}
