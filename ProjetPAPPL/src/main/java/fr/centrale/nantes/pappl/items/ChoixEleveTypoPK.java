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
public class ChoixEleveTypoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id_eleve")
    private int idEleve;
    @Basic(optional = false)
    @NotNull
    @Column(name = "code_typo")
    private int codeTypo;

    public ChoixEleveTypoPK() {
    }

    public ChoixEleveTypoPK(int idEleve, int codeTypo) {
        this.idEleve = idEleve;
        this.codeTypo = codeTypo;
    }

    public int getIdEleve() {
        return idEleve;
    }

    public void setIdEleve(int idEleve) {
        this.idEleve = idEleve;
    }

    public int getCodeTypo() {
        return codeTypo;
    }

    public void setCodeTypo(int codeTypo) {
        this.codeTypo = codeTypo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idEleve;
        hash += (int) codeTypo;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChoixEleveTypoPK)) {
            return false;
        }
        ChoixEleveTypoPK other = (ChoixEleveTypoPK) object;
        if (this.idEleve != other.idEleve) {
            return false;
        }
        if (this.codeTypo != other.codeTypo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.centrale.nantes.pappl.items.ChoixEleveTypoPK[ idEleve=" + idEleve + ", codeTypo=" + codeTypo + " ]";
    }
    
}
