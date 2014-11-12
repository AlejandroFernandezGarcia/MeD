package ampa.sa.receipt;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Set;

import ampa.sa.student.Household;

@SuppressWarnings("serial")
public class Bill implements Serializable, Comparable<Bill> {
	private Household houseHold;
	private BigDecimal total;
	private Set<BillLine> receiptLines;
	private Calendar date;

	public Bill(Household houseHold, BigDecimal total,
			Set<BillLine> receiptLines, Calendar date) {
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

	public Set<BillLine> getReceiptLines() {
		return receiptLines;
	}

	public void setReceiptLines(Set<BillLine> receiptLines) {
		this.receiptLines = receiptLines;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	@Override
	public int compareTo(Bill o) {
		if (this.date.before(o.getDate())) {
			return -1;
		} else if (this.date.after(o.getDate())) {
			return 1;
		} else
			return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (((((Bill) obj).getDate().get(Calendar.MONTH)) == (date
				.get(Calendar.MONTH)))
				&& (((Bill) obj).getDate().get(Calendar.YEAR)) == (date
						.get(Calendar.YEAR))) {
			return true;
		}
		return false;
	}

}
