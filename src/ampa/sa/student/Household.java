package ampa.sa.student;

import java.util.Set;

public class Household {

	private String banckAccount;
	private Set<Student> tutelados;

	public Household(String banckAccount, Set<Student> tutelados) {
		super();
		this.banckAccount = banckAccount;
		this.tutelados = tutelados;
	}

	public String getBanckAccount() {
		return banckAccount;
	}

	public void setBanckAccount(String banckAccount) {
		this.banckAccount = banckAccount;
	}

	public Set<Student> getTutelados() {
		return tutelados;
	}

	public void setTutelados(Set<Student> tutelados) {
		this.tutelados = tutelados;
	}
}
