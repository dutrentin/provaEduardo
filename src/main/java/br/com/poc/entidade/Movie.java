package br.com.poc.entidade;

import javax.persistence.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.FetchType;
import javax.persistence.Table;
import java.io.Serializable;
import java.io.Serializable;
import java.util.List;
import br.com.poc.entidade.Movie;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name="MOVIE")
public class Movie implements Serializable {

    private static final long serialVersionUID = -8706524676310095432L;

    @Id
    @Column(name = "CD_MOVIE")
    @SequenceGenerator(name = "SEQ_MOVIE", sequenceName = "SEQ_MOVIE", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MOVIE")
    private Integer id;

    @Column(name="NR_YEAR", length = 4)
    private Integer year;

    @Column(name="DS_TITLE", length = 100)
    private String title;

    @Column(name="DS_STUDIOS",length = 100)
    private String studios;

    @Column(name="FG_WINNER",length = 1)
    private Boolean winner;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "MOVIE_PRODUCER",
            joinColumns = { @JoinColumn(name = "CD_MOVIE", nullable = false, insertable = true) },
            inverseJoinColumns = { @JoinColumn(name = "CD_PRODUCER", nullable = false, insertable = true) })
    @Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
    private List<Producer> producers;

    public Movie(Integer id, String title){
        this.id = id;
        this.title = title;
    }

    public Movie(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getWinner() {
        return winner;
    }

    public void setWinner(Boolean winner) {
        this.winner = winner;
    }

    public String getStudios() {
        return studios;
    }

    public void setStudios(String studios) {
        this.studios = studios;
    }

    public List<Producer> getProducers() {
        return producers;
    }

    public void setProducers(List<Producer> producers) {
        this.producers = producers;
    }
}
