package com.zooplus.converter.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.zooplus.converter.dto.IpLocation;

@SpringBootTest
public class IPLookupUtilTest {

	@Mock
	RestTemplate restTemplate;

	@Test
	void findIP() {
		String findMyIpUrl = "https://api.my-ip.io/ip";
		Mockito.when(restTemplate.exchange(findMyIpUrl, HttpMethod.GET, new HttpEntity(null), String.class))
				.thenReturn(new ResponseEntity<String>("85.214.132.117", HttpStatus.OK));
		assertEquals("85.214.132.117", IPLookupUtil.findPublicIP(restTemplate, findMyIpUrl));
	}

	@Test
	void findLocationOfIP() {
		String iplocationUrl = "https://api.iplocation.net?ip=";
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_HTML));
		messageConverters.add(converter);
		restTemplate.setMessageConverters(messageConverters);
		IpLocation iplocationMetadata = new IpLocation();
		iplocationMetadata.setCountry_code2("DE");
		Mockito.when(restTemplate.getForEntity(iplocationUrl + "85.214.132.117", IpLocation.class))
				.thenReturn(new ResponseEntity<IpLocation>(iplocationMetadata, HttpStatus.OK));
		assertEquals("DE", IPLookupUtil.findCountryFromIP(restTemplate, "85.214.132.117", iplocationUrl));
	}

}
