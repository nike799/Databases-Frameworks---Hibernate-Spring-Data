package productsshop.domain.dto.categorydto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryByProductsExportRootDto {
    @XmlElement(name = "category")
    private CategoryByProductsExportDto[]categoryByProductsExportDtos;

    public CategoryByProductsExportRootDto() {
    }

    public CategoryByProductsExportDto[] getCategoryByProductsExportDtos() {
        return categoryByProductsExportDtos;
    }

    public void setCategoryByProductsExportDtos(CategoryByProductsExportDto[] categoryByProductsExportDtos) {
        this.categoryByProductsExportDtos = categoryByProductsExportDtos;
    }
}
