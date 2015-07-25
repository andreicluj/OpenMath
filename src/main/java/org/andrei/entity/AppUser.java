/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.andrei.entity;

import java.io.Serializable;
import javax.persistence.Column;
 
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
 
@Entity
public class AppUser implements Serializable {
 
	public AppUser() {
 
	};
 
	public AppUser(String login) {
		this.login = login;
 
	};
 
	@Id
        @Column(name = "ID")
        @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
        @SequenceGenerator(name = "id_Sequence", sequenceName = "SEQ_GLOBAL")
        
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
 
	private String login;
 
	public Long getId() {
		return id;
	}
 
	public void setId(Long id) {
		this.id = id;
	}
 
	public String getLogin() {
		return login;
	}
 
	public void setLogin(String login) {
		this.login = login;
	}
}