const API_BASE_URL = "http://localhost/api/slpp";

class PetitionDecisionService {
  async updatePetition(petitionId, updatedData) {
    console.log("Petition ID:", petitionId);
    const url = `${API_BASE_URL}/petition/${petitionId}/update`;

    const response = await fetch(url, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(updatedData),
      credentials: 'include',
    });

    if (!response.ok) {
      const errorResponse = await response.json();
      const errorMessage = errorResponse.errorMessage || "Failed to update the petition.";
      throw new Error(errorMessage);
    }

    // Return a success message
    return "Petition closed successfully!";
  }

  async updateThreshold(threshold) {
    console.log("Updating threshold to:", threshold);
    const url = `${API_BASE_URL}/petition/threshold?threshold=${threshold}`;

    const response = await fetch(url, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ threshold }),
       credentials: 'include',// Send the threshold in the request body
    });

    if (response.status !== 200) {
      throw new Error(
        `Failed to update threshold. Status: ${response.status}`
      );
    }

    return true;
  }

  async getThreshold() {
    const url = `${API_BASE_URL}/petition/threshold`;

    const response = await fetch(url, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
      credentials: 'include',
    });

    if (response.status !== 200) {
      throw new Error(
        `Failed to get threshold. Status: ${response.status}`
      );
    }

    const data = await response.json(); // Parse the JSON response
    console.log("Current Threshold: ", data); // For debugging purposes

    // Assuming the threshold value is returned inside the responseMessage
    return data;
  }
}

export default new PetitionDecisionService();
