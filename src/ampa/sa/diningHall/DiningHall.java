package ampa.sa.diningHall;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import ampa.sa.util.Schedule;

@SuppressWarnings("serial")
public class DiningHall implements Serializable {

	private Schedule schedule;
	private int monitors;
	private BigDecimal price;

	public DiningHall(Schedule schedule, int places, BigDecimal price) {
		this.schedule = schedule;
		this.monitors = places;
		this.price = price.setScale(2, RoundingMode.HALF_UP);
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public int getMonitors() {
		return monitors;
	}

	public void setMonitors(int places) {
		this.monitors = places;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return schedule.getStartTime() + " | " + schedule.getEndTime();
	}

}
