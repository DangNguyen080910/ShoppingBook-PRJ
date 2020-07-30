package com.shoppingbook.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "must not be null")
	private String title;
	@NotBlank(message = "must not be null")
	private String author;
	@NotBlank(message = "must not be null")
	private String publisher;
	@NotBlank(message = "must not be null")
	@Column(name = "publication_date")
	private String publicationDate;
	@NotBlank(message = "must not be null")
	private String language;
	@Column(name = "number_of_pages")
	private int numberOfPages;
	@NotBlank(message = "must not be null")
	private String format;
	@NotBlank(message = "must not be null")
	private String isbn;
	@Column(name = "shipping_weight")
	private double shippingWeight;
	@Column(name = "list_price")
	private double listPrice;
	@Column(name = "our_price")
	private double ourPrice;
	private boolean active=true;
	@NotBlank(message = "must not be null")
	@Column(columnDefinition = "text")
	private String description;
	@Column(name = "in_stock_number")
	private int inStockNumber;
	
	@Transient
	private MultipartFile bookImage;
	
	@NotNull
	@ManyToOne
	private Category category;
	
	public Category getCategory() {
	return category;
}
public void setCategory(Category category) {
	this.category = category;
}
	
	@OneToMany(mappedBy = "book")
	@JsonIgnore
	private List<BookToCartItem> bookToCartItems;
	
	@OneToMany(mappedBy = "book")
	private List<CommentRating> cmrtList;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getPublicationDate() {
		return publicationDate;
	}
	public void setPublicationDate(String publicationDate) {
		this.publicationDate = publicationDate;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public List<CommentRating> getCmrtList() {
		return cmrtList;
	}
	public void setCmrtList(List<CommentRating> cmrtList) {
		this.cmrtList = cmrtList;
	}
	public int getNumberOfPages() {
		return numberOfPages;
	}
	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public double getShippingWeight() {
		return shippingWeight;
	}
	public void setShippingWeight(double shippingWeight) {
		this.shippingWeight = shippingWeight;
	}
	public double getListPrice() {
		return listPrice;
	}
	public void setListPrice(double listPrice) {
		this.listPrice = listPrice;
	}
	public double getOurPrice() {
		return ourPrice;
	}
	public void setOurPrice(double ourPrice) {
		this.ourPrice = ourPrice;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getInStockNumber() {
		return inStockNumber;
	}
	public void setInStockNumber(int inStockNumber) {
		this.inStockNumber = inStockNumber;
	}
	public MultipartFile getBookImage() {
		return bookImage;
	}
	public void setBookImage(MultipartFile bookImage) {
		this.bookImage = bookImage;
	}
	public List<BookToCartItem> getBookToCartItems() {
		return bookToCartItems;
	}
	public void setBookToCartItems(List<BookToCartItem> bookToCartItems) {
		this.bookToCartItems = bookToCartItems;
	}
	
}
