package ampa.sa.bill;

import java.io.Serializable;
import java.math.BigDecimal;

import ampa.sa.student.Student;

@SuppressWarnings("serial")
public class BillLine implements Serializable {
	private String concept;
	private BigDecimal price;
	private int units;
	private BigDecimal unitPrice;
	private Student student;

	public BillLine(String concept, BigDecimal price, int units,
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

	public BigDecimal getPrice() {
		return price;
	}

	public int getUnits() {
		return units;
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
}
