/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.items;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author agach
 */
@Entity
@Table(name = "choix_eleve_domaine")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChoixEleveDomaine.findAll", query = "SELECT c FROM ChoixEleveDomaine c"),
    @NamedQuery(name = "ChoixEleveDomaine.findByIdEleve", query = "SELECT c FROM ChoixEleveDomaine c WHERE c.choixEleveDomainePK.idEleve = :idEleve"),
    @NamedQuery(name = "ChoixEleveDomaine.findByCodeDomaine", query = "SELECT c FROM ChoixEleveDomaine c WHERE c.choixEleveDomainePK.codeDomaine = :codeDomaine"),
    @NamedQuery(name = "ChoixEleveDomaine.findByClassement", query = "SELECT c FROM ChoixEleveDomaine c WHERE c.classement = :classement")})
public class ChoixEleveDomaine implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ChoixEleveDomainePK choixEleveDomainePK;
    @Column(name = "classement")
    private Integer classement;
    @JoinColumn(name = "code_domaine", referencedColumnName = "code", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Domaine domaine;
    @JoinColumn(name = "id_eleve", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Eleve eleve;

    public ChoixEleveDomaine() {
    }

    public ChoixEleveDomaine(ChoixEleveDomainePK choixEleveDomainePK) {
        this.choixEleveDomainePK = choixEleveDomainePK;
    }

    public ChoixEleveDomaine(int idEleve, int codeDomaine) {
        this.choixEleveDomainePK = new ChoixEleveDomainePK(idEleve, codeDomaine);
    }

    public ChoixEleveDomainePK getChoixEleveDomainePK() {
        return choixEleveDomainePK;
    }

    public void setChoixEleveDomainePK(ChoixEleveDomainePK choixEleveDomainePK) {
        this.choixEleveDomainePK = choixEleveDomainePK;
    }

    public Integer getClassement() {
        return classement;
    }

    public void setClassement(Integer classement) {
        this.classement = classement;
    }

    public Domaine getDomaine() {
        return domaine;
    }

    public void setDomaine(Domaine domaine) {
        this.domaine = domaine;
    }

    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (choixEleveDomainePK != null ? choixEleveDomainePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChoixEleveDomaine)) {
            return false;
        }
        ChoixEleveDomaine other = (ChoixEleveDomaine) object;
        if ((this.choixEleveDomainePK == null && other.choixEleveDomainePK != null) || (this.choixEleveDomainePK != null && !this.choixEleveDomainePK.equals(other.choixEleveDomainePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.centrale.nantes.pappl.items.ChoixEleveDomaine[ choixEleveDomainePK=" + choixEleveDomainePK + " ]";
    }
    
}
