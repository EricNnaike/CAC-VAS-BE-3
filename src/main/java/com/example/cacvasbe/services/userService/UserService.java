package com.example.cacvasbe.services.userService;

import com.example.cacvasbe.dto.*;
import com.example.cacvasbe.dto.registration.UserDto;
import com.example.cacvasbe.entities.PortalUsers;
import com.example.cacvasbe.entities.Role;
import com.example.cacvasbe.enums.GenericStatusConstant;
import com.example.cacvasbe.pojo.*;
import org.springframework.http.ResponseEntity;

import javax.mail.MessagingException;
import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    PortalUsers createUser(UserDto user, PortalUsers createdBy, Role role) throws Exception;
    String getUniqueId();
    LoginResponse authenticateUser(LoginRequestDto dto) throws Exception;
    void resetPassword(PasswordDto dto) throws Exception;
    ResponseEntity<MessageResponse> forgotPassword(ForgotPasswordRequest request) throws MessagingException;
    ApprovalResponse requestReactivation(Long id);
    PortalUserPojo get(PortalUsers user);
    List<PortalUserPojo> get(List<PortalUsers> users);
    ResponseClass<?> getUserByRCNumber(String reNumber);
    void deactivateUser(DeactivateUserDto deactivateUserDto);
    GenericStatusConstant findStatus(String rcNumber);
    boolean cacService(String rcNumber);
    APIResponse<?> logout();
    APIResponse<?> buyLisense() throws Exception;
    BigDecimal getUserWalletBalance(PortalUsers user);
    String createPublicApiKey();
    String createPrivateApiKey();
    CreateApiResponse getApiKey();

}
