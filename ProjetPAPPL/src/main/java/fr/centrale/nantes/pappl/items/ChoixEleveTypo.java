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
@Table(name = "choix_eleve_typo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ChoixEleveTypo.findAll", query = "SELECT c FROM ChoixEleveTypo c"),
    @NamedQuery(name = "ChoixEleveTypo.findByClassement", query = "SELECT c FROM ChoixEleveTypo c WHERE c.classement = :classement"),
    @NamedQuery(name = "ChoixEleveTypo.findById", query = "SELECT c FROM ChoixEleveTypo c WHERE c.id = :id")})
public class ChoixEleveTypo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "classement")
    private Integer classement;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "id_eleve", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Eleve idEleve;
    @JoinColumn(name = "code_typo", referencedColumnName = "code")
    @ManyToOne(optional = false)
    private TypologieMission codeTypo;

    public ChoixEleveTypo() {
    }

    public ChoixEleveTypo(Integer id) {
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

    public Eleve getIdEleve() {
        return idEleve;
    }

    public void setIdEleve(Eleve idEleve) {
        this.idEleve = idEleve;
    }

    public TypologieMission getCodeTypo() {
        return codeTypo;
    }

    public void setCodeTypo(TypologieMission codeTypo) {
        this.codeTypo = codeTypo;
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
        if (!(object instanceof ChoixEleveTypo)) {
            return false;
        }
        ChoixEleveTypo other = (ChoixEleveTypo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.centrale.nantes.pappl.items.ChoixEleveTypo[ id=" + id + " ]";
    }
    
}
