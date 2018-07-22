package com.revature.models;

import java.sql.Date;

public class Reimbursement {
	private int id;
	private double amount;
	private Date submitted;
	private Date resolved;
	private String reciept;
	private int author;
	private int resolver;
	private int status;
	private int type; 

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public Date getSubmitted() {
		return submitted;
	}
	
	public void setSubmitted(Date submitted) {
		this.submitted = submitted;
	}
	
	public Date getResolved() {
		return resolved;
	}
	
	public void setResolved(Date resolved) {
		this.resolved = resolved;
	}
	
	public String getReciept() {
		return reciept;
	}
	
	public void setReciept(String reciept) {
		this.reciept = reciept;
	}
	
	public int getAuthor() {
		return author;
	}
	
	public void setAuthor(int author) {
		this.author = author;
	}
	
	public int getResolver() {
		return resolver;
	}
	
	public void setResolver(int resolver) {
		this.resolver = resolver;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + author;
		result = prime * result + id;
		result = prime * result + ((reciept == null) ? 0 : reciept.hashCode());
		result = prime * result + ((resolved == null) ? 0 : resolved.hashCode());
		result = prime * result + resolver;
		result = prime * result + status;
		result = prime * result + ((submitted == null) ? 0 : submitted.hashCode());
		result = prime * result + type;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reimbursement other = (Reimbursement) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (author != other.author)
			return false;
		if (id != other.id)
			return false;
		if (reciept == null) {
			if (other.reciept != null)
				return false;
		} else if (!reciept.equals(other.reciept))
			return false;
		if (resolved == null) {
			if (other.resolved != null)
				return false;
		} else if (!resolved.equals(other.resolved))
			return false;
		if (resolver != other.resolver)
			return false;
		if (status != other.status)
			return false;
		if (submitted == null) {
			if (other.submitted != null)
				return false;
		} else if (!submitted.equals(other.submitted))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reimbursement [id=" + id + ", amount=" + amount + ", submitted=" + submitted + ", resolved=" + resolved
				+ ", reciept=" + reciept + ", author=" + author + ", resolver=" + resolver + ", status=" + status
				+ ", type=" + type + "]";
	}
}
