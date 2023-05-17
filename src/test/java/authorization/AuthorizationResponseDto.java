package authorization;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthorizationResponseDto {

    private String scope;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("expires_in")
    private Integer expiresIn;

    private String jti;

    @JsonProperty("access_token")
    private String accessToken;
}

//{
//        "scope": "openid",
//        "token_type": "bearer",
//        "expires_in": 3600,
//        "jti": "4858db76-bd87-4354-9f2d-15423d3e21bc",
//        "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhbGx1cmU4Iiwic2NvcGUiOlsib3BlbmlkIl0sImlzcyI6IkFsbHVyZSBUZXN0T3BzIiwiZXhwIjoxNjg0MDUzNzgwLCJpYXQiOjE2ODQwNTAxODAsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJjbGllbnRfaWQiOiJhbGx1cmUtZWUtZ2F0ZXdheSIsImp0aSI6IjE3NzY0ZjJmLTQ3N2QtNGExYy04N2VjLWJkNmJhYjgzM2U2ZiJ9.U24lIs4cOpq-UAFoRPUubOwf_nrmWHrRql9ugaMDODZozSBUxvKhg_tBNP0l-KVpBIRKO_s9SbO1QeRaT601qJXT1p5-IhOCCf7d7lZvwJI5D7LRknueo1y9Osn0ELIPfR-k3ZYWQpHOFfLRbH6VH_T8PLyvttCohrO5NeK52R-1wBpLRjxO1cP_VVOHos2WDWli3D8f-sicx9nkWQ2shOV_Ta9r1_Cq4-KE0sAowvg-hHuXtbWix7NstaXr-f7gm-p8Wqxa_SRuPbvHEaLTdWwvML6YHXZWfrhkOOQjdPBuixmtDebtvD3uxQABUmQIUJVo9UBrf9N1GtpjPpW1Ww"
//        }