package hexlet.code.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import hexlet.code.model.Url;

@AllArgsConstructor
@Getter
public class UrlsPage {
    public List<Url> urls;
}
