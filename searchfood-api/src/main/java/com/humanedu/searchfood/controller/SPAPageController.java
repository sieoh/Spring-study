package com.humanedu.searchfood.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SPAPageController {
  @GetMapping("/page/main")
    public ModelAndView pageMain() {
    return new ModelAndView("main");
  }
}
