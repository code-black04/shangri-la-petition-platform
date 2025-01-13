import React, { useState } from "react";
import styled from "styled-components";
import CreatePetitionService from "../dashboard/service/CreatePetitionService.js";
import MessageBanner from "../../petitioner/signup/MessageBanner.js"

const CreatePetition = () => {

  const today = new Date().toISOString().slice(0, 10);
  const [petition_date, setPetitionDate] = useState(today);

  const [petition_title, setPetitionTitle] = useState("");
  const [petition_text, setPetitionText] = useState("");

  const [message, setMessage] = useState(''); // To store success or error message
  const [messageType, setMessageType] = useState(''); // 'success' or 'error'

  const handleSubmit = async (event) => {
    event.preventDefault();
    console.log("Petition data:", { petition_date, petition_title, petition_text });
    try {
      const petitionCreationData = {petition_date, petition_title, petition_text};
      const petitionCreationResponse = await CreatePetitionService.createPetition(petitionCreationData);

      if(petitionCreationResponse.status === 201) {
        setMessageType('success');
        setMessage("Petition created successfully!");

        setTimeout((() => {
          setMessage("");
          setMessageType('');
          setPetitionText("");
          setPetitionTitle("");
        }), 2000);
        
      } else {
        setMessageType('error');
        setMessage("An expected error occurred, please try again..");
      }
    } catch (error) {
      console.error(error);
      setMessageType('error');
      setMessage("An expected error occurred, please try again..");
    }
  };

  const handleClear = () => {
    setPetitionTitle("");
    setPetitionText("");
  };

  return (
    <PetitionFormContainer>
      <MessageBanner type={messageType} message={message} />
      <FormTitle>Create New Petition</FormTitle>
      <Form onSubmit={handleSubmit}>
        <Label>Date</Label>
        <Input type="text"
          value={petition_date}
          readOnly
          required/>

        <Label>Title</Label>
        <Input
          type="text"
          placeholder="Enter petition title"
          value={petition_title}
          onChange={(e) => setPetitionTitle(e.target.value)}
          required
        />

        <Label>Content</Label>
        <TextArea
          placeholder="Enter petition content"
          value={petition_text}
          onChange={(e) => setPetitionText(e.target.value)}
          required
        />

        <ButtonRow>
          <SubmitButton type="button" onClick={handleClear}>
            Clear
          </SubmitButton>
          <SubmitButton type="submit">Submit</SubmitButton>
        </ButtonRow>
      </Form>
    </PetitionFormContainer>
  );
};

export default CreatePetition;


const PetitionFormContainer = styled.div`
  background: #1e1e1e;
  border: 1px solid #333;
  padding: 30px;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
  max-width: 700px;
  width: 100%; /* Ensure responsiveness */
  margin: 0 auto;
  color: #e0e0e0;
`;

const FormTitle = styled.h2`
  text-align: center;
  margin-bottom: 20px;
  font-size: 28px;
  font-weight: 600;
  color: #ffffff;
`;

const Form = styled.form`
  display: flex;
  flex-direction: column;
`;

const Label = styled.label`
  font-weight: bold;
  margin-bottom: 5px;
  font-size: 16px;
  color: #ffffff;
  text-align: justify;
`;

const Input = styled.input`
  padding: 12px;
  margin-bottom: 20px;
  border: 1px solid #555;
  border-radius: 5px;
  font-size: 16px;
  background-color: #2a2a2a;
  color: #ffffff;
  &:focus {
    outline: none;
    border-color: #007bff;
  }
`;

const TextArea = styled.textarea`
  padding: 12px;
  margin-bottom: 20px;
  border: 1px solid #555;
  border-radius: 5px;
  font-size: 16px;
  background-color: #2a2a2a;
  color: #ffffff;
  height: 100px; /* Consistent height */
  max-height: 200px; /* Prevent excessive stretching */
  resize: vertical; /* Allow resizing vertically only */

  &:focus {
    outline: none;
    border-color: #007bff;
  }
`;

const ButtonRow = styled.div`
  display: flex;
  justify-content: space-between;
  gap: 10px;
  flex-wrap: wrap; /* Ensure responsiveness for small screens */
`;

const SubmitButton = styled.button`
  flex: 1; /* Equal width for buttons */
  padding: 12px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;

  &:hover {
    background-color: #0056b3;
  }

  &:focus {
    outline: none;
  }
`;