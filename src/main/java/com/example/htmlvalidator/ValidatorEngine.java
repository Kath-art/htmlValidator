package com.example.htmlvalidator;

import com.example.htmlvalidator.model.Issue;
import com.example.htmlvalidator.rules.Rule;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

@Service
public class ValidatorEngine {
    private final List<Rule> rules;
    private final TemplateEngine templateEngine;
    private final Context context;

    public ValidatorEngine(TemplateEngine templateEngine) {
        rules = new ArrayList<>();
        this.templateEngine = templateEngine;
        this.context = new Context();

        ServiceLoader.load(Rule.class).forEach(rules::add);
    }

    public List<Issue> validate(Document document) {
        List<Issue> issues = new ArrayList<>();
        for (Rule rule : rules) {
            issues.addAll(rule.evaluate(document));
        }
        return issues;
    }


    public String renderResults(List<Issue> issues, String source) {
        context.setVariable("source", source);
        context.setVariable("issues", issues);
        return templateEngine.process("results", context);
    }
}
