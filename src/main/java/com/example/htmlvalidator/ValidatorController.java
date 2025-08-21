package com.example.htmlvalidator;

import com.example.htmlvalidator.model.Issue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/validate")
public class ValidatorController {

    private final ValidatorEngine engine = new ValidatorEngine();

    // Landing page form
    @GetMapping
    @ResponseBody
    public String form() {
        return """
            <html>
              <body>
                <h1>HTML Validator</h1>
                <form action="/validate/url" method="get">
                  <label>Check webpage URL:</label><br/>
                  <input type="text" name="target" size="50"/>
                  <button type="submit">Validate</button>
                </form>
                <br/>
                <form action="/validate/file" method="post" enctype="multipart/form-data">
                  <label>Upload HTML file:</label><br/>
                  <input type="file" name="file"/>
                  <button type="submit">Validate</button>
                </form>
              </body>
            </html>
            """;
    }

    // Validate by URL
    @GetMapping("/url")
    @ResponseBody
    public String validateUrl(@RequestParam String target) throws IOException {
        Document doc = Jsoup.connect(target).get();
        return runValidation(doc, "URL: " + target);
    }

    // Validate uploaded file
    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public String validateFile(@RequestParam("file") MultipartFile file) throws IOException {
        String html = new String(file.getBytes(), StandardCharsets.UTF_8);
        Document doc = Jsoup.parse(html);
        return runValidation(doc, "Uploaded File");
    }

    private String runValidation(Document doc, String source) {
        List<Issue> issues = engine.validate(doc);

        StringBuilder sb = new StringBuilder("<html><body>");
        sb.append("<h2>Results for ").append(source).append("</h2>");
        if (issues.isEmpty()) {
            sb.append("<p style='color:green'>✅ No issues found.</p>");
        } else {
            sb.append("<p>⚠️ Found ").append(issues.size()).append(" issue(s):</p><ul>");
            issues.forEach(i -> sb.append("<li>").append(i.toString()).append("</li>"));
            sb.append("</ul>");
        }
        sb.append("</body></html>");
        return sb.toString();
    }
}

