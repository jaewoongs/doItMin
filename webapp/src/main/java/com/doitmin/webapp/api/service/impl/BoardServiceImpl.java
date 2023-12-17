package com.doitmin.webapp.api.service.impl;

import com.doitmin.webapp.api.dto.BoardDto;
import com.doitmin.webapp.api.dto.BoardPostDto;
import com.doitmin.webapp.api.dto.ProfileDto;
import com.doitmin.webapp.api.mapper.BoardMapper;
import com.doitmin.webapp.api.mapper.BoardPostMapper;
import com.doitmin.webapp.api.repository.BoardRepository;
import com.doitmin.webapp.api.service.BoardService;
import io.awspring.cloud.s3.S3Resource;
import io.awspring.cloud.s3.S3Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    private S3Template s3Template;
    @Autowired
    private BoardRepository boardRepository;

    @Override
    public List<BoardDto> getBoardList() {
        return boardRepository
                .findAllByOrderByIdDesc()
                .stream()
                .map(BoardMapper.INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BoardDto postBoard(ProfileDto profileDto, BoardPostDto boardPostDto, MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            InputStream is = file.getInputStream();
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
// uploading file without metadata
            S3Resource s = s3Template.upload("doitmin", "DoitminFile_" + System.currentTimeMillis() + extension, is);
            String url = s.getURL().toString();
            boardPostDto.setBoardImageUrl(url);
// uploading file with metadata
//        s3Template.upload(BUCKET, "file.txt", is, ObjectMetadata.builder().contentType("text/plain").build());
        }
        boardRepository.save(BoardPostMapper.INSTANCE.toEntity(boardPostDto));
        return null;
    }


}
