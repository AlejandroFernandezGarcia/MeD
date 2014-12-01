package ampa.sa.bill;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;

import ampa.sa.student.Household;

@SuppressWarnings("serial")
public class Bill implements Serializable, Comparable<Bill> {
	private Household houseHold;
	private BigDecimal total;
	private Set<BillLine> billLines;
	private Calendar date;

	public Bill(Household houseHold, BigDecimal total, Set<BillLine> billLines,
			Calendar date) {
		super();
		this.houseHold = houseHold;
		this.total = total;
		this.billLines = billLines;
		this.date = date;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public Set<BillLine> getBillLines() {
		return billLines;
	}

	public void setBillLines(Set<BillLine> billLines) {
		this.billLines = billLines;
	}

	public Calendar getDate() {
		return date;
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

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM | yyyy");
		Calendar aux = Calendar.getInstance();
		aux.setTime(date.getTime());
		aux.add(Calendar.MONTH, 1);
		return sdf.format(aux.getTime());
	}

}
