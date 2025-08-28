package com.example.htmlvalidator.controllers;

import com.example.htmlvalidator.ValidatorEngine;
import com.example.htmlvalidator.model.Issue;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/validate-file")
public class FileValidatorController implements ValidateHtmlController {
    private final ValidatorEngine engine;

    public FileValidatorController(ValidatorEngine engine) {
        this.engine = engine;
    }

    @GetMapping
    public String form() {
        return "file-form";
    }

    @Override
    @PostMapping
    @ResponseBody
    public String validateHtml(@RequestBody String htmlContent) {
        try {
            List<Issue> issues = engine.validate(Jsoup.parse(htmlContent));
            return engine.renderResults(issues, htmlContent);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
