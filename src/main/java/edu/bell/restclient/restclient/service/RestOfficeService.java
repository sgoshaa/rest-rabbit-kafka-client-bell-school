package edu.bell.restclient.restclient.service;

import edu.bell.restclient.restclient.config.BaseUrl;
import edu.bell.restclient.restclient.dto.request.OfficeInListDto;
import edu.bell.restclient.restclient.dto.request.OfficeInSaveDto;
import edu.bell.restclient.restclient.dto.request.OfficeInUpdateDto;
import edu.bell.restclient.restclient.dto.request.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestOfficeService {

    private final String url = "/api/office/";
    private final RestTemplate template;
    private final BaseUrl baseUrl;

    public RestOfficeService(RestTemplate template, BaseUrl baseUrl) {
        this.template = template;
        this.baseUrl = baseUrl;
    }

    public ResponseEntity<ResponseDto> getOfficeById(Integer id) {
        return template.getForEntity(baseUrl.getUrl() + url + id, ResponseDto.class);

    }

    public ResponseEntity<ResponseDto> saveOffice(OfficeInSaveDto office) {
        return template.postForEntity(baseUrl.getUrl() + url + "save", office, ResponseDto.class);
    }

    public ResponseEntity<ResponseDto> getListOfficeByRequest(OfficeInListDto office) {
        return template
                .postForEntity(baseUrl.getUrl() + url + "list", office, ResponseDto.class);
    }

    public ResponseEntity<ResponseDto> updateOffice(OfficeInUpdateDto officeInUpdateDto) {
        return template
                .postForEntity(baseUrl.getUrl() + url + "update", officeInUpdateDto, ResponseDto.class);
    }
}
