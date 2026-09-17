package co.edu.udea.bookingnow.infrastructure.adapter.in.rest.dto;

public record LoginResponse<T>(String accessToken, String tokenType, long expiresIn, T cuenta) {
    @Override
    public String toString() { return "LoginResponse[token omitido]"; }
}
