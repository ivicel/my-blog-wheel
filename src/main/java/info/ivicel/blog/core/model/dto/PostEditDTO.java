package info.ivicel.blog.core.model.dto;


import info.ivicel.blog.core.model.domain.PostStatus;
import info.ivicel.blog.core.model.domain.User;
import java.util.Set;
import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PostEditDTO {
    @NotEmpty
    private String title;

    private String url;

    private String body;

    private String bodyHtml;

    private String password;

    private PostStatus status;

    private User user;

    private Set<String> tags;

    private Set<String> categories;
}
