import React, { useState, useEffect } from "react";
import styled from "styled-components";
import PetitionDecisionService from "../service/PetitionDecisionService";


const ThresholdManagement = () => {
  const [currentThreshold, setCurrentThreshold] = useState(1); // Holds the current threshold fetched from backend
  const [threshold, setThreshold] = useState(1); // Holds the new value to be set
  const [message, setMessage] = useState("");

  useEffect(() => {
    const fetchThreshold = async () => {
      try {
        const currentThreshold = await PetitionDecisionService.getThreshold();
        setCurrentThreshold(currentThreshold);
        setThreshold(currentThreshold); // Initialize dropdown to current threshold
      } catch (error) {
        console.error("Error fetching threshold:", error.message);
        setMessage("Failed to fetch current threshold");
      }
    };

    fetchThreshold();
  }, []);

  const handleSetThreshold = async () => {
    try {
      await PetitionDecisionService.updateThreshold(threshold);
      setCurrentThreshold(threshold); // Update current threshold after successful API call
      setMessage("Threshold updated successfully!");
    } catch (error) {
      console.error("Error updating threshold:", error.message);
      setMessage("Failed to update threshold");
    }
  };

  return (
    <ThresholdContainer>
      <Label>Current Signature Threshold</Label>
      <CurrentThreshold>{currentThreshold}</CurrentThreshold>

      <Label>Set New Signature Threshold</Label>
      <Dropdown
        value={threshold}
        onChange={(e) => setThreshold(parseInt(e.target.value, 10))}
      >
        {[...Array(50)].map((_, i) => (
          <option key={i + 1} value={i + 1}>
            {i + 1}
          </option>
        ))}
      </Dropdown>
      <SetButton onClick={handleSetThreshold}>Set</SetButton>

      {message && <Message success={message.includes("successfully")}>{message}</Message>}
    </ThresholdContainer>
  );
};

export default ThresholdManagement;


const ThresholdContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 50px;
  background: #242424;
  padding: 40px 30px;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.5);
  color: #e0e0e0;
  width: 500px;
`;

const Label = styled.h2`
  font-size: 20px;
  margin-bottom: 15px;
  font-weight: 600;
  color: #ffffff;
  text-align: center;
`;

const CurrentThreshold = styled.div`
  font-size: 36px;
  font-weight: bold;
  color: #00ff88;
  margin-bottom: 25px;
  text-align: center;
`;

const Dropdown = styled.select`
  width: 100%;
  padding: 12px;
  font-size: 16px;
  border-radius: 8px;
  background-color: #333;
  color: #ffffff;
  border: 1px solid #007bff;
  margin-bottom: 20px;
  outline: none;
  transition: border-color 0.3s ease;

  &:hover, &:focus {
    border-color: #0056b3;
  }
`;

const SetButton = styled.button`
  padding: 12px 20px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 8px;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  width: 100%;

  &:hover {
    background-color: #0056b3;
  }

  &:active {
    background-color: #004099;
  }
`;

const Message = styled.div`
  margin-top: 20px;
  font-size: 16px;
  font-weight: bold;
  color: ${(props) => (props.success ? "#00ff88" : "#ff4d4f")};
  text-align: center;
`;