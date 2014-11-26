package ampa.sa.student;

import java.io.Serializable;
import java.util.Set;

import ampa.sa.receipt.Bill;

@SuppressWarnings("serial")
public class Household implements Serializable {

	private String banckAccount;
	private Set<Student> mentored;
	private Set<Bill> receipts;
	private String representative;
	private String representative1;

	public Household(String banckAccount, Set<Student> mentored,
			Set<Bill> receipts, String representative) {
		super();
		this.banckAccount = banckAccount;
		this.mentored = mentored;
		this.receipts = receipts;
		this.representative = representative;
		this.representative1 = null;
	}

	public Household(String banckAccount, Set<Student> mentored,
			Set<Bill> receipts, String representative, String representative1) {
		super();
		this.banckAccount = banckAccount;
		this.mentored = mentored;
		this.receipts = receipts;
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

	public Set<Bill> getReceipts() {
		return receipts;
	}

	public void setReceipts(Set<Bill> receipts) {
		this.receipts = receipts;
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

}
