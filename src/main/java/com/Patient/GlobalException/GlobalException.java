package com.Patient.GlobalException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.Patient.Exception.DoctorNotFoundException;
import com.Patient.Exception.MedicalRecordNotFoundException;
import com.Patient.Exception.PatientNotFoundException;
import com.Patient.Exception.UserInvalidException;

@ControllerAdvice
public class GlobalException {

@ExceptionHandler( PatientNotFoundException.class)
private ResponseEntity<?> StudentNotFoundHandler(PatientNotFoundException except){
    return responsebody(HttpStatus.NOT_FOUND,except.getMessage());

}

@ExceptionHandler( DoctorNotFoundException.class)
private ResponseEntity<?> DoctorNotFoundHandler(DoctorNotFoundException except){
    return responsebody(HttpStatus.NOT_FOUND,except.getMessage());

}

@ExceptionHandler( MedicalRecordNotFoundException.class)
private ResponseEntity<?> MedicalRecordsNotFoundHandler(MedicalRecordNotFoundException except){
    return responsebody(HttpStatus.NOT_FOUND,except.getMessage());

}

@ExceptionHandler(UserInvalidException.class)
private ResponseEntity<?> InvalidUserHandler(UserInvalidException except){
    return responsebody(HttpStatus.FORBIDDEN,except.getMessage());

}

@ExceptionHandler(IllegalArgumentException.class)
public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex) {
    return responsebody(HttpStatus.BAD_REQUEST, ex.getMessage());
}

@ExceptionHandler(Exception.class)
public ResponseEntity<?> handleGeneralException() {
    return responsebody(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
}

private ResponseEntity<?> responsebody(HttpStatus statusquote,String message){

    Map<String,Object>  map = new LinkedHashMap<>();
    map.put("Status",statusquote.value());
    map.put("Error Message",message);
    map.put("Time",LocalDateTime.now());
    return new ResponseEntity<>(map,statusquote);
}
   
}
