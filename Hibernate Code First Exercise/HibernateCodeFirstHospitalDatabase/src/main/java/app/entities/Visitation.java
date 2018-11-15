package app.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "visitations")
public class Visitation {
    private Long id;
    private LocalDate date;
    private Set<Comment> comments;
    private Patient patient;

    public Visitation() {
    }

    public Visitation(LocalDate date, Set<Comment> comments, Patient patient) {
        this.date = date;
        this.comments = comments;
        this.patient = patient;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "date_of_visitation")
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @OneToMany(mappedBy = "visitation")
    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    @ManyToOne(targetEntity = Patient.class)
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;

    }

}

