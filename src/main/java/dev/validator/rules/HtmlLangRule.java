package dev.validator.rules;

import dev.validator.model.Issue;
import dev.validator.model.Severity;
import org.jsoup.nodes.Document;

import java.util.List;

public class HtmlLangRule implements Rule {

    @Override
    public List<Issue> evaluate(Document doc) {
        var html = doc.selectFirst("html");
        if (html == null || !html.hasAttr("lang") || html.attr("lang").isBlank()) {
            return List.of(new Issue(Severity.WARNING, "<html> tag missing non-empty lang attribute", "html"));
        }
        return List.of();
    }

    @Override
    public String description() {
        return "<html> element should have a non-empty lang attribute.";
    }
}
