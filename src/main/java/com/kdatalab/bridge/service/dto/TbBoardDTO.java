package com.kdatalab.bridge.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.kdatalab.bridge.domain.TbBoard} entity.
 */
@Schema(description = "Board entity")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TbBoardDTO implements Serializable {

    private Long id;

    private String bdType;

    private String title;

    private String content;

    private Instant publishedDate;

    private String status;

    private Integer boardOrder;

    private String regUser;

    private Instant regDate;

    private String modUser;

    private Instant modDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBdType() {
        return bdType;
    }

    public void setBdType(String bdType) {
        this.bdType = bdType;
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

    public Instant getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Instant publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getBoardOrder() {
        return boardOrder;
    }

    public void setBoardOrder(Integer boardOrder) {
        this.boardOrder = boardOrder;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TbBoardDTO)) {
            return false;
        }

        TbBoardDTO tbBoardDTO = (TbBoardDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tbBoardDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TbBoardDTO{" +
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
