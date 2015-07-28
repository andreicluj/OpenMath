/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.andrei.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Trinitas
 */
@Entity
@Table(name = "PROBLEM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Problem.findAll", query = "SELECT p FROM Problem p"),
    @NamedQuery(name = "Problem.findById", query = "SELECT p FROM Problem p WHERE p.id = :id"),
    @NamedQuery(name = "Problem.findByProblemtext", query = "SELECT p FROM Problem p WHERE p.problemtext = :problemtext"),
    @NamedQuery(name = "Problem.findByPublishdate", query = "SELECT p FROM Problem p WHERE p.publishdate = :publishdate")})
public class Problem implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "SEQ_GLOBAL")
    private BigDecimal id;
    @Column(name = "PROBLEMTEXT")
    private String problemtext;
    @Column(name = "PUBLISHDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishdate;

    public Problem() {
    }

    public Problem(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getProblemtext() {
        return problemtext;
    }

    public void setProblemtext(String problemtext) {
        this.problemtext = problemtext;
    }

    public Date getPublishdate() {
        return publishdate;
    }

    public void setPublishdate(Date publishdate) {
        this.publishdate = publishdate;
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
        if (!(object instanceof Problem)) {
            return false;
        }
        Problem other = (Problem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.andrei.entity.Problem[ id=" + id + " ]";
    }
    
}
