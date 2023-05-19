package com.kdatalab.bridge.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Guide entity
 */
@Entity
@Table(name = "tb_guide")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TbGuide implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "status")
    private String status;

    @Column(name = "link")
    private String link;

    @Column(name = "reg_user")
    private String regUser;

    @Column(name = "reg_date")
    private Instant regDate;

    @Column(name = "mod_user")
    private String modUser;

    @Column(name = "mod_date")
    private Instant modDate;

    @OneToOne
    @JoinColumn(unique = true)
    private TbEduMst eduSeq;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TbGuide id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public TbGuide title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public TbGuide content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return this.status;
    }

    public TbGuide status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLink() {
        return this.link;
    }

    public TbGuide link(String link) {
        this.setLink(link);
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRegUser() {
        return this.regUser;
    }

    public TbGuide regUser(String regUser) {
        this.setRegUser(regUser);
        return this;
    }

    public void setRegUser(String regUser) {
        this.regUser = regUser;
    }

    public Instant getRegDate() {
        return this.regDate;
    }

    public TbGuide regDate(Instant regDate) {
        this.setRegDate(regDate);
        return this;
    }

    public void setRegDate(Instant regDate) {
        this.regDate = regDate;
    }

    public String getModUser() {
        return this.modUser;
    }

    public TbGuide modUser(String modUser) {
        this.setModUser(modUser);
        return this;
    }

    public void setModUser(String modUser) {
        this.modUser = modUser;
    }

    public Instant getModDate() {
        return this.modDate;
    }

    public TbGuide modDate(Instant modDate) {
        this.setModDate(modDate);
        return this;
    }

    public void setModDate(Instant modDate) {
        this.modDate = modDate;
    }

    public TbEduMst getEduSeq() {
        return this.eduSeq;
    }

    public void setEduSeq(TbEduMst tbEduMst) {
        this.eduSeq = tbEduMst;
    }

    public TbGuide eduSeq(TbEduMst tbEduMst) {
        this.setEduSeq(tbEduMst);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TbGuide)) {
            return false;
        }
        return id != null && id.equals(((TbGuide) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TbGuide{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", status='" + getStatus() + "'" +
            ", link='" + getLink() + "'" +
            ", regUser='" + getRegUser() + "'" +
            ", regDate='" + getRegDate() + "'" +
            ", modUser='" + getModUser() + "'" +
            ", modDate='" + getModDate() + "'" +
            "}";
    }
}
