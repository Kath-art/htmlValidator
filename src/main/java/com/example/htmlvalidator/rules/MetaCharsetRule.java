package com.example.htmlvalidator.rules;

import com.example.htmlvalidator.model.Issue;
import com.example.htmlvalidator.model.Severity;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;

public class MetaCharsetRule implements Rule {

    @Override
    public List<Issue> evaluate(Document doc) {
        Element meta = doc.selectFirst("meta[charset]");
        if (meta == null || meta.attr("charset").isBlank()) {
            return List.of(new Issue(Severity.WARNING, "Missing or empty <meta charset>", "head"));
        }
        return List.of();
    }

    @Override
    public String description() {
        return "Document should declare a charset in <meta> tag.";
    }
}
