package com.kdatalab.bridge.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Attachment entity
 */
@Entity
@Table(name = "tb_attachment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TbAttachment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "att_type")
    private String attType;

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "ext")
    private String ext;

    @Column(name = "reg_user")
    private String regUser;

    @Column(name = "reg_date")
    private Instant regDate;

    @Column(name = "mod_user")
    private String modUser;

    @Column(name = "mod_date")
    private Instant modDate;

    @ManyToOne
    private TbBoard bdSeq;

    @ManyToOne
    @JsonIgnoreProperties(value = { "eduSeq" }, allowSetters = true)
    private TbGuide gdSeq;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TbAttachment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttType() {
        return this.attType;
    }

    public TbAttachment attType(String attType) {
        this.setAttType(attType);
        return this;
    }

    public void setAttType(String attType) {
        this.attType = attType;
    }

    public String getName() {
        return this.name;
    }

    public TbAttachment name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return this.path;
    }

    public TbAttachment path(String path) {
        this.setPath(path);
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getFileSize() {
        return this.fileSize;
    }

    public TbAttachment fileSize(Long fileSize) {
        this.setFileSize(fileSize);
        return this;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getExt() {
        return this.ext;
    }

    public TbAttachment ext(String ext) {
        this.setExt(ext);
        return this;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getRegUser() {
        return this.regUser;
    }

    public TbAttachment regUser(String regUser) {
        this.setRegUser(regUser);
        return this;
    }

    public void setRegUser(String regUser) {
        this.regUser = regUser;
    }

    public Instant getRegDate() {
        return this.regDate;
    }

    public TbAttachment regDate(Instant regDate) {
        this.setRegDate(regDate);
        return this;
    }

    public void setRegDate(Instant regDate) {
        this.regDate = regDate;
    }

    public String getModUser() {
        return this.modUser;
    }

    public TbAttachment modUser(String modUser) {
        this.setModUser(modUser);
        return this;
    }

    public void setModUser(String modUser) {
        this.modUser = modUser;
    }

    public Instant getModDate() {
        return this.modDate;
    }

    public TbAttachment modDate(Instant modDate) {
        this.setModDate(modDate);
        return this;
    }

    public void setModDate(Instant modDate) {
        this.modDate = modDate;
    }

    public TbBoard getBdSeq() {
        return this.bdSeq;
    }

    public void setBdSeq(TbBoard tbBoard) {
        this.bdSeq = tbBoard;
    }

    public TbAttachment bdSeq(TbBoard tbBoard) {
        this.setBdSeq(tbBoard);
        return this;
    }

    public TbGuide getGdSeq() {
        return this.gdSeq;
    }

    public void setGdSeq(TbGuide tbGuide) {
        this.gdSeq = tbGuide;
    }

    public TbAttachment gdSeq(TbGuide tbGuide) {
        this.setGdSeq(tbGuide);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TbAttachment)) {
            return false;
        }
        return id != null && id.equals(((TbAttachment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TbAttachment{" +
            "id=" + getId() +
            ", attType='" + getAttType() + "'" +
            ", name='" + getName() + "'" +
            ", path='" + getPath() + "'" +
            ", fileSize=" + getFileSize() +
            ", ext='" + getExt() + "'" +
            ", regUser='" + getRegUser() + "'" +
            ", regDate='" + getRegDate() + "'" +
            ", modUser='" + getModUser() + "'" +
            ", modDate='" + getModDate() + "'" +
            "}";
    }
}
