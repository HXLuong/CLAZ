package com.claz.rest.controllers;

import java.io.File;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.claz.services.UploadService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@CrossOrigin("*")
@RestController
public class UploadRestController {

	@Autowired
	UploadService uploadService;

	@PostMapping("/rest/upload/{folder}")
	public JsonNode upload(@RequestParam("file") MultipartFile file, @PathVariable("folder") String folder) {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		try {
			File savedFile = uploadService.save(file, folder);
			node.put("name", savedFile.getName());
			node.put("size", savedFile.length());
		} catch (Exception e) {
			node.put("error", "File upload failed: " + e.getMessage());
		}
		return node;
	}

}
