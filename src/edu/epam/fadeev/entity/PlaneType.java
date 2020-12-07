package edu.epam.fadeev.entity;

public class PlaneType {
    private Manufacturer manufacturer;
    private String model;

    public PlaneType(Manufacturer manufacturer, String model) {
        this.manufacturer = manufacturer;
        this.model = model;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public String getModel() {
        return model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlaneType planeType = (PlaneType) o;

        if (manufacturer != planeType.manufacturer) return false;
        return model != null ? model.equals(planeType.model) : planeType.model == null;
    }

    @Override
    public int hashCode() {
        int result = manufacturer != null ? manufacturer.hashCode() : 0;
        result = 31 * result + (model != null ? model.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PlaneType{");
        sb.append("manufacturer=").append(manufacturer);
        sb.append(", model='").append(model).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
