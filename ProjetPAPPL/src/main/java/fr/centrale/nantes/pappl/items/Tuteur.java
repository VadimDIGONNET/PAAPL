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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
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
@Table(name = "tuteur")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tuteur.findAll", query = "SELECT t FROM Tuteur t"),
    @NamedQuery(name = "Tuteur.findById", query = "SELECT t FROM Tuteur t WHERE t.id = :id"),
    @NamedQuery(name = "Tuteur.findByNom", query = "SELECT t FROM Tuteur t WHERE t.nom = :nom"),
    @NamedQuery(name = "Tuteur.findByPrenom", query = "SELECT t FROM Tuteur t WHERE t.prenom = :prenom"),
    @NamedQuery(name = "Tuteur.findByNbSujetSouhaite", query = "SELECT t FROM Tuteur t WHERE t.nbSujetSouhaite = :nbSujetSouhaite")})
public class Tuteur implements Serializable {

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
    @Column(name = "nb_sujet_souhaite")
    private Integer nbSujetSouhaite;
    @JoinTable(name = "choix_tuteur", joinColumns = {
        @JoinColumn(name = "id_tuteur", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "numero_sujet", referencedColumnName = "numero")})
    @ManyToMany
    private Collection<Sujet> sujetCollection;

    public Tuteur() {
    }

    public Tuteur(Integer id) {
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

    public Integer getNbSujetSouhaite() {
        return nbSujetSouhaite;
    }

    public void setNbSujetSouhaite(Integer nbSujetSouhaite) {
        this.nbSujetSouhaite = nbSujetSouhaite;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tuteur)) {
            return false;
        }
        Tuteur other = (Tuteur) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.centrale.nantes.pappl.items.Tuteur[ id=" + id + " ]";
    }
    
}
