const API_URL = 'http://localhost/api/slpp/petitions/create';

class CreatePetitionService {
    async createPetition(petitionData) {
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(petitionData),
            credentials: 'include',
        });

        console.log("RESPONSE: ", response);
        
        return response;
    }
}

export default new CreatePetitionService();