package app.domain.entities;

import app.domain.interfaces.Entitable;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "store_locations")
public class StoreLocation implements Entitable {
    private String locationName;
    private Set<Sale> sales;

    public StoreLocation() {
    }

    public StoreLocation(String locationName, Set<Sale> sales) {
        this.locationName = locationName;
        this.sales = sales;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void setId(int id) {

    }

    @Column(name = "location_name")
    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    @OneToMany(targetEntity = Sale.class, mappedBy = "storeLocation")
    public Set<Sale> getSales() {
        return sales;
    }

    public void setSales(Set<Sale> sales) {
        this.sales = sales;
    }
}
