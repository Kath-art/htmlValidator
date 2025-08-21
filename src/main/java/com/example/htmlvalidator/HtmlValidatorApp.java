package com.example.htmlvalidator;

import com.example.htmlvalidator.model.Issue;
import com.example.htmlvalidator.model.Severity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

@SpringBootApplication
public class HtmlValidatorApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(HtmlValidatorApp.class, args);

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

        // detect if argument looks like a URL (starts with http/https)
        if (args[0].startsWith("http://") || args[0].startsWith("https://")) {
            System.out.println("Fetching webpage: " + args[0]);
            doc = Jsoup.connect(args[0]).get();   // fetch online HTML
        } else {
            File file = new File(args[0]);
            if (!file.exists()) {
                System.err.println("File not found: " + file.getAbsolutePath());
                System.exit(2);
                return;
            }
            doc = Jsoup.parse(file, StandardCharsets.UTF_8.name(), file.toURI().toString());
        }

        // dynamically load all rules
        ValidatorEngine engine = new ValidatorEngine();
        List<Issue> issues = engine.validate(doc);

        if (issues.isEmpty()) {
            System.out.println("✅ No issues found.");
        } else {
            System.out.println("⚠️ Found " + issues.size() + " issue(s):");
            issues.forEach(i -> System.out.println(" - " + i));

            long errors = issues.stream()
                    .filter(i -> i.severity() == Severity.ERROR)
                    .count();

            System.out.println();
            System.out.println("Summary: " + errors + " error(s), " +
                    (issues.size() - errors) + " warning(s).");
        }
    }
}
