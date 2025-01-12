export default class PetitionerSignUpModel {
    constructor(emailId, firstName, lastName, dateOfBirth, password, biometricId) {
        this.emailId = emailId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
        this.biometricId = biometricId;
    }

}