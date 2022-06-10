package edu.bell.restclient.restclient.service;

import edu.bell.restclient.restclient.config.BaseUrl;
import edu.bell.restclient.restclient.dto.request.OrganisationDtoRequest;
import edu.bell.restclient.restclient.dto.request.OrganizationSaveInDto;
import edu.bell.restclient.restclient.dto.request.OrganizationUpdateInDto;
import edu.bell.restclient.restclient.dto.request.ResponseDto;
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

    public ResponseEntity<ResponseDto> getOrganizationById(Integer id) {
        return template.getForEntity(baseUrl.getUrl() + url + id, ResponseDto.class);
    }

    public ResponseEntity<ResponseDto> getOrganizationByRequest(OrganisationDtoRequest organisationDTO) {
        return template
                .postForEntity(baseUrl.getUrl() + url + "list", organisationDTO, ResponseDto.class);
    }

    public ResponseEntity<ResponseDto> saveOrganization(OrganizationSaveInDto organizationSaveInDto) {
        return template.postForEntity(baseUrl.getUrl() + url + "save", organizationSaveInDto, ResponseDto.class);
    }

    public ResponseEntity<ResponseDto> updateOrganization(OrganizationUpdateInDto organizationUpdateInDto) {
        return template
                .postForEntity(baseUrl.getUrl() + url + "update", organizationUpdateInDto, ResponseDto.class);
    }
}
