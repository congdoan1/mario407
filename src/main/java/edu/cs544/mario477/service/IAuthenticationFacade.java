package edu.cs544.mario477.service;

import edu.cs544.mario477.domain.User;

public interface IAuthenticationFacade {
    User getCurrentUser();
}