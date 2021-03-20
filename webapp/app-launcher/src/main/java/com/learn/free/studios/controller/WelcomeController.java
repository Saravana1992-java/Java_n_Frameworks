package com.learn.free.studios.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.learn.free.studios.commons.AvailableServices;

@Controller
public class WelcomeController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView index() throws URISyntaxException, IOException {
		final String curView = "index";
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(curView);
		modelAndView.addObject("availableServices", AvailableServices.services);
		modelAndView.addObject("curView", curView);
		return modelAndView;
	}

	@RequestMapping(value = "/about", method = RequestMethod.GET)
	public ModelAndView about() throws URISyntaxException, IOException {
		final String curView = "about";
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(curView);
		modelAndView.addObject("availableServices", AvailableServices.services);
		modelAndView.addObject("curView", curView);
		return modelAndView;
	}

	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	public ModelAndView contact() throws URISyntaxException, IOException {
		final String curView = "contact";
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(curView);
		modelAndView.addObject("availableServices", AvailableServices.services);
		modelAndView.addObject("curView", curView);
		return modelAndView;
	}
	
	@RequestMapping(value = "/services", method = RequestMethod.GET)
	public ModelAndView services() throws URISyntaxException, IOException {
		final String curView = "services";
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(curView);
		modelAndView.addObject("availableServices", AvailableServices.services);
		modelAndView.addObject("curView", curView);
		return modelAndView;
	}

	@RequestMapping(value = "/gbs", method = RequestMethod.GET)
	public ModelAndView gbs() throws URISyntaxException, IOException {
		final String curView = "gbs";
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(curView);
		modelAndView.addObject("availableServices", AvailableServices.services);
		modelAndView.addObject("curView", "services");
		return modelAndView;
	}
	
	@RequestMapping(value = "/ebm", method = RequestMethod.GET)
	public ModelAndView ebm() throws URISyntaxException, IOException {
		final String curView = "ebm";
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(curView);
		modelAndView.addObject("availableServices", AvailableServices.services);
		modelAndView.addObject("curView", "services");
		return modelAndView;
	}
	
	
}
