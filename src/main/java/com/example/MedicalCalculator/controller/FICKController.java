package com.example.MedicalCalculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FICKController
{
    @PostMapping("/calculateFICK")
    public String calculate(@RequestParam double Weight, 
                             @RequestParam double Height, 
                             @RequestParam double SaO2,
                             @RequestParam double SvO2,
                             @RequestParam double Hgb,
                             @RequestParam double HeartRate,
                             @RequestParam double Age,
                             Model model) {

        double BSA = Math.sqrt((Height * Weight) / 3600);

        double CaO2 = Hgb * 1.31 * SaO2;

        double CvO2 = Hgb * 1.31 * SvO2;

        double SV;

        double CI;

        double CO;

        double VO2;

        if(Age < 70)
        {
            VO2 = 125 * BSA;

            CO = 10 * (VO2 / (CaO2 - CvO2));

            SV = 1000 * (CO / HeartRate);

            CI = CO / BSA;
        }
        else
        {
            VO2 = 110 * BSA;

            CO = 10 * (VO2 / (CaO2 - CvO2));

            SV = 1000 * (CO / HeartRate);

            CI = CO / BSA;
        }
        model.addAttribute("result", CO);
        model.addAttribute("result2", CI);
        model.addAttribute("result3", SV);
        return "fragments/FICKResult :: resultFragment";
                             }
}