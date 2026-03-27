package com.example.mental_health2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MentalHealthPageController {

    // Halaman utama (form)
    @GetMapping("/")
    public String showMentalHealthForm() {
        return "mental_health2";
    }

    // Halaman hasil (GET setelah redirect)
    @GetMapping("/result")
    public String showResult() {
        return "result";
    }
}