package bookshopsystemapp.domain.entities;

import bookshopsystemapp.domain.entities.enums.AgeRestriction;
import bookshopsystemapp.domain.entities.enums.EditionType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity(name = "books")
public class Book extends BaseEntity {
    private String tittle;
    private String discription;
    private EditionType editionType;
    private BigDecimal price;
    private Integer copies;
    private LocalDate releaseDate;
    private AgeRestriction ageRestriction;
    private Author author;
    private Set<Category> categories;

    public Book() {
    }

    public Book(String tittle, String discription,
                EditionType editionType, BigDecimal price, Integer copies,
                LocalDate releaseDate, AgeRestriction ageRestriction) {
        this.tittle = tittle;
        this.discription = discription;
        this.editionType = editionType;
        this.price = price;
        this.copies = copies;
        this.releaseDate = releaseDate;
        this.ageRestriction = ageRestriction;
    }

    @Column(name = "tittle", nullable = false)
    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    @Column(name = "description", columnDefinition = "text", length = 1000)
    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "edition_type", nullable = false)
    public EditionType getEditionType() {
        return editionType;
    }

    public void setEditionType(EditionType editionType) {
        this.editionType = editionType;
    }

    @Column(name = "price", nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "copies", nullable = false)
    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    @Column(name = "release_date")
    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "age_restriction")
    public AgeRestriction getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(AgeRestriction ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    @ManyToOne(targetEntity = Author.class)
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @ManyToMany(targetEntity = Category.class)
    @JoinTable(name = "books_categories",
            joinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
