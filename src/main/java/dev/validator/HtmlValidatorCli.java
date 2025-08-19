package dev.validator;

import dev.validator.model.Issue;
import dev.validator.model.Severity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class HtmlValidatorCli {

    public static void main(String[] args) throws Exception {
        if (args.length == 0 || "-h".equals(args[0]) || "--help".equals(args[0])) {
            System.out.println("""
                HTML Validator
                Usage:
                  validate <file.html>
                """);
            System.exit(args.length == 0 ? 1 : 0);
        }

        File file = new File(args[0]);
        if (!file.exists()) {
            System.err.println("File not found: " + file.getAbsolutePath());
            System.exit(2);
        }

        Document doc = Jsoup.parse(file, StandardCharsets.UTF_8.name(), file.toURI().toString());

        // dynamically load all rules
        ValidatorEngine engine = new ValidatorEngine();
        List<Issue> issues = engine.validate(doc);

        if (issues.isEmpty()) {
            System.out.println("No issues found.");
        } else {
            System.out.println("Found " + issues.size() + " issue(s):");
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
