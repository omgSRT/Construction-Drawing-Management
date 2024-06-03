package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.dto.response.IntrospectResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Account;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class AuthenticateService {
    //Get Jwt Secret From application.properties
    @Value("${custom.jwt.secret}")
    private String jwtSecret;

    //Introspect JWT Token
    public IntrospectResponse introspectJWT(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(jwtSecret.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiredDate = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        //Check And Return Bool If  And JWT Expired
        return IntrospectResponse
                .builder()
                .valid(verified && expiredDate.after(new Date()))
                .build();
    }

    //Check If User Is Exist And Authenticate It
    //Uncomment When Database Exist
//    public boolean authenticate(AuthenticateRequest request){
//        var user = repository.findByUsername(request.getUsername())
//                .orElseThrow(() -> new AppException(ErrorCode12.USER_NOT_EXISTED));
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
//        return passwordEncoder.matches(request.getPassword(),user.getPassword());
//    }

    //Generate JWT Token
    public String generateToken(Account account){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(account.getUsername())
                .issuer("dev-GSU24SE23")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(3, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("accountId", account.getAccountId())
                .claim("scope", buildScope(account))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());


        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(jwtSecret.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot Create JWT", e);
            throw new RuntimeException(e);
        }
    }

    private String buildScope(Account account){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(account.getRoles())){
            account.getRoles().forEach(stringJoiner::add);
        }

        return stringJoiner.toString();
    }
}
