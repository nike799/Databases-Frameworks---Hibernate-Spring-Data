package cardealer.domain.dto.cardto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerTotalSalesExportRootDto {
    @XmlElement(name = "customer")
    private List<CustomerTotalSalesExportDto> customerTotalSalesExportDtos;

    public CustomerTotalSalesExportRootDto() {
    }

    public List<CustomerTotalSalesExportDto> getCustomerTotalSalesExportDtos() {
        return customerTotalSalesExportDtos;
    }

    public void setCustomerTotalSalesExportDtos(List<CustomerTotalSalesExportDto> customerTotalSalesExportDtos) {
        this.customerTotalSalesExportDtos = customerTotalSalesExportDtos;
    }
}
