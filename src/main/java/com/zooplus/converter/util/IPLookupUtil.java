package com.zooplus.converter.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.zooplus.converter.dto.IpLocation;

public class IPLookupUtil {

	/**
	 * Function to return the IP of the machine making requests
	 * @param restTemplate 
	 * 
	 * @param findMyIpUrl
	 * @return
	 */
	public static String findPublicIP(RestTemplate restTemplate, String findMyIpUrl) {
		ResponseEntity<String> myIPResponse = restTemplate.getForEntity(findMyIpUrl, String.class);
		String publicIPAddress = myIPResponse.getBody();
		return publicIPAddress;

	}

	/**
	 * Function to return the Country code of the IP address passed
	 * @param restTemplate 
	 * 
	 * @param iPAddress
	 * @param IPlocationUrl
	 * @return
	 */
	public static String findCountryFromIP(RestTemplate restTemplate, String iPAddress, String IPlocationUrl) {
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_HTML));
		messageConverters.add(converter);
		restTemplate.setMessageConverters(messageConverters);
		final String url_geo = IPlocationUrl + iPAddress;
		ResponseEntity<IpLocation> myIPMetadata = restTemplate.getForEntity(url_geo, IpLocation.class);
		return myIPMetadata.getBody().getCountry_code2();
	}

}
