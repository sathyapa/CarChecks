package carcheck.model;

public class CarModel {

	private String carNum;
	private String carColor;
	private String make;
	private String year;
	private String model;

	public String getCarNum() {
		return carNum;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum;
	}

	public String getCarColor() {
		return carColor;
	}

	public void setCarColor(String carColor) {
		this.carColor = carColor;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((carColor == null) ? 0 : carColor.hashCode());
		result = prime * result + ((carNum == null) ? 0 : carNum.hashCode());
		result = prime * result + ((make == null) ? 0 : make.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
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
		CarModel other = (CarModel) obj;
		if (carColor == null) {
			if (other.carColor != null)
				return false;
		} else if (!carColor.equalsIgnoreCase(other.carColor))
			return false;
		if (carNum == null) {
			if (other.carNum != null)
				return false;
		} else if (!carNum.equalsIgnoreCase(other.carNum))
			return false;
		if (make == null) {
			if (other.make != null)
				return false;
		} else if (!make.equals(other.make))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equalsIgnoreCase(other.model))
			return false;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CarModel [carNum=" + carNum + ", carColor=" + carColor + ", make=" + make + ", year=" + year + "]";
	}

	
}
