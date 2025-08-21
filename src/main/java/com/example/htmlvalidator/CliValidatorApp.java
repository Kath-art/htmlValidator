package com.example.htmlvalidator;

import com.example.htmlvalidator.model.Issue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CliValidatorApp {
    public static void main(String[] args) throws Exception {
        if (args.length == 0 || "-h".equals(args[0]) || "--help".equals(args[0])) {
            System.out.println("""
                HTML Validator
                Usage:
                  validate <file.html>    # validate a local file
                  validate <url>          # validate a webpage
                """);
            System.exit(args.length == 0 ? 1 : 0);
        }

        Document doc;
        if (args[0].startsWith("http://") || args[0].startsWith("https://")) {
            doc = Jsoup.connect(args[0]).get();
        } else {
            File file = new File(args[0]);
            doc = Jsoup.parse(file, StandardCharsets.UTF_8.name(), file.toURI().toString());
        }

        ValidatorEngine engine = new ValidatorEngine();
        List<Issue> issues = engine.validate(doc);

        if (issues.isEmpty()) {
            System.out.println("No issues found.");
        } else {
            System.out.println("Found " + issues.size() + " issue(s):");
            issues.forEach(System.out::println);
        }
    }
}

