package dev.validator.rules;

import dev.validator.model.Issue;
import dev.validator.model.Severity;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Node;

import java.util.List;

public class DoctypeRule implements Rule {

    @Override
    public List<Issue> evaluate(Document doc) {
        for (Node n : doc.childNodes()) {
            if (n instanceof DocumentType dt) {
                if (!"html".equalsIgnoreCase(dt.name())) {
                    return List.of(new Issue(Severity.WARNING,
                            "Unexpected doctype name: " + dt.name() + " (expected 'html').", ""));
                }
                return List.of(); // OK
            }
        }
        return List.of(new Issue(Severity.ERROR, "Missing <!doctype html>.", ""));
    }

    @Override
    public String description() {
        return "Document should declare <!doctype html>.";
    }
}
