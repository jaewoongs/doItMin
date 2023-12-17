package com.doitmin.webapp.api.service;

import com.doitmin.webapp.api.dto.BoardDto;
import com.doitmin.webapp.api.dto.BoardPostDto;
import com.doitmin.webapp.api.dto.ProfileDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BoardService {
    List<BoardDto> getBoardList();

    BoardDto postBoard(ProfileDto profileDto, BoardPostDto boardPostDto, MultipartFile file) throws IOException;
}
