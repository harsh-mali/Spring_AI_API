package com.AI.AiProject.controller;

import com.AI.AiProject.service.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gemini")
@RequiredArgsConstructor
@CrossOrigin
public class GeminiController {
    private final GeminiService geminiService;

    @PostMapping("/ask")
    public String askGeminiAPI(@RequestBody String prompt){

        return geminiService.askGemini(prompt);
    }
}
