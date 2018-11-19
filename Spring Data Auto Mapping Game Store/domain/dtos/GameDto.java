package gamestore.domain.dtos;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class GameDto {
    private String title;
    private String trailer;
    private BigDecimal size;
    private BigDecimal price;
    private String description;
    private String imageThumbnail;
    private LocalDate releaseDate;

    public GameDto() {
    }

    public GameDto(String title, String trailer, BigDecimal size, BigDecimal price,
                   String description, String imageThumbnail, LocalDate releaseDate) {
        this.title = title;
        this.trailer = trailer;
        this.size = size;
        this.price = price;
        this.description = description;
        this.imageThumbnail = imageThumbnail;
        this.releaseDate = releaseDate;
    }

    @NotNull(message = "Title cannot be null.")
    @Pattern(regexp = "[A-Z][\\w\\s]{3,100}",
            message = "Title has to begin with an uppercase letter and must have length between 3 and 100 symbols ")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @NotNull(message = "Trailer cannot be null.")
    @Length(min = 11, max = 11,message = "Trailer must be string of exactly 11 characters")
    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    @NotNull(message = "Size cannot be null.")
    @Min(value = 0, message = "Size must be positive!")
    @Digits(integer = 19, fraction = 2)
    public BigDecimal getSize() {
        return size;
    }

    public void setSize(BigDecimal size) {
        this.size = size;
    }

    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be positive!")
    @Digits(integer = 19, fraction = 2)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @NotNull
    @Length(min = 20)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Pattern(regexp = "^http[s]?://.*", message = "Image Thumbnail must start with: http:// or https://")
    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public void setImageThumbnail(String imageThumbnail) {
        this.imageThumbnail = imageThumbnail;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }
}
