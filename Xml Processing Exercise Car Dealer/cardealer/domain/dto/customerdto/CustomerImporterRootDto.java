package cardealer.domain.dto.customerdto;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerImporterRootDto {
    @XmlElement(name = "customer")
    private CustomerImporterDto[] customers;

    public CustomerImporterRootDto() {
    }

    public CustomerImporterDto[] getCustomers() {
        return customers;
    }

    public void setCustomers(CustomerImporterDto[] customers) {
        this.customers = customers;
    }
}
