package com.example.htmlvalidator.controllers;

import com.example.htmlvalidator.ValidatorEngine;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/validate-html")
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
    @PostMapping(value = "/file")
    @ResponseBody
    public void validateHtml(@RequestBody String htmlContent) {
        try {
            engine.validate(Jsoup.parse(htmlContent));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
