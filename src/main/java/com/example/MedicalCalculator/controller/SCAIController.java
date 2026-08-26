package com.example.MedicalCalculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SCAIController
{
    @PostMapping("/calculateSCAI")
    public String calculate(@RequestParam double SBP,
                             @RequestParam double MAP,
                             @RequestParam double SL,
                             @RequestParam double SAA,
                             @RequestParam double PH,
                             @RequestParam double vaso,
                             @RequestParam double MCD,
                             @RequestParam Boolean Hypo,
                             Model model) {

        // --- Named intermediate conditions (mirrors the CSAI/CSWG chart rows) ---

        // "Hemodynamically unstable" band: SBP 60-90 OR MAP 50-65
        boolean hemoUnstable = (SBP >= 60 && SBP <= 90) || (MAP >= 50 && MAP <= 65);

        // "Hypoperfused" band: lactate 2-5 OR SAA/ALT 200-500
        boolean hypoperfused = (SL >= 2 && SL <= 5) || (SAA >= 200 && SAA <= 500);

        // "Severely hypoperfused" band (Stage D territory): lactate 5-10 OR SAA/ALT > 500
        boolean severeHypoperfused = (SL >= 5 && SL <= 10) || (SAA > 500);

        // "Extreme hypoperfusion" band (Stage E territory): lactate > 10
        boolean extremeHypoperfused = SL > 10;

        // Not acidotic (pH threshold; Stage E is pH < 7.2)
        boolean notAcidotic = PH > 7.2;
        boolean acidotic = !notAcidotic;

        // Support burden
        double supportCount = vaso + MCD;
        boolean noSupport = (vaso == 0 && MCD == 0);

        String result;

        // ---- Stage A: hemodynamically stable, no hypoperfusion, no support, no hypotension flag ----
        if (SBP > 90 && MAP > 65 && SL < 2 && SAA < 200 && notAcidotic && noSupport && !Hypo)
        {
            result = "SCAI Stage A";
        }
        // ---- Stage B: hypotensive OR hypoperfused (untreated) — no drugs, no devices ----
        else if ((hemoUnstable || hypoperfused) && notAcidotic && noSupport && !Hypo)
        {
            result = "SCAI Stage B";
        }
        // ---- Stage C: hypotensive AND hypoperfused, with exactly one drug/device OR untreated+Hypo flag ----
        else if (hemoUnstable && hypoperfused && notAcidotic &&
                 ((noSupport && Hypo) || (supportCount == 1 && !Hypo)))
        {
            result = "SCAI Stage C";
        }
        // ---- Stage D: failure to stabilize — severe hypoperfusion, or 2-5 drugs/devices, or persistent hypotension/hypoperfusion on 1 drug ----
        else if ((hemoUnstable && severeHypoperfused && notAcidotic) ||
                 ((supportCount >= 2 && supportCount <= 5 && !Hypo) || (supportCount == 1 && Hypo)))
        {
            result = "SCAI Stage D";
        }
        // ---- Stage E: extremis / refractory shock — extreme lactate, acidosis, >=3 drugs/devices, or out-of-hospital arrest ----
        else if (extremeHypoperfused || acidotic || supportCount >= 3)
        {
            result = "SCAI Stage E";
        }
        else
        {
            // Shouldn't normally be reached if inputs are within expected clinical ranges.
            // Falling back to E is the safe default for shock-staging (fail toward the more severe stage).
            result = "SCAI Stage E";
        }

        model.addAttribute("result", result);
        return "fragments/SCAIResult :: resultFragment";
    }
}