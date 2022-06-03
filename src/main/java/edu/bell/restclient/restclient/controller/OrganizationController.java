package edu.bell.restclient.restclient.controller;

import edu.bell.restclient.restclient.dto.request.OrganisationDtoRequest;
import edu.bell.restclient.restclient.dto.request.OrganizationSaveInDto;
import edu.bell.restclient.restclient.dto.request.OrganizationUpdateInDto;
import edu.bell.restclient.restclient.dto.response.ResponseDto;
import edu.bell.restclient.restclient.service.RestOrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Контроллер Организация", description = "Позволяет работать с сущностью Организация")
@RestController
@RequestMapping("/api/organization/")
public class OrganizationController {

    private final RestOrganizationService restService;

    public OrganizationController(RestOrganizationService restService) {
        this.restService = restService;
    }

    @Operation(summary = "Получение офиса по id", description = "Позволяет получать офис по id")
    @GetMapping("{id}")
    public ResponseDto getOrganizationById(@PathVariable @Parameter(description = "Уникальный идентификатор"
            , example = "101", required = true) Integer id) {
        return restService.getOrganizationById(id);
    }

    @Operation(summary = "Поиск списка организаций по фильтру",
            description = "Позволяет получать список организаций по нескольким параметрам")
    @PostMapping("list")
    public ResponseDto getListOrganization(@RequestBody OrganisationDtoRequest organisationDTO) {
        return restService.getOrganizationByRequest(organisationDTO);
    }

    @Operation(summary = "Сохранение новой организации",
            description = "Позволяет сохранять новую организацию в БД")
    @PostMapping("save")
    public ResponseDto saveOrganization(@Valid @RequestBody OrganizationSaveInDto organizationSaveInDto) {
        return restService.saveOrganization(organizationSaveInDto);
    }

    @Operation(summary = "Обновление организации",
            description = "Позволяет обновлять организацию в БД")
    @PostMapping("update")
    public ResponseDto updateOrganization(@Valid @RequestBody OrganizationUpdateInDto organizationUpdateInDto) {
        return restService.updateOrganization(organizationUpdateInDto);
    }
}
