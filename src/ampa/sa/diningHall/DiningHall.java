package ampa.sa.diningHall;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import ampa.sa.util.Schedule;

@SuppressWarnings("serial")
public class DiningHall implements Serializable{
	private int id;
	private Schedule schedule;
	private int monitors;
	private BigDecimal price;

	public DiningHall(int id, Schedule schedule, int places, BigDecimal price) {
		this.id = id;
		this.schedule = schedule;
		this.monitors = places;
		this.price = price.setScale(2,RoundingMode.HALF_UP);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + monitors;
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result
				+ ((schedule == null) ? 0 : schedule.hashCode());
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
		DiningHall other = (DiningHall) obj;
		if (id != other.id)
			return false;
		if (monitors != other.monitors)
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (schedule == null) {
			if (other.schedule != null)
				return false;
		} else if (!schedule.equals(other.schedule))
			return false;
		return true;
	}

	@Override
	public String toString(){
		return "(" + String.valueOf(id) + ") " + schedule.getStartTime() + " | " + schedule.getEndTime();
	}
	
}
