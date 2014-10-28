package ampa.sa.student;

import java.io.Serializable;
import java.util.Set;

import ampa.sa.receipt.Receipt;

@SuppressWarnings("serial")
public class Household implements Serializable {

	private String banckAccount;
	private Set<Student> mentored;
	private Set<Receipt> receipts;

	public Household(String banckAccount, Set<Student> mentored) {
		super();
		this.banckAccount = banckAccount;
		this.mentored = mentored;
	}

	public Set<Receipt> getReceipts() {
		return receipts;
	}

	public void setReceipts(Set<Receipt> receipts) {
		this.receipts = receipts;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((banckAccount == null) ? 0 : banckAccount.hashCode());
		result = prime * result
				+ ((mentored == null) ? 0 : mentored.hashCode());
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
		Household other = (Household) obj;
		if (banckAccount == null) {
			if (other.banckAccount != null)
				return false;
		} else if (!banckAccount.equals(other.banckAccount))
			return false;
		if (mentored == null) {
			if (other.mentored != null)
				return false;
		} else if (!mentored.equals(other.mentored))
			return false;
		return true;
	}

}
