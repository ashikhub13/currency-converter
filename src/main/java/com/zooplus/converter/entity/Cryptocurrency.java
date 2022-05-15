package com.zooplus.converter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "crypto_currency")
public class Cryptocurrency {

	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "name")
	private String name;
}
