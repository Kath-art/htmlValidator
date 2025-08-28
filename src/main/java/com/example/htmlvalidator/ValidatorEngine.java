package com.example.htmlvalidator;

import com.example.htmlvalidator.model.Issue;
import com.example.htmlvalidator.rules.Rule;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

@Service
public class ValidatorEngine {

    private final List<Rule> rules;

    // dynamically load all Rule implementations
    public ValidatorEngine() {
        rules = new ArrayList<>();
        ServiceLoader.load(Rule.class).forEach(rules::add);
    }

    public List<Issue> validate(Document document) {
//        try {
//            String html = new String(htmlContent.getBytes(), StandardCharsets.UTF_8);
//            Document doc = Jsoup.parse(html);
//            List<Issue> issues = engine.validate(doc);
//            model.addAttribute("source", "Uploaded File");
//            model.addAttribute("issues", issues);
//        } catch (IOException e) {
//            model.addAttribute("source", "Uploaded File");
//            model.addAttribute("error", "Failed to read file: " + e.getMessage());
//        }
//        return "results";
//    }

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
