package com.backend.practice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Controller
public class SocketController {
	
	
	@PostMapping("/")
	public void chat() {
		
	}
	
	@GetMapping("/")
	public void chatGet() {
		
	}

}
