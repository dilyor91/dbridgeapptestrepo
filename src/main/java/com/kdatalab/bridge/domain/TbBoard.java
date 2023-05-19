package com.kdatalab.bridge.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Board entity
 */
@Entity
@Table(name = "tb_board")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TbBoard implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "bd_type")
    private String bdType;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "published_date")
    private Instant publishedDate;

    @Column(name = "status")
    private String status;

    @Column(name = "board_order")
    private Integer boardOrder;

    @Column(name = "reg_user")
    private String regUser;

    @Column(name = "reg_date")
    private Instant regDate;

    @Column(name = "mod_user")
    private String modUser;

    @Column(name = "mod_date")
    private Instant modDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TbBoard id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBdType() {
        return this.bdType;
    }

    public TbBoard bdType(String bdType) {
        this.setBdType(bdType);
        return this;
    }

    public void setBdType(String bdType) {
        this.bdType = bdType;
    }

    public String getTitle() {
        return this.title;
    }

    public TbBoard title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public TbBoard content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getPublishedDate() {
        return this.publishedDate;
    }

    public TbBoard publishedDate(Instant publishedDate) {
        this.setPublishedDate(publishedDate);
        return this;
    }

    public void setPublishedDate(Instant publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getStatus() {
        return this.status;
    }

    public TbBoard status(String status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getBoardOrder() {
        return this.boardOrder;
    }

    public TbBoard boardOrder(Integer boardOrder) {
        this.setBoardOrder(boardOrder);
        return this;
    }

    public void setBoardOrder(Integer boardOrder) {
        this.boardOrder = boardOrder;
    }

    public String getRegUser() {
        return this.regUser;
    }

    public TbBoard regUser(String regUser) {
        this.setRegUser(regUser);
        return this;
    }

    public void setRegUser(String regUser) {
        this.regUser = regUser;
    }

    public Instant getRegDate() {
        return this.regDate;
    }

    public TbBoard regDate(Instant regDate) {
        this.setRegDate(regDate);
        return this;
    }

    public void setRegDate(Instant regDate) {
        this.regDate = regDate;
    }

    public String getModUser() {
        return this.modUser;
    }

    public TbBoard modUser(String modUser) {
        this.setModUser(modUser);
        return this;
    }

    public void setModUser(String modUser) {
        this.modUser = modUser;
    }

    public Instant getModDate() {
        return this.modDate;
    }

    public TbBoard modDate(Instant modDate) {
        this.setModDate(modDate);
        return this;
    }

    public void setModDate(Instant modDate) {
        this.modDate = modDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TbBoard)) {
            return false;
        }
        return id != null && id.equals(((TbBoard) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TbBoard{" +
            "id=" + getId() +
            ", bdType='" + getBdType() + "'" +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", publishedDate='" + getPublishedDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", boardOrder=" + getBoardOrder() +
            ", regUser='" + getRegUser() + "'" +
            ", regDate='" + getRegDate() + "'" +
            ", modUser='" + getModUser() + "'" +
            ", modDate='" + getModDate() + "'" +
            "}";
    }
}
