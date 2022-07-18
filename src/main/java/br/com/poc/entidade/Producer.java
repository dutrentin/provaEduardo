package br.com.poc.entidade;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;
import javax.persistence.ManyToMany;

@Entity
@Table(name="PRODUCER")
public class Producer implements Serializable {

    private static final long serialVersionUID = -8906524676340355432L;

    @Id
    @Column(name = "CD_PRODUCER")
    @SequenceGenerator(name = "SEQ_PRODUCER", sequenceName = "SEQ_PRODUCER", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRODUCER")
    private Integer id;

    @Column(name="DS_PRODUCER")
    private String producer;

    @ManyToMany(mappedBy="producers")
    private List<Movie> movies;

    public Producer(String producer) {
        this.producer = producer;
    }

    public Producer() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}
