const API_URL = 'http://localhost/api/slpp/petitions/signature';

class SignPetitionService {
    async signPetition(petitionId) {
        const emailId = 'pm4525@gmail.com';//TODO - get from profile or token
        const response =  await fetch(`${API_URL}?petitionId=${petitionId}&emailId=${emailId}`, {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
          credentials: 'include',
        });
      
        if (!response.ok) {
          throw new Error("Failed to sign the petition");
        }
      
        return response.json();
      };
}

export default new SignPetitionService();
  