package com.zooplus.converter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.zooplus.converter.entity.CountryCurrency;
import com.zooplus.converter.exception.CountryCurrencyLookupException;
import com.zooplus.converter.exception.IPMetadataLookupException;
import com.zooplus.converter.exception.IPNotFoundException;
import com.zooplus.converter.repository.CountryCurrencyRepository;
import com.zooplus.converter.util.IPLookupUtil;

@SpringBootTest
class IPLookupServiceTest {

	@InjectMocks
	IPLookupService ipLookupService;

	@Mock
	RestTemplate restTemplate;

	@Mock
	CountryCurrencyRepository countryCurrencyRepository;

	@Test
	void testGetCurrencyCode_IPAddress_not_passed() {
		String ipAddress = "85.214.132.117";
		String findMyIpUrl = "https://api.my-ip.io/ip";
		String iplocationUrl = "https://api.iplocation.net?ip=";
		try (MockedStatic<IPLookupUtil> ipLookupUtil = Mockito.mockStatic(IPLookupUtil.class)) {
			ipLookupUtil.when(() -> IPLookupUtil.findPublicIP(restTemplate, findMyIpUrl)).thenReturn(ipAddress);
			ipLookupUtil.when(() -> IPLookupUtil.findCountryFromIP(restTemplate, ipAddress, iplocationUrl))
					.thenReturn("DE");
			CountryCurrency countryCurrency = new CountryCurrency("DE", "Germany", "EURO", "EUR");
			ReflectionTestUtils.setField(ipLookupService, "ipLocationUrl", iplocationUrl);
			ReflectionTestUtils.setField(ipLookupService, "findMyIpUrl", findMyIpUrl);
			when(countryCurrencyRepository.findById("DE")).thenReturn(Optional.of(countryCurrency));
			assertEquals("EUR", ipLookupService.getCurrencyCode(null));
		}
	}

	@Test
	void testGetCurrencyCode_IPAddress_passed() {
		String ipAddress = "85.214.132.117";
		String findMyIpUrl = "https://api.my-ip.io/ip";
		String iplocationUrl = "https://api.iplocation.net?ip=";
		try (MockedStatic<IPLookupUtil> ipLookupUtil = Mockito.mockStatic(IPLookupUtil.class)) {
			ipLookupUtil.when(() -> IPLookupUtil.findCountryFromIP(restTemplate, ipAddress, iplocationUrl))
					.thenReturn("DE");
			CountryCurrency countryCurrency = new CountryCurrency("DE", "Germany", "EURO", "EUR");
			ReflectionTestUtils.setField(ipLookupService, "ipLocationUrl", iplocationUrl);
			ReflectionTestUtils.setField(ipLookupService, "findMyIpUrl", findMyIpUrl);
			when(countryCurrencyRepository.findById("DE")).thenReturn(Optional.of(countryCurrency));
			assertEquals("EUR", ipLookupService.getCurrencyCode(ipAddress));
		}
	}

	@Test
	void testGetCurrencyCode_expect_IPNotFoundException() {
		String ipAddress = "85.214.132.117";
		String findMyIpUrl = "https://api.my-ip.io/ip";
		String iplocationUrl = "https://api.iplocation.net?ip=";
		try (MockedStatic<IPLookupUtil> ipLookupUtil = Mockito.mockStatic(IPLookupUtil.class)) {
			ipLookupUtil.when(() -> IPLookupUtil.findPublicIP(restTemplate, findMyIpUrl))
					.thenThrow(IPNotFoundException.class);
			ipLookupUtil.when(() -> IPLookupUtil.findCountryFromIP(restTemplate, ipAddress, iplocationUrl))
					.thenReturn("DE");
			CountryCurrency countryCurrency = new CountryCurrency("DE", "Germany", "EURO", "EUR");
			ReflectionTestUtils.setField(ipLookupService, "ipLocationUrl", iplocationUrl);
			ReflectionTestUtils.setField(ipLookupService, "findMyIpUrl", findMyIpUrl);
			when(countryCurrencyRepository.findById("DE")).thenReturn(Optional.of(countryCurrency));
			Assertions.assertThrows(IPNotFoundException.class, () -> {
				ipLookupService.getCurrencyCode(null);
			});
		}
	}

	@Test
	void testGetCurrencyCode_expect_IPMetadataLookupException() {
		String ipAddress = "85.214.132.117";
		String findMyIpUrl = "https://api.my-ip.io/ip";
		String iplocationUrl = "https://api.iplocation.net?ip=";
		try (MockedStatic<IPLookupUtil> ipLookupUtil = Mockito.mockStatic(IPLookupUtil.class)) {
			ipLookupUtil.when(() -> IPLookupUtil.findPublicIP(restTemplate, findMyIpUrl)).thenReturn(ipAddress);
			ipLookupUtil.when(() -> IPLookupUtil.findCountryFromIP(restTemplate, ipAddress, iplocationUrl))
					.thenThrow(IPMetadataLookupException.class);
			CountryCurrency countryCurrency = new CountryCurrency("DE", "Germany", "EURO", "EUR");
			ReflectionTestUtils.setField(ipLookupService, "ipLocationUrl", iplocationUrl);
			ReflectionTestUtils.setField(ipLookupService, "findMyIpUrl", findMyIpUrl);
			when(countryCurrencyRepository.findById("DE")).thenReturn(Optional.of(countryCurrency));
			Assertions.assertThrows(IPMetadataLookupException.class, () -> {
				ipLookupService.getCurrencyCode(null);
			});
		}
	}

	@Test
	void testGetCurrencyCode_expect_CountryCurrencyLookupException() {
		String ipAddress = "85.214.132.117";
		String findMyIpUrl = "https://api.my-ip.io/ip";
		String iplocationUrl = "https://api.iplocation.net?ip=";
		try (MockedStatic<IPLookupUtil> ipLookupUtil = Mockito.mockStatic(IPLookupUtil.class)) {
			ipLookupUtil.when(() -> IPLookupUtil.findPublicIP(restTemplate, findMyIpUrl)).thenReturn(ipAddress);
			ipLookupUtil.when(() -> IPLookupUtil.findCountryFromIP(restTemplate, ipAddress, iplocationUrl))
					.thenReturn("DE");
			ReflectionTestUtils.setField(ipLookupService, "ipLocationUrl", iplocationUrl);
			ReflectionTestUtils.setField(ipLookupService, "findMyIpUrl", findMyIpUrl);
			when(countryCurrencyRepository.findById("DE")).thenReturn(Optional.empty());
			Assertions.assertThrows(CountryCurrencyLookupException.class, () -> {
				ipLookupService.getCurrencyCode(null);
			});
		}
	}

}
