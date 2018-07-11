/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package syafiqah.SUMS.Allocation.Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author ASUS
 */
@Entity
@NamedQueries({
    @NamedQuery(name="ShortlistSize.supervisor", query="SELECT s FROM ShortListProperties s where s.shortListName='shortlistsupervisor'"),
    @NamedQuery(name="ShortlistSize.project", query="SELECT s FROM ShortListProperties s where s.shortListName='shortlistproject'")
})
public class ShortListProperties implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Basic
    private String shortListName;
    @Basic
    private int shortlistMax;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortListName() {
        return shortListName;
    }

    public void setShortListName(String shortListName) {
        this.shortListName = shortListName;
    }

    public int getShortlistMax() {
        return shortlistMax;
    }

    public void setShortlistMax(int shortlistMax) {
        this.shortlistMax = shortlistMax;
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
        if (!(object instanceof ShortListProperties)) {
            return false;
        }
        ShortListProperties other = (ShortListProperties) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.ShortListProperties[ id=" + id + " ]";
    }
    
}
