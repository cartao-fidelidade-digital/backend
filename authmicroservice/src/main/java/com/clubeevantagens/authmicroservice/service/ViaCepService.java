package com.clubeevantagens.authmicroservice.service;

import com.clubeevantagens.authmicroservice.client.ViaCep;
import com.clubeevantagens.authmicroservice.exception.address.PostalCodeNotFoundException;
import com.clubeevantagens.authmicroservice.exception.general.ClientUnavailableException;
import com.clubeevantagens.authmicroservice.model.data.Address;
import com.clubeevantagens.authmicroservice.model.dto.response.ViaCepResponseDto;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class  ViaCepService {

    private static final Logger logger = LoggerFactory.getLogger(ViaCepService.class);

    private final ViaCep viaCep;

    public ViaCepService(ViaCep viaCep) {
        this.viaCep = viaCep;
    }

    public ViaCepResponseDto requestToViaCep(String cep) {

        try{
            return viaCep.findAddressByCep(cep);
        } catch (HttpClientErrorException.BadRequest e){
            logger.error("Postal code not found: {}", cep, e);
            throw new PostalCodeNotFoundException("Postal code not found!");
        } catch (FeignException.FeignClientException e){
            logger.error("Error consulting ViaCep client for postal code: {}", cep, e);
            throw new ClientUnavailableException("Error consulting ViaCep client!");
        }
    }
}
