package com.example.htmlvalidator;

import com.example.htmlvalidator.model.Issue;
import com.example.htmlvalidator.rules.Rule;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class ValidatorEngine {

    private final List<Rule> rules;

    // dynamically load all Rule implementations
    public ValidatorEngine() {
        rules = new ArrayList<>();
        ServiceLoader.load(Rule.class).forEach(rules::add);
    }

    public List<Issue> validate(Document document) {
        List<Issue> issues = new ArrayList<>();
        for (Rule rule : rules) {
            issues.addAll(rule.evaluate(document));
        }
        return issues;
    }

    public List<Rule> getRules() {
        return List.copyOf(rules);
    }
}
