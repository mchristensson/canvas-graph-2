package org.mac.canvasgraph;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Web-controller för sidans rot
 * 
 * @author magnus
 *
 */
@Controller
public class HomeController {

	@Value("${spring.application.name}")
	String appName;

	/**
	 * Visar rotsidan
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/")
	public String homePage(Model model) {
		model.addAttribute("appName", appName);
		return "home";

	}

	/**
	 * Triggar att fel kastas
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/triggafel")
	public String errorTrigger(Model model) throws Exception {
		throw new Exception("Nåt gick fel");
	}

	/**
	 * Returnerar en SVG av canadas flagga
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = "/getCanadaFlag", produces = MediaType.APPLICATION_XHTML_XML_VALUE)
	public @ResponseBody byte[] getImageWithMediaTypeCanada() throws IOException {
		InputStream in = getClass().getResourceAsStream("/sample/canada.svg");
		return IOUtils.toByteArray(in);
	}
}