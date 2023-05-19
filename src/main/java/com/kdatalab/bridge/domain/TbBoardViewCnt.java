package com.kdatalab.bridge.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * TbBoardViewCnt entity
 */
@Entity
@Table(name = "tb_board_view_cnt")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TbBoardViewCnt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "view_cnt")
    private Long viewCnt;

    @OneToOne
    @JoinColumn(unique = true)
    private TbBoard bdSeq;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TbBoardViewCnt id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getViewCnt() {
        return this.viewCnt;
    }

    public TbBoardViewCnt viewCnt(Long viewCnt) {
        this.setViewCnt(viewCnt);
        return this;
    }

    public void setViewCnt(Long viewCnt) {
        this.viewCnt = viewCnt;
    }

    public TbBoard getBdSeq() {
        return this.bdSeq;
    }

    public void setBdSeq(TbBoard tbBoard) {
        this.bdSeq = tbBoard;
    }

    public TbBoardViewCnt bdSeq(TbBoard tbBoard) {
        this.setBdSeq(tbBoard);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TbBoardViewCnt)) {
            return false;
        }
        return id != null && id.equals(((TbBoardViewCnt) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TbBoardViewCnt{" +
            "id=" + getId() +
            ", viewCnt=" + getViewCnt() +
            "}";
    }
}
