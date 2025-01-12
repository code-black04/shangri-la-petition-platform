const API_URL = 'http://localhost/api/petitioner/auth/login';

class PetitionerCommitteeSignInService {
    async getUser(petitionerCommitteeSignInData) {
               const urlEncodedData = new URLSearchParams(petitionerCommitteeSignInData).toString();

        console.log("formData : " + urlEncodedData);
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
             body: JSON.stringify(petitionerCommitteeSignInData),
            credentials: 'include',
        });

        console.log("RESPONSE: ", response);
        
        return response;
    }
}

export default new PetitionerCommitteeSignInService();