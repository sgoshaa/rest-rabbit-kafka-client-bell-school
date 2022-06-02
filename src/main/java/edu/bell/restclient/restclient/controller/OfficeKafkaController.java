package edu.bell.restclient.restclient.controller;

import edu.bell.restclient.restclient.config.KafkaTopicConfig;
import edu.bell.restclient.restclient.dto.request.MessageDto;
import edu.bell.restclient.restclient.dto.request.OfficeInListDto;
import edu.bell.restclient.restclient.dto.request.OfficeInSaveDto;
import edu.bell.restclient.restclient.dto.request.OfficeInUpdateDto;
import edu.bell.restclient.restclient.dto.response.ResponseDto;
import edu.bell.restclient.restclient.dto.response.SuccessDto;
import edu.bell.restclient.restclient.service.KafkaOfficeService;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/kafka/office/")
public class OfficeKafkaController {

    private final KafkaOfficeService kafkaOfficeService;

    public OfficeKafkaController(KafkaOfficeService kafkaOfficeService) {
        this.kafkaOfficeService = kafkaOfficeService;
    }

    @GetMapping("{id}")
    public ResponseDto getOfficeById(@PathVariable Integer id) {
        return kafkaOfficeService.getOfficeById(id);

    }

    @PostMapping("save")
    public ResponseDto saveOffice(@Valid @RequestBody OfficeInSaveDto office) {
        return kafkaOfficeService.saveOffice(office);
    }

    @PostMapping("list")
    public ResponseDto getListOfficesByRequest(@Valid @RequestBody OfficeInListDto office) {
        return kafkaOfficeService.getListOfficeByRequest(office);
    }

    @PostMapping("update")
    public ResponseDto updateOffice(@Valid @RequestBody OfficeInUpdateDto officeInUpdateDto) {
        return kafkaOfficeService.updateOffice(officeInUpdateDto);
    }
}
