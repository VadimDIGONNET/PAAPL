/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.items;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * @author agach
 */
@Embeddable
public class ChoixEleveDomainePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_eleve")
    private int idEleve;
    @Basic(optional = false)
    @NotNull
    @Column(name = "code_domaine")
    private int codeDomaine;

    public ChoixEleveDomainePK() {
    }

    public ChoixEleveDomainePK(int idEleve, int codeDomaine) {
        this.idEleve = idEleve;
        this.codeDomaine = codeDomaine;
    }

    public int getIdEleve() {
        return idEleve;
    }

    public void setIdEleve(int idEleve) {
        this.idEleve = idEleve;
    }

    public int getCodeDomaine() {
        return codeDomaine;
    }

    public void setCodeDomaine(int codeDomaine) {
        this.codeDomaine = codeDomaine;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idEleve;
        hash += (int) codeDomaine;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChoixEleveDomainePK)) {
            return false;
        }
        ChoixEleveDomainePK other = (ChoixEleveDomainePK) object;
        if (this.idEleve != other.idEleve) {
            return false;
        }
        if (this.codeDomaine != other.codeDomaine) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.centrale.nantes.pappl.items.ChoixEleveDomainePK[ idEleve=" + idEleve + ", codeDomaine=" + codeDomaine + " ]";
    }
    
}
