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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 *
 * @author vadimdigonnet
 */
@Entity
@Table(name = "choix_eleve_domaine")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChoixEleveDomaine.findAll", query = "SELECT c FROM ChoixEleveDomaine c"),
    @NamedQuery(name = "ChoixEleveDomaine.findByClassement", query = "SELECT c FROM ChoixEleveDomaine c WHERE c.classement = :classement"),
    @NamedQuery(name = "ChoixEleveDomaine.findById", query = "SELECT c FROM ChoixEleveDomaine c WHERE c.id = :id")})
public class ChoixEleveDomaine implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "classement")
    private Integer classement;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "code_domaine", referencedColumnName = "code")
    @ManyToOne(optional = false)
    private Domaine codeDomaine;
    @JoinColumn(name = "id_eleve", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Eleve idEleve;

    public ChoixEleveDomaine() {
    }

    public ChoixEleveDomaine(Integer id) {
        this.id = id;
    }

    public Integer getClassement() {
        return classement;
    }

    public void setClassement(Integer classement) {
        this.classement = classement;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Domaine getCodeDomaine() {
        return codeDomaine;
    }

    public void setCodeDomaine(Domaine codeDomaine) {
        this.codeDomaine = codeDomaine;
    }

    public Eleve getIdEleve() {
        return idEleve;
    }

    public void setIdEleve(Eleve idEleve) {
        this.idEleve = idEleve;
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
        if (!(object instanceof ChoixEleveDomaine)) {
            return false;
        }
        ChoixEleveDomaine other = (ChoixEleveDomaine) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.centrale.nantes.pappl.items.ChoixEleveDomaine[ id=" + id + " ]";
    }
    
}
