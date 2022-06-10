package edu.bell.restclient.restclient.controller;

import edu.bell.restclient.restclient.dto.request.OrganisationDtoRequest;
import edu.bell.restclient.restclient.dto.request.OrganizationSaveInDto;
import edu.bell.restclient.restclient.dto.request.OrganizationUpdateInDto;
import edu.bell.restclient.restclient.dto.request.ResponseDto;
import edu.bell.restclient.restclient.service.RestOrganizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class OrganizationController implements OrganizationControllerApi {

    private final RestOrganizationService restService;

    public OrganizationController(RestOrganizationService restService) {
        this.restService = restService;
    }

    public ResponseEntity<ResponseDto> getOrganizationById(Integer id) {
        return restService.getOrganizationById(id);
    }

    public ResponseEntity<ResponseDto> getListOrganization(OrganisationDtoRequest organisationDTO) {
        return restService.getOrganizationByRequest(organisationDTO);
    }

    public ResponseEntity<ResponseDto> saveOrganization(OrganizationSaveInDto organizationSaveInDto) {
        return restService.saveOrganization(organizationSaveInDto);
    }

    public ResponseEntity<ResponseDto> updateOrganization(OrganizationUpdateInDto organizationUpdateInDto) {
        return restService.updateOrganization(organizationUpdateInDto);
    }
}
