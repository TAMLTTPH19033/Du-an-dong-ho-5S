package com.datn.dongho5s.Service;

import com.datn.dongho5s.Exception.CustomException.BadRequestException;
import com.datn.dongho5s.Request.RegisterRequest;
import com.datn.dongho5s.Response.RegisterResponse;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public interface AccountService {
    ResponseEntity<?> register (RegisterRequest registerRequest) throws BadRequestException;
    HashMap<Integer,String> getListTP();
    HashMap<Integer,String> getListQuan( Integer idTP) throws Exception;
    HashMap<String,String> getListPhuong( Integer idQH) throws  Exception;

}
