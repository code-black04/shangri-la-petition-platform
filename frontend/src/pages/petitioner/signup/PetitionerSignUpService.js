const API_URL = 'http://localhost/api/petitioner/auth/signup';

class PetitionerSignUpService {
    async createUser (userSignUpData) {
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: {
                'Content-Type':'application/json',
            },
            body: JSON.stringify(userSignUpData),
            credentials: 'include',
        });

        console.log("RESPONSE: ", response);
      
        return response;
    }
}

export default new PetitionerSignUpService();