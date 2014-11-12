package ampa.sa.diningHall;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@SuppressWarnings("serial")
public class DiningHall implements Serializable {

	public enum Type {
		MADRUGADORES, COMEDOR
	}

	private int monitors;
	private BigDecimal price;
	private Type type;

	public DiningHall(int places, BigDecimal price, Type type) {
		this.monitors = places;
		this.price = price.setScale(2, RoundingMode.HALF_UP);
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getMonitors() {
		return monitors;
	}

	public void setMonitors(int monitors) {
		this.monitors = monitors;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public String toString() {
		switch (type) {
		case MADRUGADORES:
			return "Madrugadores";
		case COMEDOR:
			return "Comedor";
		}
		return "";
	}

}
