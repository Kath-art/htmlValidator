package com.example.htmlvalidator.controllers;

import com.example.htmlvalidator.ValidatorEngine;
import com.example.htmlvalidator.model.Issue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("validate-url")
public class UrlValidatorController implements ValidateHtmlController {
    private final ValidatorEngine engine;

    public UrlValidatorController(ValidatorEngine engine) { this.engine = engine;}

    @GetMapping
    public String form() {
        return "url-form";
    }

    @Override
    @PostMapping
    @ResponseBody
    public String validateHtml(@RequestParam String htmlContent) {
        try {
            Document doc = Jsoup.connect(htmlContent).get();
            List<Issue> issues = engine.validate(doc);
            return engine.renderResults(issues, htmlContent);

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not validate URL: " + e.getMessage());
        }
    }
}
