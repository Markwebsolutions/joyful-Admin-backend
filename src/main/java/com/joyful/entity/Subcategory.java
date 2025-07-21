package com.joyful.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Subcategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "name", columnDefinition = "TEXT")
	private String name;
	@Column(name = "imagepath", columnDefinition = "TEXT")
	private String imagepath;

	@Column(name = "metatitle", columnDefinition = "TEXT")
	private String metatitle;

	@Column(name = "ispublished")
	private boolean ispublished;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "metadescription", columnDefinition = "TEXT")
	private String metadescription;

	@Column(name = "seokeywords", columnDefinition = "TEXT")
	private String seokeywords;

	@ManyToMany
	@JoinTable(name = "subcategory_category", joinColumns = @JoinColumn(name = "subcategory_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	@JsonIgnoreProperties("subcategories")
	private List<Category> categories;

	@ManyToMany(mappedBy = "subcategories")
	@JsonIgnoreProperties("subcategories") // ✅ Prevents recursive loop
	private Set<Product> products = new HashSet<>();


	@Transient
	private List<Long> categoryIds;

	public List<Long> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(List<Long> categoryIds) {
		this.categoryIds = categoryIds;
	}

	public Set<Product> getProducts() {
		return this.products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}
}
