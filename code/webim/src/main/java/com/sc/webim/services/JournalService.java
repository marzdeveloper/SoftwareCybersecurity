package com.sc.webim.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sc.webim.model.Job;

import org.apache.sanselan.ImageReadException;
import org.springframework.web.multipart.MultipartFile;

public interface JournalService {
	Map<String, ArrayList<String>> getJob(Job job);
}
