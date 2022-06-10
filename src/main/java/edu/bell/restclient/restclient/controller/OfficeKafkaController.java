package edu.bell.restclient.restclient.controller;

import edu.bell.restclient.restclient.dto.request.OfficeInListDto;
import edu.bell.restclient.restclient.dto.request.OfficeInSaveDto;
import edu.bell.restclient.restclient.dto.request.OfficeInUpdateDto;
import edu.bell.restclient.restclient.dto.request.ResponseDto;
import edu.bell.restclient.restclient.service.KafkaOfficeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OfficeKafkaController implements KafkaOfficeControllerApi {

    private final KafkaOfficeService kafkaOfficeService;

    public OfficeKafkaController(KafkaOfficeService kafkaOfficeService) {
        this.kafkaOfficeService = kafkaOfficeService;
    }

    @Override
    public ResponseEntity<ResponseDto> getListOfficeByRequestKafka(OfficeInListDto officeInListDto) {
        return kafkaOfficeService.getListOfficeByRequest(officeInListDto);
    }

    @Override
    public ResponseEntity<ResponseDto> getOfficeByIdKafka(Integer id) {
        return kafkaOfficeService.getOfficeById(id);
    }

    @Override
    public ResponseEntity<ResponseDto> saveOfficeKafka(OfficeInSaveDto office) {
        return kafkaOfficeService.saveOffice(office);
    }

    @Override
    public ResponseEntity<ResponseDto> updateOfficeKafka(OfficeInUpdateDto officeInUpdateDto) {
        return kafkaOfficeService.updateOffice(officeInUpdateDto);
    }
}
