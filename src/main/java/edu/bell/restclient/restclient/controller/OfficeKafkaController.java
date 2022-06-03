package edu.bell.restclient.restclient.controller;

import edu.bell.restclient.restclient.dto.request.OfficeInListDto;
import edu.bell.restclient.restclient.dto.request.OfficeInSaveDto;
import edu.bell.restclient.restclient.dto.request.OfficeInUpdateDto;
import edu.bell.restclient.restclient.dto.response.ResponseDto;
import edu.bell.restclient.restclient.service.KafkaOfficeService;
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

@Tag(description = "Позволяет работать с сущностью офис через очередь Kafka", name = "Kafka офис контроллер")
@RestController
@RequestMapping("/api/kafka/office/")
public class OfficeKafkaController {

    private final KafkaOfficeService kafkaOfficeService;

    public OfficeKafkaController(KafkaOfficeService kafkaOfficeService) {
        this.kafkaOfficeService = kafkaOfficeService;
    }

    @Operation(summary = "Получение офиса по id", description = "Позволяет получить офис по id, используя kafka")
    @GetMapping("{id}")
    public ResponseDto getOfficeById(@PathVariable @Parameter(
            description = "Уникальный идентификатор офиса",
            example = "101") Integer id) {
        return kafkaOfficeService.getOfficeById(id);

    }

    @Operation(summary = "Сохранение нового офиса", description = "Сохраняет новый офис в БД")
    @PostMapping("save")
    public ResponseDto saveOffice(@Valid @RequestBody OfficeInSaveDto office) {
        return kafkaOfficeService.saveOffice(office);
    }

    @Operation(summary = "Поиск офиса по фильтру", description = "Позволяет искать офис по нескольким параметрам")
    @PostMapping("list")
    public ResponseDto getListOfficesByRequest(@Valid @RequestBody OfficeInListDto office) {
        return kafkaOfficeService.getListOfficeByRequest(office);
    }

    @Operation(summary = "Обновление офиса", description = "Обновляет офис в БД")
    @PostMapping("update")
    public ResponseDto updateOffice(@Valid @RequestBody OfficeInUpdateDto officeInUpdateDto) {
        return kafkaOfficeService.updateOffice(officeInUpdateDto);
    }
}
