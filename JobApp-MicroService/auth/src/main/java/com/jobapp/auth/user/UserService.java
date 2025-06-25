package com.jobapp.auth.user;

import com.jobapp.auth.models.LoginRequestDTO;
import com.jobapp.auth.models.LoginResponseDTO;
import com.jobapp.auth.models.UserRequestDTO;
import com.jobapp.auth.models.UserResponseDTO;

public interface UserService {

    // Registers a new user and returns info to show the client (never the password or hash).
    UserResponseDTO registerUser(UserRequestDTO userRequest);

    // Checks login credentials and returns a JWT if valid, or throws an exception if not.
// You NEVER return a boolean for login in a JWT system!
    LoginResponseDTO loginUser(LoginRequestDTO loginRequest);

    // Gets a user by their id (for profile page, etc.).
    UserResponseDTO getUserById(Long id);

    // Gets a user by email (optional, for admin or debugging).
    UserResponseDTO getUserByEmail(String email);

    // Updates a user’s info (except password).
    UserResponseDTO updateUser(Long id, UserUpdateRequestDTO updateRequest);

    // Changes a user’s password (returns nothing or a message).
    void changePassword(Long userId, ChangePasswordDTO passwordDTO);

    // Deletes a user account (returns nothing or a status/message).
    void deleteUser(Long id);

    // Lists all users, paginated (for admin only).
   /// Page<UserResponseDTO> getAllUsers(int page, int size);

    // (Optional) Check if an email is already taken (for registration form live validation).
   // boolean emailExists(String email);
}
