package aac.br.springmvc_tres.model.service;

import aac.br.springmvc_tres.model.dto.response.AddressDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url= "https://viacep.com.br/ws/" , value = "viacep")
public interface PostalCodeService {
	
	@GetMapping("{postalCode}/json")
    AddressDto findAddressByCep(@PathVariable("postalCode") String postalCode);
}