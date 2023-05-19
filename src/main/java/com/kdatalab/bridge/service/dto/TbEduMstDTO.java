package com.kdatalab.bridge.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.kdatalab.bridge.domain.TbEduMst} entity.
 */
@Schema(description = "TB_EDU_MST")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TbEduMstDTO implements Serializable {

    private Long id;

    private Long viewCnt;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TbEduMstDTO)) {
            return false;
        }

        TbEduMstDTO tbEduMstDTO = (TbEduMstDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tbEduMstDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TbEduMstDTO{" +
            "id=" + getId() +
            ", viewCnt=" + getViewCnt() +
            "}";
    }
}
