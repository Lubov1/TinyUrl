package com.tinyurl;

import com.tinyurl.repository.UrlsRepository;
import com.tinyurl.repository.UrlsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class TinyUrlApplicationTests {
	@Autowired
	public MockMvc mockMvc;

	@MockBean
	public UrlsService urlsService;

	@MockBean
	public UrlsRepository urlsRepository;

	@Test
	void testNotFound() throws Exception {
		mockMvc.perform(post("/tinyurl/74805").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"url\":\"https://www.youtube.com/\"}")).andExpect(status().is(400));
	}

	@Test
	void testCreated() throws Exception {
		String url = "https://www.youtube.com/";
		String tinyUrl = "12345";
		when(urlsService.createTinyUrl(eq(url))).thenReturn(tinyUrl);

		mockMvc.perform(post("/tinyurl/").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(String.format("{\"url\":\"%s\"}",url)))
				.andExpect(status().is(200))
				.andExpect(jsonPath("$.tinyUrl").value(tinyUrl));
	}

	@Test
	void testRedirect() throws Exception {
		String url = "https://www.youtube.com/";
		String tinyUrl = "12345";
		when(urlsService.getLongUrl(eq(tinyUrl))).thenReturn(url);

		mockMvc.perform(post(String.format("/tinyurl/%s",tinyUrl)))
				.andExpect(status().is(302))
				.andExpect(redirectedUrl(url));
	}

}
