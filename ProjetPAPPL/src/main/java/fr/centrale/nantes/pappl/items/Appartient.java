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
@Table(name = "appartient")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Appartient.findAll", query = "SELECT a FROM Appartient a"),
    @NamedQuery(name = "Appartient.findByAppartientId", query = "SELECT a FROM Appartient a WHERE a.appartientId = :appartientId")})
public class Appartient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "appartient_id")
    private Integer appartientId;
    @JoinColumn(name = "code", referencedColumnName = "code")
    @ManyToOne(optional = false)
    private Domaine code;
    @JoinColumn(name = "numero", referencedColumnName = "numero")
    @ManyToOne(optional = false)
    private Sujet numero;

    public Appartient() {
    }

    public Appartient(Integer appartientId) {
        this.appartientId = appartientId;
    }

    public Integer getAppartientId() {
        return appartientId;
    }

    public void setAppartientId(Integer appartientId) {
        this.appartientId = appartientId;
    }

    public Domaine getCode() {
        return code;
    }

    public void setCode(Domaine code) {
        this.code = code;
    }

    public Sujet getNumero() {
        return numero;
    }

    public void setNumero(Sujet numero) {
        this.numero = numero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (appartientId != null ? appartientId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Appartient)) {
            return false;
        }
        Appartient other = (Appartient) object;
        if ((this.appartientId == null && other.appartientId != null) || (this.appartientId != null && !this.appartientId.equals(other.appartientId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fr.centrale.nantes.pappl.items.Appartient[ appartientId=" + appartientId + " ]";
    }
    
}
