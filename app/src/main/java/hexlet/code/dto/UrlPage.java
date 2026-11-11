package hexlet.code.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import hexlet.code.model.Url;

@AllArgsConstructor
@Getter
public class UrlPage extends BasePage {
    private Url url;
}
