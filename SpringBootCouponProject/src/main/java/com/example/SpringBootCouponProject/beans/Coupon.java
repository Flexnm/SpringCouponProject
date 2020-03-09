package com.example.SpringBootCouponProject.beans;

import java.sql.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "coupons")
public class Coupon {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long couponId;
	@ManyToOne(fetch = FetchType.EAGER)
	private Company company;
	@Enumerated(EnumType.ORDINAL)// can also use @Colum because it saves the data as ordinal by default
	private CategoryType type;
	@Column
	private String title;
	@Column
	private String description;
	@Column(name = "start_date")
	private Date startDate;
	@Column(name = "end_date")
	private Date endDate;
	@Column
	private int amount;
	@Column
	private double price;
	@Column
	private String image;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(inverseJoinColumns = @JoinColumn(name = "customer_id"),
	joinColumns = @JoinColumn(name = "coupon_id"),
	name = "customers_vs_coupons")
	private Set<Customer> customers;
	
	public Coupon() {} // CTOR for HIBERNATE

	public Coupon(CategoryType type, String title, String description, Date startDate, Date endDate, int amount,
			double price, String image) {
		super();
		this.type = type;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}

	@Override
	public String toString() {
		return "Coupon [couponId=" + couponId + ", company=" + company + ", type=" + type + ", title=" + title
				+ ", description=" + description + ", startDate=" + startDate + ", endDate=" + endDate + ", amount="
				+ amount + ", price=" + price + ", image=" + image + "]";
	}

	public CategoryType getType() {
		return type;
	}

	public void setType(CategoryType type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public long getCouponId() {
		return couponId;
	}

	public Company getCompany() {
		return company;
	}

	public Set<Customer> getCustomers() {
		return customers;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
