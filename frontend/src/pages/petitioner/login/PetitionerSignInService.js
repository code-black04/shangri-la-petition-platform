const API_URL = 'http://localhost/api/petitioner/auth/login';

class PetitionerSignInService {
    async getUser(userSignInData) {
           const urlEncodedData = new URLSearchParams(userSignInData).toString();

    console.log("formData : " + urlEncodedData);
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(userSignInData),
            credentials: 'include',
        });

        console.log("RESPONSE: ", response);
        
        return response;
    }
}

export default new PetitionerSignInService();