package org.aptech.t2109e.jspservlet.config.properties;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class JwtConfig {
    public final String AUTH_HEADER = "Authorization";
    public final String TOKEN_PREFIX = "Bearer ";
    private String SECRET_KEY;
    private int EXPIRATION_TIME;

    private static JwtConfig instance = null;
    public static JwtConfig getInstance(){
        if(instance == null){
            return new JwtConfig();
        }
        return instance;
    }

    private JwtConfig(){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream("application.properties");
        try {
            Properties properties = new Properties();
            properties.load(input);
            this.SECRET_KEY = properties.getProperty("jwt.properties.secret-key").trim();
            this.EXPIRATION_TIME = Integer.parseInt(properties.getProperty("jwt.properties.expiration-time").trim());
        } catch (IOException e) {
            System.err.println("Cannot read file properties \n");
            e.printStackTrace();
        }
    }


    public String createJwtToken(String email) {
        String token = Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
        return token;
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();

            Date expirationDate = claims.getExpiration();
            Date currentDate = new Date();

            if (expirationDate.before(currentDate)) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

}
