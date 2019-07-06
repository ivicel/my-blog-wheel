package info.ivicel.blog.core.model.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum PostStatus {
    /**
     * 已发布
     */
    PUBLISHED("PUBLISHED"),

    /**
     * 草稿箱
     */
    DRAFT("DRAFT"),

    /**
     * 回收站
     */
    RECYCLE("RECYCLE");

    private final String status;

    PostStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @JsonCreator
    public static PostStatus get(@JsonProperty("status") String status) {
        try {
            return valueOf(status.toUpperCase());
        } catch (Exception e) {
            return PostStatus.DRAFT;
        }
    }
}
