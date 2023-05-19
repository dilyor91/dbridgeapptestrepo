package com.kdatalab.bridge.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.kdatalab.bridge.domain.TbAttachment} entity.
 */
@Schema(description = "Attachment entity")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TbAttachmentDTO implements Serializable {

    private Long id;

    private String attType;

    private String name;

    private String path;

    private Long fileSize;

    private String ext;

    private String regUser;

    private Instant regDate;

    private String modUser;

    private Instant modDate;

    private TbBoardDTO bdSeq;

    private TbGuideDTO gdSeq;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttType() {
        return attType;
    }

    public void setAttType(String attType) {
        this.attType = attType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
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

    public TbBoardDTO getBdSeq() {
        return bdSeq;
    }

    public void setBdSeq(TbBoardDTO bdSeq) {
        this.bdSeq = bdSeq;
    }

    public TbGuideDTO getGdSeq() {
        return gdSeq;
    }

    public void setGdSeq(TbGuideDTO gdSeq) {
        this.gdSeq = gdSeq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TbAttachmentDTO)) {
            return false;
        }

        TbAttachmentDTO tbAttachmentDTO = (TbAttachmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tbAttachmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TbAttachmentDTO{" +
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
            ", bdSeq=" + getBdSeq() +
            ", gdSeq=" + getGdSeq() +
            "}";
    }
}
