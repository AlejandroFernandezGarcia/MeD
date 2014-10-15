package ampa.sa.diningHall;

import ampa.sa.util.Schedule;

public class DiningHall {

	//FIXME Enum type necesario? Sabiendo el horario no haria falta
	/*private enum type {
		EARLYBIRD, NORMAL
	};*/

	private int id;
	private Schedule schedule;
	private int places;
	private int price;

	public DiningHall(int id, Schedule schedule, int places, int price) {
		this.id = id;
		this.schedule = schedule;
		this.places = places;
		this.price = price;
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

	public int getPlaces() {
		return places;
	}

	public void setPlaces(int places) {
		this.places = places;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

}
