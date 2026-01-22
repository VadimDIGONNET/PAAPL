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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author vadimdigonnet
 */
@Entity
@Table(name = "description_sujet")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DescriptionSujet.findAllWithSujet" , query = "SELECT ds.sujet , ds FROM DescriptionSujet ds"),
    @NamedQuery(name = "DescriptionSujet.findAll", query = "SELECT d FROM DescriptionSujet d"),
    @NamedQuery(name = "DescriptionSujet.findByNumero", query = "SELECT d FROM DescriptionSujet d WHERE d.numero = :numero"),
    @NamedQuery(name = "DescriptionSujet.findByContexte", query = "SELECT d FROM DescriptionSujet d WHERE d.contexte = :contexte"),
    @NamedQuery(name = "DescriptionSujet.findByTravailRealiser", query = "SELECT d FROM DescriptionSujet d WHERE d.travailRealiser = :travailRealiser"),
    @NamedQuery(name = "DescriptionSujet.findByLivrable", query = "SELECT d FROM DescriptionSujet d WHERE d.livrable = :livrable")})
public class DescriptionSujet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "numero")
    private Integer numero;
    @Size(max = 2147483647)
    @Column(name = "contexte")
    private String contexte;
    @Size(max = 2147483647)
    @Column(name = "travail_realiser")
    private String travailRealiser;
    @Size(max = 2147483647)
    @Column(name = "livrable")
    private String livrable;
    @JoinColumn(name = "nom_entreprise", referencedColumnName = "nom")
    @ManyToOne
    private Entreprise nomEntreprise;
    @JoinColumn(name = "numero", referencedColumnName = "numero", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Sujet sujet;

    public DescriptionSujet() {
    }

    public DescriptionSujet(Integer numero) {
        this.numero = numero;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getContexte() {
        return contexte;
    }

    public void setContexte(String contexte) {
        this.contexte = contexte;
    }

    public String getTravailRealiser() {
        return travailRealiser;
    }

    public void setTravailRealiser(String travailRealiser) {
        this.travailRealiser = travailRealiser;
    }

    public String getLivrable() {
        return livrable;
    }

    public void setLivrable(String livrable) {
        this.livrable = livrable;
    }

    public Entreprise getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(Entreprise nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public Sujet getSujet() {
        return sujet;
    }

    public void setSujet(Sujet sujet) {
        this.sujet = sujet;
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
        if (!(object instanceof DescriptionSujet)) {
            return false;
        }
        DescriptionSujet other = (DescriptionSujet) object;
        if ((this.numero == null && other.numero != null) || (this.numero != null && !this.numero.equals(other.numero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.centrale.nantes.pappl.items.DescriptionSujet[ numero=" + numero + " ]";
    }
    
}
