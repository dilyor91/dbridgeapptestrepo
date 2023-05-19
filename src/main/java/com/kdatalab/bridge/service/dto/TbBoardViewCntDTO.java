package com.kdatalab.bridge.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kdatalab.bridge.domain.TbBoardViewCnt} entity.
 */
@Schema(description = "TbBoardViewCnt entity")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TbBoardViewCntDTO implements Serializable {

    private Long id;

    private Long viewCnt;

    private TbBoardDTO bdSeq;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getViewCnt() {
        return viewCnt;
    }

    public void setViewCnt(Long viewCnt) {
        this.viewCnt = viewCnt;
    }

    public TbBoardDTO getBdSeq() {
        return bdSeq;
    }

    public void setBdSeq(TbBoardDTO bdSeq) {
        this.bdSeq = bdSeq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TbBoardViewCntDTO)) {
            return false;
        }

        TbBoardViewCntDTO tbBoardViewCntDTO = (TbBoardViewCntDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tbBoardViewCntDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TbBoardViewCntDTO{" +
            "id=" + getId() +
            ", viewCnt=" + getViewCnt() +
            ", bdSeq=" + getBdSeq() +
            "}";
    }
}
