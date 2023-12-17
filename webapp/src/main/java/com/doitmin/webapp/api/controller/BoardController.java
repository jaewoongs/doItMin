package com.doitmin.webapp.api.controller;

import com.doitmin.webapp.api.dto.BoardDto;
import com.doitmin.webapp.api.dto.BoardPostDto;
import com.doitmin.webapp.api.dto.ProfileDto;
import com.doitmin.webapp.api.service.BoardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "게시판 API")
@RestController
@RequestMapping("/api/v1/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/list")
    public ResponseEntity<List<BoardDto>> getBoardList() {
        return ResponseEntity.ok(boardService.getBoardList());
    }

    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BoardDto> postBoard(Authentication authentication,
                                              @Parameter(
                                                      description = "key = file",
                                                      content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
                                              )
                                              @RequestPart("file") MultipartFile file,
                                              @Parameter(
                                                      content = @Content(
                                                              mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                              schema = @Schema(implementation = BoardPostDto.class)
                                                      )
                                              )
                                              @RequestPart String boardPostDtoString) throws IOException {
        BoardPostDto boardPostDto = convertJsonStringToDto(boardPostDtoString);
        ProfileDto profileDto = (ProfileDto) authentication.getPrincipal();
        return ResponseEntity.ok(boardService.postBoard(profileDto, boardPostDto, file));
    }

    //    @PostMapping("/multipart-files")
//    public String uploadMultipleFiles(
//
//            @RequestParam String type
//    ) {
////        multipartFiles.map(it -> {
////            ObjectMetadata objectMetadata = new ObjectMetadata();
////            objectMetadata.setContentType(it.getContentType());
////            objectMetadata.setContentLength(it.getSize());
////            PutObjectRequest putObjectRequest = new PutObjectRequest(
////                    "bucketName",
////                    "objectKey",
////                    it.getInputStream(),
////                    objectMetadata
////            );
////            amazonS3Client.putObject(putObjectRequest);
////        });
//        return ResponseEntity.ok(boardService.);
//    }
    private BoardPostDto convertJsonStringToDto(String jsonString) {
        try {
            return objectMapper.readValue(jsonString, BoardPostDto.class);
        } catch (IOException e) {
            // Handle the exception, possibly throw a custom exception
            throw new RuntimeException("Error parsing JSON", e);
        }
    }

}
