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
@Table(name = "contient")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contient.findAll", query = "SELECT c FROM Contient c"),
    @NamedQuery(name = "Contient.findByContientId", query = "SELECT c FROM Contient c WHERE c.contientId = :contientId")})
public class Contient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "contient_id")
    private Integer contientId;
    @JoinColumn(name = "numero", referencedColumnName = "numero")
    @ManyToOne(optional = false)
    private Sujet numero;
    @JoinColumn(name = "code", referencedColumnName = "code")
    @ManyToOne(optional = false)
    private TypologieMission code;

    public Contient() {
    }

    public Contient(Integer contientId) {
        this.contientId = contientId;
    }

    public Integer getContientId() {
        return contientId;
    }

    public void setContientId(Integer contientId) {
        this.contientId = contientId;
    }

    public Sujet getNumero() {
        return numero;
    }

    public void setNumero(Sujet numero) {
        this.numero = numero;
    }

    public TypologieMission getCode() {
        return code;
    }

    public void setCode(TypologieMission code) {
        this.code = code;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (contientId != null ? contientId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contient)) {
            return false;
        }
        Contient other = (Contient) object;
        if ((this.contientId == null && other.contientId != null) || (this.contientId != null && !this.contientId.equals(other.contientId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.centrale.nantes.pappl.items.Contient[ contientId=" + contientId + " ]";
    }
    
}
