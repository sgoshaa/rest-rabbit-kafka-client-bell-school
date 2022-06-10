package edu.bell.restclient.restclient.controller;


import edu.bell.restclient.restclient.dto.request.OfficeInListDto;
import edu.bell.restclient.restclient.dto.request.OfficeInSaveDto;
import edu.bell.restclient.restclient.dto.request.OfficeInUpdateDto;
import edu.bell.restclient.restclient.dto.request.ResponseDto;
import edu.bell.restclient.restclient.service.RestOfficeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OfficeController implements OfficeControllerApi {

    private final RestOfficeService restOfficeService;

    public OfficeController(RestOfficeService restOfficeService) {
        this.restOfficeService = restOfficeService;
    }

    public ResponseEntity<ResponseDto> getOfficeById(Integer id) {
        return restOfficeService.getOfficeById(id);
    }

    public ResponseEntity<ResponseDto> saveOffice(OfficeInSaveDto office) {
        return restOfficeService.saveOffice(office);
    }

    public ResponseEntity<ResponseDto> getListOfficeByRequest(OfficeInListDto office) {
        return restOfficeService.getListOfficeByRequest(office);
    }

    public ResponseEntity<ResponseDto> updateOffice(OfficeInUpdateDto officeInUpdateDto) {
        return restOfficeService.updateOffice(officeInUpdateDto);
    }
}
