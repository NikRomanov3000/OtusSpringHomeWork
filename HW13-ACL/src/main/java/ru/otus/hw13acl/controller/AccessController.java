package ru.otus.hw13acl.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessController {
  @GetMapping({ "/accessDenied" })
  public String getAccessDeniedPage() {
    return "accessdenied";
  }
}
