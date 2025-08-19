package dev.validator.rules;

import dev.validator.model.Issue;
import dev.validator.model.Severity;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;

/**
 * Ensures that <img> tags have a non-empty alt attribute unless they are decorative.
 */
public class ImgAltRule implements Rule {

    @Override
    public List<Issue> evaluate(Document doc) {
        return doc.select("img").stream()
                .filter(img -> !isDecorative(img))
                .filter(img -> !img.hasAttr("alt") || img.attr("alt").isBlank())
                .map(img -> new Issue(Severity.ERROR, "<img> missing non-empty alt", img.cssSelector()))
                .toList();
    }

    private boolean isDecorative(Element img) {
        String role = img.attr("role");
        String ariaHidden = img.attr("aria-hidden");
        return "presentation".equalsIgnoreCase(role) || "true".equalsIgnoreCase(ariaHidden);
    }

    @Override
    public String description() {
        return "<img> should have meaningful alt text unless decorative.";
    }
}
