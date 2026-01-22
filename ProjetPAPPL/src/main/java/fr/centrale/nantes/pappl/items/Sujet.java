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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "sujet")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sujet.findAll", query = "SELECT s FROM Sujet s"),
    @NamedQuery(name = "Sujet.findByIntitule", query = "SELECT s FROM Sujet s WHERE s.intitule = :intitule"),
    @NamedQuery(name = "Sujet.findByNote", query = "SELECT s FROM Sujet s WHERE s.note = :note")})
public class Sujet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "numero")
    private Integer numero;
    @Size(max = 2147483647)
    @Column(name = "intitule")
    private String intitule;
    @Column(name = "note")
    private Integer note;
    @Column(name = "numero_du_sujet")
    private Integer numeroDuSujet;
    @ManyToMany(mappedBy = "sujetCollection")
    private Collection<Tuteur> tuteurCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "numero")
    private Collection<Appartient> appartientCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "numero")
    private Collection<Contient> contientCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "sujet")
    private DescriptionSujet descriptionSujet;
    @OneToMany(mappedBy = "numeroSujet")
    private Collection<Eleve> eleveCollection;
    @JoinColumn(name = "annee", referencedColumnName = "annee")
    @ManyToOne
    private Annee annee;
    @JoinColumn(name = "id_correspondant", referencedColumnName = "id")
    @ManyToOne
    private Correspondant idCorrespondant;

    public Sujet() {
    }

    public Sujet(Integer numero) {
        this.numero = numero;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }
    
    public Integer getNumeroDuSujet() {
        return numeroDuSujet;
    }

    public void setNumeroDuSujet(Integer numeroDuSujet) {
        this.numeroDuSujet = numeroDuSujet;
    }

    @XmlTransient
    public Collection<Tuteur> getTuteurCollection() {
        return tuteurCollection;
    }

    public void setTuteurCollection(Collection<Tuteur> tuteurCollection) {
        this.tuteurCollection = tuteurCollection;
    }

    @XmlTransient
    public Collection<Appartient> getAppartientCollection() {
        return appartientCollection;
    }

    public void setAppartientCollection(Collection<Appartient> appartientCollection) {
        this.appartientCollection = appartientCollection;
    }

    @XmlTransient
    public Collection<Contient> getContientCollection() {
        return contientCollection;
    }

    public void setContientCollection(Collection<Contient> contientCollection) {
        this.contientCollection = contientCollection;
    }

    public DescriptionSujet getDescriptionSujet() {
        return descriptionSujet;
    }

    public void setDescriptionSujet(DescriptionSujet descriptionSujet) {
        this.descriptionSujet = descriptionSujet;
    }

    @XmlTransient
    public Collection<Eleve> getEleveCollection() {
        return eleveCollection;
    }

    public void setEleveCollection(Collection<Eleve> eleveCollection) {
        this.eleveCollection = eleveCollection;
    }

    public Annee getAnnee() {
        return annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public Correspondant getIdCorrespondant() {
        return idCorrespondant;
    }

    public void setIdCorrespondant(Correspondant idCorrespondant) {
        this.idCorrespondant = idCorrespondant;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numero != null ? numero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sujet)) {
            return false;
        }
        Sujet other = (Sujet) object;
        if ((this.numero == null && other.numero != null) || (this.numero != null && !this.numero.equals(other.numero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.centrale.nantes.pappl.items.Sujet[ numero=" + numero + " ]";
    }
    
}
