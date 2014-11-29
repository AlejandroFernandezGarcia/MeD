package ampa.sa.student;

import java.io.Serializable;
import java.util.Set;

import ampa.sa.bill.Bill;

@SuppressWarnings("serial")
public class Household implements Serializable {

	private String banckAccount;
	private Set<Student> mentored;
	private Set<Bill> bills;
	private String representative;
	private String representative1;

	public Household(String banckAccount, Set<Student> mentored,
			Set<Bill> bills, String representative) {
		super();
		this.banckAccount = banckAccount;
		this.mentored = mentored;
		this.bills = bills;
		this.representative = representative;
		this.representative1 = null;
	}

	public Household(String banckAccount, Set<Student> mentored,
			Set<Bill> bills, String representative, String representative1) {
		super();
		this.banckAccount = banckAccount;
		this.mentored = mentored;
		this.bills = bills;
		this.representative = representative;
		this.representative1 = representative1;
	}

	public String getBanckAccount() {
		return banckAccount;
	}

	public void setBanckAccount(String banckAccount) {
		this.banckAccount = banckAccount;
	}

	public Set<Student> getMentored() {
		return mentored;
	}

	public void setMentored(Set<Student> mentored) {
		this.mentored = mentored;
	}

	public Set<Bill> getBills() {
		return bills;
	}

	public void setBills(Set<Bill> bills) {
		this.bills = bills;
	}

	public String getRepresentative() {
		return representative;
	}

	public void setRepresentative(String representative) {
		this.representative = representative;
	}

	public String getRepresentative1() {
		return representative1;
	}

	public void setRepresentative1(String representative1) {
		this.representative1 = representative1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((banckAccount == null) ? 0 : banckAccount.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		return (banckAccount.compareTo(((Household) obj).getBanckAccount()) == 0);
	}

}
