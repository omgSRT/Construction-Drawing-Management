package com.GSU24SE43.ConstructionDrawingManagement.service;

import com.GSU24SE43.ConstructionDrawingManagement.dto.request.IntrospectRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.request.LogoutRequest;
import com.GSU24SE43.ConstructionDrawingManagement.dto.response.IntrospectResponse;
import com.GSU24SE43.ConstructionDrawingManagement.entity.Account;
import com.GSU24SE43.ConstructionDrawingManagement.entity.InvalidatedToken;
import com.GSU24SE43.ConstructionDrawingManagement.exception.ErrorCode;
import com.GSU24SE43.ConstructionDrawingManagement.repository.InvalidateTokenRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;

import java.util.Date;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
@Service
public class AuthenticateService {
    //Get Jwt Secret From application.yaml
//    @Value("${custom.jwt.secret}")
    @Autowired
    InvalidateTokenRepository invalidateTokenRepository;
    private String jwtSecret = "9fpGEUpGqiplW2HJB7UDOpJScDgzJWJR5xqOP3zsJQKs8fuIQpvw37BP3hmNmb/9";

    //Introspect JWT Token
    public IntrospectResponse introspectJWT(IntrospectRequest request) throws JOSEException, ParseException {
        String token = request.getToken();
        boolean invalid =true;
        try {
            verifyToken(token);
        }catch (RuntimeException e){
            invalid = false;
        }
        //Check And Return Bool If  And JWT Expired
        return IntrospectResponse
                .builder()
                .valid(invalid)
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
    public String generateToken(Optional<Account> account) {
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + 3600 * 1000);
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(account.get().getUsername())
                .issuer("dev-GSU24SE23")
                .issueTime(now)
                .expirationTime(expirationTime)
                .jwtID(UUID.randomUUID().toString())
                .claim("accountId", account.get().getAccountId())
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

    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        var signToken = verifyToken(request.getToken());

        String jti = signToken.getJWTClaimsSet().getJWTID();
        Date expTime = signToken.getJWTClaimsSet().getExpirationTime();
        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jti)
                .expiryTime(expTime)
                .build();

        invalidateTokenRepository.save(invalidatedToken);
    }

    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(jwtSecret.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiredDate = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);
        if (!(verified && expiredDate.after(new Date()))) {
            throw new RuntimeException("String.valueOf(ErrorCode.UNAUTHENTICATED)");
        }
        if(invalidateTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())){
            throw new RuntimeException("unauthenicated");
        }

        return signedJWT;
    }

    private String buildScope(Optional<Account> account) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(account.get().getRoleName())) {
            account.get().getRoleName().forEach(stringJoiner::add);
        }

        return stringJoiner.toString();
    }
}
