// package com.crio.rent_read.config.security;

// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import java.io.IOException;
// import java.time.LocalDateTime;
// import java.util.HashMap;
// import java.util.Map;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.springframework.http.HttpStatus;
// import org.springframework.security.access.AccessDeniedException;
// import org.springframework.security.web.access.AccessDeniedHandler;
// import org.springframework.stereotype.Component;

// @Component
// public class CustomAccessDeniedHandler implements AccessDeniedHandler {

//     @Override
//     public void handle(HttpServletRequest request, HttpServletResponse response,
//             AccessDeniedException accessDeniedException) throws IOException, ServletException {
        
//                 response.setStatus(HttpStatus.FORBIDDEN.value());
//                 response.setContentType("application/json");
        
//                 Map<String, Object> body = new HashMap<>();
//                 body.put("message", "Access Denied: You don't have permission to perform this action");
//                 body.put("httpStatus", "FORBIDDEN");
//                 body.put("localDateTime", LocalDateTime.now().toString());
        
//                 ObjectMapper mapper = new ObjectMapper();
//                 response.getWriter().write(mapper.writeValueAsString(body));
        
//     }
    
// }


package com.crio.rent_read.config.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import com.crio.rent_read.exchange.AccessDeniedResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    // @Override
    // public void handle(HttpServletRequest request, HttpServletResponse response,
    //                    AccessDeniedException accessDeniedException) throws IOException, ServletException {

    //     response.setStatus(HttpStatus.FORBIDDEN.value());
    //     response.setContentType("application/json");

    //     Map<String, Object> body = new HashMap<>();
    //     body.put("message", "Access Denied: You don't have permission to perform this action");
    //     body.put("httpStatus", "FORBIDDEN");
    //     body.put("localDateTime", LocalDateTime.now());

    //     ObjectMapper mapper = new ObjectMapper();
    //     mapper.registerModule(new JavaTimeModule()); // Proper serialization of LocalDateTime
    //     mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    //     response.getWriter().write(mapper.writeValueAsString(body));
    // }

    
    @Override
public void handle(HttpServletRequest request, HttpServletResponse response,
                   AccessDeniedException accessDeniedException) throws IOException {

    response.setStatus(HttpStatus.FORBIDDEN.value());
    response.setContentType("application/json");

    AccessDeniedResponse errorResponse = new AccessDeniedResponse(
        "Access Denied: You don't have permission to perform this action",
        "FORBIDDEN",
        LocalDateTime.now().toString()
    );

    ObjectMapper mapper = new ObjectMapper();
    response.getWriter().write(mapper.writeValueAsString(errorResponse));
}






}
