package edu.bell.restclient.restclient.service;

import edu.bell.restclient.restclient.config.BaseUrl;
import edu.bell.restclient.restclient.dto.request.OfficeInListDto;
import edu.bell.restclient.restclient.dto.request.OfficeInSaveDto;
import edu.bell.restclient.restclient.dto.request.OfficeInUpdateDto;
import edu.bell.restclient.restclient.dto.response.ResponseDto;
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

    public ResponseDto getOfficeById(Integer id) {
        return template.getForObject(baseUrl.getUrl() + url + id, ResponseDto.class);
    }

    public ResponseDto saveOffice(OfficeInSaveDto office) {
        ResponseEntity<ResponseDto> responseEntity
                = template.postForEntity(baseUrl.getUrl() + url + "save", office, ResponseDto.class);
        return responseEntity.getBody();
    }

    public ResponseDto getListOfficeByRequest(OfficeInListDto office) {
        ResponseEntity<ResponseDto> responseDtoResponseEntity = template
                .postForEntity(baseUrl.getUrl() + url + "list", office, ResponseDto.class);
        return responseDtoResponseEntity.getBody();
    }

    public ResponseDto updateOffice(OfficeInUpdateDto officeInUpdateDto) {
        ResponseEntity<ResponseDto> responseDtoResponseEntity = template
                .postForEntity(baseUrl.getUrl() + url + "update", officeInUpdateDto, ResponseDto.class);
        return responseDtoResponseEntity.getBody();
    }
}
