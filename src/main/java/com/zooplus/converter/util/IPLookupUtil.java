package com.zooplus.converter.util;

import org.springframework.http.ResponseEntity;
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
		RestTemplate res = new RestTemplate();
		ResponseEntity<String> myIPResponse = res.getForEntity(findMyIpUrl, String.class);
		String publicIPAddress = myIPResponse.getBody();
		return publicIPAddress;
	}

	/**
	 * Function to return the Country code of the IP address passed
	 * @param restTemplate 
	 * 
	 * @param ipAddress
	 * @param IPlocationUrl
	 * @return
	 */
	public static String findCountryFromIP(RestTemplate restTemplate, String ipAddress, String IPlocationUrl) {		
		final String url_geo = IPlocationUrl + ipAddress;
		ResponseEntity<IpLocation> myIPMetadata = restTemplate.getForEntity(url_geo, IpLocation.class);
		return myIPMetadata.getBody().getCountry_code2();
	}

}
