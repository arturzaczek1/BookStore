package pl.arturzaczek.demo;


import org.apache.commons.codec.digest.DigestUtils;


public class UserRegistrationService {

    private UserDAO userDao;

    public void registerUser(UserRegistrationDTO userRegistrationDTO) {
        if (userDao.checkIfUserExist(userRegistrationDTO.getEMail())) {
            throw new UserExistsException("User exists!!");
        }
        userDao.saveUser(rewriteDTOToUser(userRegistrationDTO));
    }

    private User rewriteDTOToUser(UserRegistrationDTO userRegistrationDTO) {
        User user = new User();

        user.setFirstName(userRegistrationDTO.getFirstName());
        user.setLastName(userRegistrationDTO.getLastName());
        user.setEMail(userRegistrationDTO.getEMail());
        user.setPasswordHash(DigestUtils.sha512Hex(userRegistrationDTO.getPassword()));

        UserAddress userAddress = new UserAddress();
        userAddress.setCity(userRegistrationDTO.getCity());
        userAddress.setCountry(userRegistrationDTO.getCountry());
        userAddress.setStreet(userRegistrationDTO.getStreet());
        userAddress.setZipCode(userRegistrationDTO.getZipCode());

        user.setUserAddress(userAddress);
        user.setBirthDate(userRegistrationDTO.getBirthDate());
        user.setPesel(userRegistrationDTO.getPesel());
        user.setPhone(userRegistrationDTO.getPhone());
        user.setPreferEmails(userRegistrationDTO.isPreferEmails());
        // -> uzupenic pozostale pola todo
        return user;
    }
}

