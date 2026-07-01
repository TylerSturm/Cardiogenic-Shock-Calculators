package com.example.MedicalCalculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController
{
    @GetMapping("/")
    public String Home()
    {
        // direct user to the signup page when loading base directory
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String HomePage()
    {
        return "home";
    }

    @GetMapping("/PAPI")
    public String papi()
    {
        return "PAPI";
    }

    @GetMapping("/FICK")
    public String fick()
    {
        return "FICK";
    }

    @GetMapping("/CPO")
    public String cpo()
    {
        return "CPO";
    }

    @GetMapping("/CO")
    public String co()
    {
        return "CO";
    }

    @GetMapping("/SVR")
    public String svr()
    {
        return "SVR";
    }
}