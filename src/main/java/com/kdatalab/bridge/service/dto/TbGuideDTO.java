package com.kdatalab.bridge.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.kdatalab.bridge.domain.TbGuide} entity.
 */
@Schema(description = "Guide entity")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TbGuideDTO implements Serializable {

    private Long id;

    private String title;

    private String content;

    private String status;

    private String link;

    private String regUser;

    private Instant regDate;

    private String modUser;

    private Instant modDate;

    private TbEduMstDTO eduSeq;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRegUser() {
        return regUser;
    }

    public void setRegUser(String regUser) {
        this.regUser = regUser;
    }

    public Instant getRegDate() {
        return regDate;
    }

    public void setRegDate(Instant regDate) {
        this.regDate = regDate;
    }

    public String getModUser() {
        return modUser;
    }

    public void setModUser(String modUser) {
        this.modUser = modUser;
    }

    public Instant getModDate() {
        return modDate;
    }

    public void setModDate(Instant modDate) {
        this.modDate = modDate;
    }

    public TbEduMstDTO getEduSeq() {
        return eduSeq;
    }

    public void setEduSeq(TbEduMstDTO eduSeq) {
        this.eduSeq = eduSeq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TbGuideDTO)) {
            return false;
        }

        TbGuideDTO tbGuideDTO = (TbGuideDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tbGuideDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TbGuideDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", status='" + getStatus() + "'" +
            ", link='" + getLink() + "'" +
            ", regUser='" + getRegUser() + "'" +
            ", regDate='" + getRegDate() + "'" +
            ", modUser='" + getModUser() + "'" +
            ", modDate='" + getModDate() + "'" +
            ", eduSeq=" + getEduSeq() +
            "}";
    }
}
