package info.ivicel.blog.core.model.dto;

import lombok.Data;

@Data
public class SearchDTO {
    private String keyword;

    private String status;

    private Long categoryId;
}
