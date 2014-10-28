package ampa.sa.receipt;

import java.io.Serializable;
import java.math.BigDecimal;

import ampa.sa.student.Student;

@SuppressWarnings("serial")
public class ReceiptLine implements Serializable{
	private String concept;
	private BigDecimal price;
	private int units;
	private BigDecimal unitPrice;
	private Student student;

	public ReceiptLine(String concept, BigDecimal price, int units,
			BigDecimal unitPrice, Student student) {
		super();
		this.concept = concept;
		this.price = price;
		this.units = units;
		this.unitPrice = unitPrice;
		this.student = student;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		this.units = units;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

}
