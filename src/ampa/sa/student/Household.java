package ampa.sa.student;

import java.io.Serializable;
import java.util.Set;

import ampa.sa.receipt.Bill;

@SuppressWarnings("serial")
public class Household implements Serializable {

	private String banckAccount;
	private Set<Student> mentored;
	private Set<Bill> receipts;

	public Household(String banckAccount, Set<Student> mentored,
			Set<Bill> receipts) {
		super();
		this.banckAccount = banckAccount;
		this.mentored = mentored;
		this.receipts = receipts;
	}

	public Set<Bill> getReceipts() {
		return receipts;
	}

	public void setReceipts(Set<Bill> receipts) {
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
}
