const API_URL = 'http://localhost/api/slpp';

class GetAllPetitionService {
    async getAllPetitions(status) {
        // Construct URL dynamically based on the status
        const url = status === "All" ? `${API_URL}/petitions` : `${API_URL}/petitions?status=${status}`;

        const response = await fetch(url, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include',
        });

        console.log("RESPONSE: ", response);
        
        return response;
    }
}

export default new GetAllPetitionService();