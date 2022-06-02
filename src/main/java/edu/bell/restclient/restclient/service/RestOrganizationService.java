package edu.bell.restclient.restclient.service;

import edu.bell.restclient.restclient.config.BaseUrl;
import edu.bell.restclient.restclient.dto.request.OrganisationDtoRequest;
import edu.bell.restclient.restclient.dto.request.OrganizationSaveInDto;
import edu.bell.restclient.restclient.dto.request.OrganizationUpdateInDto;
import edu.bell.restclient.restclient.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestOrganizationService {

    private final BaseUrl baseUrl;
    private final RestTemplate template;
    private final String url = "/api/organization/";

    public RestOrganizationService(BaseUrl baseUrl, RestTemplate template) {
        this.baseUrl = baseUrl;
        this.template = template;

    }

    public ResponseDto getOrganizationById(Integer id) {
        return template.getForObject(baseUrl.getUrl() + url + id, ResponseDto.class);
    }

    public ResponseDto getOrganizationByRequest(OrganisationDtoRequest organisationDTO) {
        ResponseEntity<ResponseDto> responseDtoResponseEntity = template
                .postForEntity(baseUrl.getUrl() + url + "list", organisationDTO, ResponseDto.class);
        return responseDtoResponseEntity.getBody();
    }

    public ResponseDto saveOrganization(OrganizationSaveInDto organizationSaveInDto) {
        ResponseEntity<ResponseDto> responseDtoResponseEntity
                = template.postForEntity(baseUrl.getUrl() + url + "save", organizationSaveInDto, ResponseDto.class);
        return responseDtoResponseEntity.getBody();
    }

    public ResponseDto updateOrganization(OrganizationUpdateInDto organizationUpdateInDto) {
        ResponseEntity<ResponseDto> responseEntity = template
                .postForEntity(baseUrl.getUrl() + url + "update", organizationUpdateInDto, ResponseDto.class);
        return responseEntity.getBody();
    }
}
