package edu.bell.restclient.restclient.controller;

import edu.bell.restclient.restclient.dto.request.OrganisationDtoRequest;
import edu.bell.restclient.restclient.dto.request.OrganizationSaveInDto;
import edu.bell.restclient.restclient.dto.request.ResponseDto;
import edu.bell.restclient.restclient.service.RabbitOrganizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrganizationRabbitController implements RabbitOrganizationControllerApi {

    private final RabbitOrganizationService rabbitOrganizationService;

    public OrganizationRabbitController(RabbitOrganizationService rabbitOrganizationService) {
        this.rabbitOrganizationService = rabbitOrganizationService;
    }

    public ResponseEntity<ResponseDto> getOrganizationByIdQueue(Integer id) {
        return rabbitOrganizationService.getOrganizationById(id);
    }

    public ResponseEntity<ResponseDto> getListOrganizationQueue(OrganisationDtoRequest organisationDTO) {
        return rabbitOrganizationService.getListOrganizationByRequest(organisationDTO);
    }

    public ResponseEntity<ResponseDto> saveOrganizationQueue(OrganizationSaveInDto organizationSaveInDto) {
        return rabbitOrganizationService.saveOrganization(organizationSaveInDto);
    }
}
