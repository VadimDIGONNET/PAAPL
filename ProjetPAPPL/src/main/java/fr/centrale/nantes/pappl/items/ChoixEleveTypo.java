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
@Table(name = "choix_eleve_typo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChoixEleveTypo.findAll", query = "SELECT c FROM ChoixEleveTypo c"),
    @NamedQuery(name = "ChoixEleveTypo.findByIdEleve", query = "SELECT c FROM ChoixEleveTypo c WHERE c.choixEleveTypoPK.idEleve = :idEleve"),
    @NamedQuery(name = "ChoixEleveTypo.findByCodeTypo", query = "SELECT c FROM ChoixEleveTypo c WHERE c.choixEleveTypoPK.codeTypo = :codeTypo"),
    @NamedQuery(name = "ChoixEleveTypo.findByClassement", query = "SELECT c FROM ChoixEleveTypo c WHERE c.classement = :classement")})
public class ChoixEleveTypo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ChoixEleveTypoPK choixEleveTypoPK;
    @Column(name = "classement")
    private Integer classement;
    @JoinColumn(name = "id_eleve", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Eleve eleve;
    @JoinColumn(name = "code_typo", referencedColumnName = "code", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TypologieMission typologiemission;

    public ChoixEleveTypo() {
    }

    public ChoixEleveTypo(ChoixEleveTypoPK choixEleveTypoPK) {
        this.choixEleveTypoPK = choixEleveTypoPK;
    }

    public ChoixEleveTypo(int idEleve, int codeTypo) {
        this.choixEleveTypoPK = new ChoixEleveTypoPK(idEleve, codeTypo);
    }

    public ChoixEleveTypoPK getChoixEleveTypoPK() {
        return choixEleveTypoPK;
    }

    public void setChoixEleveTypoPK(ChoixEleveTypoPK choixEleveTypoPK) {
        this.choixEleveTypoPK = choixEleveTypoPK;
    }

    public Integer getClassement() {
        return classement;
    }

    public void setClassement(Integer classement) {
        this.classement = classement;
    }

    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public TypologieMission getTypologiemission() {
        return typologiemission;
    }

    public void setTypologiemission(TypologieMission typologiemission) {
        this.typologiemission = typologiemission;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (choixEleveTypoPK != null ? choixEleveTypoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChoixEleveTypo)) {
            return false;
        }
        ChoixEleveTypo other = (ChoixEleveTypo) object;
        if ((this.choixEleveTypoPK == null && other.choixEleveTypoPK != null) || (this.choixEleveTypoPK != null && !this.choixEleveTypoPK.equals(other.choixEleveTypoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.centrale.nantes.pappl.items.ChoixEleveTypo[ choixEleveTypoPK=" + choixEleveTypoPK + " ]";
    }
    
}
