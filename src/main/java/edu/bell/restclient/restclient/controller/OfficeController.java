package edu.bell.restclient.restclient.controller;


import edu.bell.restclient.restclient.dto.response.ResponseDto;
import edu.bell.restclient.restclient.service.RestOfficeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import edu.bell.restclient.restclient.dto.request.OfficeInListDto;
import edu.bell.restclient.restclient.dto.request.OfficeInSaveDto;
import edu.bell.restclient.restclient.dto.request.OfficeInUpdateDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/office/")
@Tag(name = "Офис контроллер", description = "Контроллер для работы с офисом")
public class OfficeController {

    private final RestOfficeService restOfficeService;

    public OfficeController(RestOfficeService restOfficeService) {
        this.restOfficeService = restOfficeService;
    }

    @Operation(summary = "Получение офиса по Id", description = "Ищет офис по его Id")
    @GetMapping("{id}")
    public ResponseDto getOfficeById(@PathVariable @Parameter(
            description = "Уникальный идентификатор офиса"
            , required = true) Integer id) {
        return restOfficeService.getOfficeById(id);
    }

    @Operation(summary = "Сохранение нового офиса", description = "Сохраняет новый офис в БД")
    @PostMapping("save")
    public ResponseDto saveOffice(@Valid @RequestBody OfficeInSaveDto office) {
        return restOfficeService.saveOffice(office);
    }

    @Operation(summary = "Поиск офиса по фильтру", description = "Позволяет искать офис по нескольким параметрам")
    @PostMapping("list")
    public ResponseDto getListOfficeByRequest(@Valid @RequestBody  OfficeInListDto office) {
        return restOfficeService.getListOfficeByRequest(office);
    }

    @Operation(summary = "Обновление офиса", description = "Обновляет офис в БД")
    @PostMapping("update")
    public ResponseDto updateOffice(@Valid @RequestBody OfficeInUpdateDto officeInUpdateDto) {
        return restOfficeService.updateOffice(officeInUpdateDto);
    }
}
