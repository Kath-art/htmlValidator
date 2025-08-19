package dev.validator.rules;

import dev.validator.model.Issue;
import dev.validator.model.Severity;
import org.jsoup.nodes.Document;

import java.util.*;

public class DuplicateIdRule implements Rule {

    @Override
    public List<Issue> evaluate(Document doc) {
        Map<String, Integer> idCounts = new HashMap<>();
        doc.select("[id]").forEach(el -> idCounts.merge(el.id(), 1, Integer::sum));

        List<Issue> issues = new ArrayList<>();
        idCounts.forEach((id, count) -> {
            if (count > 1) {
                issues.add(new Issue(Severity.ERROR, "Duplicate id: " + id, "#" + id));
            }
        });

        return issues;
    }

    @Override
    public String description() {
        return "IDs should be unique within the document.";
    }
}
