package dev.validator.rules;

import dev.validator.model.Issue;
import org.jsoup.nodes.Document;
import java.util.List;

@FunctionalInterface
public interface Rule {
    List<Issue> evaluate(Document document);

    default String id() {
        return getClass().getSimpleName();
    }

    default String description() {
        return "No description provided";
    }
}
