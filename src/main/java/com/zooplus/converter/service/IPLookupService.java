package com.zooplus.converter.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.zooplus.converter.entity.CountryCurrency;
import com.zooplus.converter.exception.CountryCurrencyLookupException;
import com.zooplus.converter.exception.IPMetadataLookupException;
import com.zooplus.converter.exception.IPNotFoundException;
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

	public String getCurrencyCode(String ipAddress) throws IPNotFoundException, IPMetadataLookupException, CountryCurrencyLookupException {
		if (!StringUtils.hasText(ipAddress))
			try {
				ipAddress = IPLookupUtil.findPublicIP(restTemplate, findMyIpUrl);
			} catch (Exception e) {
				throw new IPNotFoundException("Unable to identify your locale");
			}
		String countryCode = "";
		try {
			countryCode = IPLookupUtil.findCountryFromIP(restTemplate, ipAddress, ipLocationUrl);
		} catch (Exception e) {
			throw new IPMetadataLookupException("The locale Information for the IP Address is currently unavailable");
		}
		Optional<CountryCurrency> countryCurrency = countryCurrencyRepository.findById(countryCode);
		if (countryCurrency.isPresent()) {
			return countryCurrency.get().getCurrencyCode();
		} else {
			throw new CountryCurrencyLookupException("Unable to fetch locale for the IP Address");
		}
	}

}
