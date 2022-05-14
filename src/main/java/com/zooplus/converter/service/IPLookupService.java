package com.zooplus.converter.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.zooplus.converter.entity.CountryCurrency;
import com.zooplus.converter.repository.CountryCurrencyRepository;
import com.zooplus.converter.util.IPLookupUtil;

@Service
public class IPLookupService {

	@Autowired
	RestTemplate restTemplate;

	@Value("${findmyip.url}")
	private String findMyIpUrl;

	@Value("${iplocation.url}")
	private String ipLocationUrl;

	@Autowired
	CountryCurrencyRepository countryCurrencyRepository;

	public String getCurrencyCode(String ipAddress) {
		if (!StringUtils.hasText(ipAddress))
			try {
				ipAddress = IPLookupUtil.findPublicIP(restTemplate, findMyIpUrl);
			} catch (Exception e) {
				// TODO throw user defined exception
			}
		String countryCode = "";
		try {
			countryCode = IPLookupUtil.findCountryFromIP(restTemplate, ipAddress, ipLocationUrl);
		} catch (Exception e) {
			// TODO throw user defined exception
		}
		System.out.println("Public IP Location: " + countryCode + "\n");
		Optional<CountryCurrency> countryCurrency = countryCurrencyRepository.findById(countryCode);
		if (countryCurrency.isPresent()) {
			return countryCurrency.get().getCurrencyCode();
		} else {
			// TODO throw user defined exception
		}
		return null;
	}

}
