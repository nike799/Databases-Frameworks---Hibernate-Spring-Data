package app.entities;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {
    private Long id;
    private String text;
    private Visitation visitation;
    private Diagnose diagnose;

    public Comment() {
    }

    public Comment(String text) {
        this.text = text;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "text", length = 1000)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @ManyToOne(targetEntity = Visitation.class)
    public Visitation getVisitation() {
        return visitation;
    }

    public void setVisitation(Visitation visitation) {
        this.visitation = visitation;
    }
    @ManyToOne(targetEntity = Diagnose.class)
    public Diagnose getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(Diagnose diagnose) {
        this.diagnose = diagnose;
    }
}
