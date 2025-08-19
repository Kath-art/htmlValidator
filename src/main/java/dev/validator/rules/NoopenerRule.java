package dev.validator.rules;

import dev.validator.model.Issue;
import dev.validator.model.Severity;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;

public class NoopenerRule implements Rule {

    @Override
    public List<Issue> evaluate(Document doc) {
        return doc.select("a[target=_blank]").stream()
                .filter(a -> !a.hasAttr("rel") || !a.attr("rel").contains("noopener"))
                .map(a -> new Issue(Severity.WARNING, "External link should have rel=\"noopener\"", a.cssSelector()))
                .toList();
    }

    @Override
    public String description() {
        return "Links with target=_blank should use rel=\"noopener\" for security.";
    }
}
