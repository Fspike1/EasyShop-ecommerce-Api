package org.yearup.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


    @Controller
    public class HomeController
    {
        @GetMapping("/thankyou")
        public String showThankYouPage()
        {
            return "thankyou"; // Spring will look for thankyou.html in templates/
        }

        @GetMapping("/")
        public String showHomePage()
        {
            return "index"; // loads home.html from templates folder
        }
        @GetMapping("/index")
        public String altHomePage() {
            return "index";
        }

    }

