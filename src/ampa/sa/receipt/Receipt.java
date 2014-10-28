package ampa.sa.receipt;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Set;

import ampa.sa.student.Household;

@SuppressWarnings("serial")
public class Receipt implements Serializable, Comparable<Receipt> {
	private Household houseHold;
	private BigDecimal total;
	private Set<ReceiptLine> receiptLines;
	private Calendar date;

	public Receipt(Household houseHold, BigDecimal total,
			Set<ReceiptLine> receiptLines, Calendar date) {
		super();
		this.houseHold = houseHold;
		this.total = total;
		this.receiptLines = receiptLines;
		this.date = date;
	}

	public Household getHouseHold() {
		return houseHold;
	}

	public void setHouseHold(Household houseHold) {
		this.houseHold = houseHold;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Set<ReceiptLine> getReceiptLines() {
		return receiptLines;
	}

	public void setReceiptLines(Set<ReceiptLine> receiptLines) {
		this.receiptLines = receiptLines;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	@Override
	public int compareTo(Receipt o) {
		if (this.date.before(o.getDate())) {
			return -1;
		} else if (this.date.after(o.getDate())) {
			return 1;
		} else
			return 0;
	}

}
